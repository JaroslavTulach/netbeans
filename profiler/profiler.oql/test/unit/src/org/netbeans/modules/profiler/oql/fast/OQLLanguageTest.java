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

import java.net.URL;
import java.util.function.Consumer;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;

public class OQLLanguageTest {
    @Test
    public void loadHeap() throws Exception {
        URL smallHeap = OQLLanguageTest.class.getResource("../engine/api/impl/small_heap.bin");
        assertNotNull("small_heap.bin found", smallHeap);
        Source src = Source.newBuilder("oql", smallHeap).build();
        Context ctx = Context.newBuilder("oql", "js").allowHostAccess(HostAccess.ALL).build();
        Value value = ctx.eval(src);

        int[] count = { 0 };
        Consumer<Object> callback = (obj) -> {
            count[0]++;
        };
        value.invokeMember("forEachObject", callback, "int[]", false);
        assertEquals("Heap searched for int[]", 19, count[0]);
    }

    @Test
    public void walkHeapInJs() throws Exception {
        URL smallHeap = OQLLanguageTest.class.getResource("../engine/api/impl/small_heap.bin");
        assertNotNull("small_heap.bin found", smallHeap);
        Source dump = Source.newBuilder("oql", smallHeap).build();
        Context ctx = Context.newBuilder("oql", "js").allowHostAccess(HostAccess.ALL).build();

        Source countSrc = Source.newBuilder("js", "(function(heap) {\n"
                + "var now = new Date().getTime();\n"
                + "var count = 0;\n"
                + "heap.forEachObject(function(x) {\n"
                + "  if (x.length >= 256) count++;\n"
                + "}, 'int[]', false);\n"
                + "var end = new Date().getTime();\n"
                + "print(count);\n"
                + "print('Took ' + (end - now) + ' ms');\n"
                + "return count;\n"
                + "})\n", "count.js").build();
        Value countInstances = ctx.eval(countSrc);

        Value heap = ctx.eval(dump);
        Value count = countInstances.execute(heap);
        assertEquals("Heap searched for long int[]", 1, count.asInt());
    }
}
