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
package org.netbeans.api.visual.widget;

import java.awt.Component;
import java.awt.Frame;
import java.awt.Rectangle;
import javax.swing.JComponent;
import javax.swing.JFrame;
import org.netbeans.api.htmlui.OpenHTMLRegistration;
import org.openide.util.NbBundle;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;

/**
 * Finds a {@link Scene} in the currently selected window and displays it
 * in an HTML canvas.
 */
public final class HtmlSceneCntrl {
    @ActionID(
            category = "Tools",
            id = "org.netbeans.api.visual.widget.HtmlScene"
    )
    @ActionReferences({
        @ActionReference(path = "Menu/Tools"),
        @ActionReference(path = "Toolbars/File"),})
    @NbBundle.Messages("CTL_HtmlScene=Open HTML Scene")
    @OpenHTMLRegistration(
            url = "canvas.html",
            displayName = "#CTL_HtmlScene"
    )
    public static HtmlScene onPageLoad() {
        Scene s = null;
        Rectangle[] rb = { null };
        for (Frame f : JFrame.getFrames()) {
            if (f instanceof JFrame) {
                JFrame jf = (JFrame) f;
                s = findScene(jf.getContentPane(), rb);
                if (s != null) {
                    break;
                }
            }
        }
        HtmlScene.initializeCanvas(s, rb[0]);
        return null;
    }

    private static Scene findScene(Component c, Rectangle[] rb) {
        if (c instanceof SceneComponent) {
            SceneComponent sc = (SceneComponent) c;
            rb[0] = sc.getBounds();
            return sc.getScene();
        }
        if (c instanceof JComponent) {
            for (Component ch : ((JComponent) c).getComponents()) {
                Scene s = findScene(ch, rb);
                if (s != null) {
                    return s;
                }
            }
        }
        return null;
    }
}
