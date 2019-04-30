/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.profiler.oql.fast;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.RootNode;
import java.io.File;
import java.net.URI;
import java.util.Collections;
import java.util.Iterator;
import java.util.function.Consumer;
import org.netbeans.lib.profiler.heap.Heap;
import org.netbeans.lib.profiler.heap.HeapFactory;
import org.netbeans.lib.profiler.heap.Instance;
import org.netbeans.lib.profiler.heap.JavaClass;
import org.netbeans.lib.profiler.heap.PrimitiveArrayInstance;
import org.netbeans.modules.profiler.oql.engine.api.impl.TreeIterator;
import org.openide.util.Exceptions;

@TruffleLanguage.Registration(
    byteMimeTypes = "application/oql", name = "oql", id = "oql"
)
public class OQLLanguage extends TruffleLanguage<Data> {
    @Override
    protected Data createContext(Env env) {
        return new Data();
    }

    @Override
    protected CallTarget parse(ParsingRequest request) throws Exception {
        URI uri = request.getSource().getURI();
        Heap heap = HeapFactory.createHeap(new File(uri));
        return Truffle.getRuntime().createCallTarget(new HeapNode(this, heap));
    }

    @Override
    protected boolean isObjectOfLanguage(Object object) {
        return object instanceof HeapObject;
    }

    private static final class HeapNode extends RootNode {
        private HeapObject heap;

        public HeapNode(TruffleLanguage<?> language, Heap heap) {
            super(language);
            this.heap = new HeapObject(heap);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            return heap;
        }

    }
}

@ExportLibrary(InteropLibrary.class)
final class HeapObject implements TruffleObject {
    private final Heap heap;

    HeapObject(Heap heap) {
        this.heap = heap;
    }

    @ExportMessage
    boolean isMemberInvocable(String member) {
       return "forEachObject".equals(member);
    }

    @ExportMessage
    boolean hasMembers() {
        return true;
    }

    @ExportMessage
    Object getMembers(boolean includeInternal) throws UnsupportedMessageException {
        return "forEachObject";
    }

    @ExportMessage
    Object invokeMember(String member, Object[] arguments) throws UnsupportedMessageException, ArityException, UnknownIdentifierException, UnsupportedTypeException {
        if ("forEachObject".equals(member)) {
            Consumer<Object> consumer = (value) -> {
                try {
                    InstanceObject obj = new InstanceObject((Instance)value);
                    LibraryFactory.resolve(InteropLibrary.class).getUncached().execute(arguments[0], obj);
                } catch (UnsupportedTypeException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (ArityException ex) {
                    Exceptions.printStackTrace(ex);
                } catch (UnsupportedMessageException ex) {
                    Exceptions.printStackTrace(ex);
                }
            };
            boolean includeSubclasses = arguments.length < 2 || !Boolean.TRUE.equals(arguments[2]);
            JavaClass type = arguments.length < 1 ? null : heap.getJavaClassByName((String) arguments[1]);
            getInstances(heap, type, includeSubclasses).forEachRemaining(consumer);
            return this;
        }
        throw UnknownIdentifierException.create(member);
    }

    public Iterator getInstances(Heap delegate, final JavaClass clazz, final boolean includeSubclasses) {
        // special case for all subclasses of java.lang.Object
        if (includeSubclasses && clazz.getSuperClass() == null) {
            return delegate.getAllInstancesIterator();
        }
        return new TreeIterator<Instance, JavaClass>(clazz) {

            @Override
            protected Iterator<Instance> getSameLevelIterator(JavaClass popped) {
                return popped.getInstances().iterator();
            }

            @Override
            protected Iterator<JavaClass> getTraversingIterator(JavaClass popped) {
                return includeSubclasses ? popped.getSubClasses().iterator() : Collections.EMPTY_LIST.iterator();
            }
        };
    }

}

@ExportLibrary(InteropLibrary.class)
class InstanceObject implements TruffleObject {
    private final Instance value;

    InstanceObject(Instance value) {
        this.value = value;
    }

    @ExportMessage
    boolean isMemberReadable(String name) {
        return "length".equals(name);
    }

    @ExportMessage
    Object getMembers(boolean includePrivate) {
        return null;
    }

    @ExportMessage
    boolean hasMembers() {
        return true;
    }

    @ExportMessage
    long readMember(String name) throws UnknownIdentifierException {
        if (name.equals("length") && value instanceof PrimitiveArrayInstance) {
            return ((PrimitiveArrayInstance) value).getLength();
        }
        throw UnknownIdentifierException.create(name);
    }
}

class Data {
}
