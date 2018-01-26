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

import com.dukescript.api.canvas.GraphicsContext2D;
import com.dukescript.api.canvas.Style;
import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import org.netbeans.api.htmlui.HTMLComponent;

final class HtmlScene {
    private final Scene scene;
    
    static void open(Scene scene) {
        HtmlScene view = new HtmlScene(scene);
        JComponent c = Pages.initializeCanvas(view);
        JFrame f = new JFrame();
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(c, BorderLayout.CENTER);
        f.pack();
        f.setVisible(true);
    }

    @HTMLComponent(url = "canvas.html", type = JComponent.class)
    static void initializeCanvas(HtmlScene canvas) {
        GraphicsContext2D context = GraphicsContext2D.getOrCreate("scene");
        context.setFillStyle(new Style.Color("#ff0000"));
        context.fillRect(10, 10, 20, 20);
        context.stroke();
    }

    private HtmlScene(Scene scene) {
        this.scene = scene;
    }

}
