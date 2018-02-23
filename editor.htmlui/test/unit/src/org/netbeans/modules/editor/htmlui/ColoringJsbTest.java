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
package org.netbeans.modules.editor.htmlui;

import java.io.OutputStream;
import javax.swing.text.StyledDocument;
import junit.framework.Test;
import org.netbeans.api.java.source.CompilationController;
import org.netbeans.api.java.source.JavaSource;
import org.netbeans.api.java.source.Task;
import org.netbeans.api.lexer.Token;
import org.netbeans.api.lexer.TokenHierarchy;
import org.netbeans.api.lexer.TokenSequence;
import org.netbeans.junit.NbModuleSuite;
import org.netbeans.junit.NbTestCase;
import org.openide.cookies.EditorCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;

public class ColoringJsbTest extends NbTestCase {

    public ColoringJsbTest(String name) {
        super(name);
    }

    public static Test suite() {
        return NbModuleSuite.createConfiguration(ColoringJsbTest.class).gui(false).suite();
    }

    public void testRun() throws Exception {
        FileSystem fs = FileUtil.createMemoryFileSystem();
        FileObject jsb = fs.getRoot().createData("JSB.java");
        String code = ""
            + "import net.java.html.js.JavaScriptBody;\n"
            + "class JSB {\n"
            + "  @JavaScriptBody(args = {}, body= \"if (true) return 42;\")\n"
            + "  public static native void method();\n"
            + "}\n";
        try (OutputStream os = jsb.getOutputStream()) {
            os.write(code.getBytes());
        }

        EditorCookie ec = jsb.getLookup().lookup(EditorCookie.class);
        StyledDocument doc = ec.openDocument();
        assertNotNull("Document found", doc);
        final JavaSource source = JavaSource.forDocument(doc);
        assertNotNull("Source found", source);

        source.runUserActionTask(new Task<CompilationController>() {
            @Override
            public void run(CompilationController info) throws Exception {
                info.toPhase(JavaSource.Phase.RESOLVED);
                JSEmbeddingProvider.colorizeJSB(info);
            }
        }, false);

        TokenHierarchy<StyledDocument> h = TokenHierarchy.get(doc);
        TokenSequence<?> seq = h.tokenSequence();
        seq.moveStart();
        for (;;) {
            final Token<?> t = seq.token();
            if (t != null && t.text() != null && t.text().toString().contains("if (true) return 42")) {
                fail("Found string token, and we shouldn't: " + t.text() + "\n" + seq);
            }
            if (!seq.moveNext()) {
                break;
            }
        }
    }
}
