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
package org.netbeans.modules.java.mx.project;

import java.io.File;
import java.util.Arrays;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.netbeans.api.extexecution.ExecutionDescriptor;
import org.netbeans.api.extexecution.ExecutionService;
import org.netbeans.api.extexecution.base.ProcessBuilder;
import org.netbeans.api.extexecution.print.LineConvertor;
import org.openide.util.Enumerations;

public class SequenceProcessTest {
    public static void main(String... args) {
        System.out.println(args[0]);
    }
    
    @Test
    public void testHelloWorld() throws Exception {
        File cp = new File(SequenceProcessTest.class.getProtectionDomain().getCodeSource().getLocation().toURI());
        
        String javaHome = System.getProperty("java.home");
        File java = new File(new File(javaHome, "bin"), "java");
        
        ProcessBuilder hello = ProcessBuilder.getLocal();
        hello.setExecutable(java.getPath());
        hello.setArguments(Arrays.asList("-cp", cp.getPath(), "org.netbeans.modules.java.mx.project.SequenceProcessTest", "Hello"));
        
        ProcessBuilder world = ProcessBuilder.getLocal();
        world.setExecutable(java.getPath());
        world.setArguments(Arrays.asList("-cp", cp.getPath(), "org.netbeans.modules.java.mx.project.SequenceProcessTest", "World"));
        
        StringBuilder sb = new StringBuilder();
        
        ExecutionDescriptor descriptor = new ExecutionDescriptor().outConvertorFactory(() -> {
            return (LineConvertor) (String line) -> {
                sb.append(line).append("\n");
                return null;
            };
        });
        SequenceProcess both = new SequenceProcess(Enumerations.array(hello, world));
        ExecutionService process = ExecutionService.newService(() -> both, descriptor, javaHome);
        
        int status = process.run().get();
//        assertEquals(0, status);
        
        assertEquals("Hello\nWorld\n", sb.toString());
    }

}
