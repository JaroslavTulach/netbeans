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
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.nodes.RootNode;
import java.io.File;
import java.net.URI;
import org.graalvm.polyglot.io.ByteSequence;
import org.netbeans.lib.profiler.heap.Heap;
import org.netbeans.lib.profiler.heap.HeapFactory;

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
}

class Data {
}
