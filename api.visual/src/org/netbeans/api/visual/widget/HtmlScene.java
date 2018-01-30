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
import java.awt.Color;
import java.awt.Composite;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Image;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Path2D;
import java.awt.geom.PathIterator;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ImageObserver;
import java.awt.image.RenderedImage;
import java.awt.image.renderable.RenderableImage;
import java.text.AttributedCharacterIterator;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JFrame;
import org.netbeans.api.htmlui.HTMLComponent;

final class HtmlScene {
    private final Scene scene;
    
    static void open(Scene scene, Rectangle bounds) {
        HtmlScene view = new HtmlScene(scene);
        JComponent c = Pages.initializeCanvas(view, bounds);
        JFrame f = new JFrame();
        f.getContentPane().setLayout(new BorderLayout());
        f.getContentPane().add(c, BorderLayout.CENTER);
        if (bounds != null) {
            f.setSize(bounds.getSize());
        }
        f.setVisible(true);
    }

    @HTMLComponent(url = "canvas.html", type = JComponent.class)
    static void initializeCanvas(HtmlScene canvas, Rectangle bounds) {
        GraphicsContext2D context = GraphicsContext2D.getOrCreate("scene");
        final Graphics2D g = DelGr.register(canvas.scene.getGraphics(), "[web]", new WebGraphics2D(context));
        canvas.scene.setGraphics(g);
        if (bounds != null) {
            canvas.scene.setPreferredBounds(bounds);
        }
        canvas.scene.layout(true);
        canvas.scene.paint(g);
    }

    private HtmlScene(Scene scene) {
        this.scene = scene;
    }

    private static class WebGraphics2D extends Graphics2D implements Cloneable {
        private final GraphicsContext2D d;
        private Paint paint;
        private Stroke stroke;
        private Font font;

        public WebGraphics2D(GraphicsContext2D d) {
            this.d = d;
        }

        @Override
        protected WebGraphics2D clone()  {
            try {
                return (WebGraphics2D)super.clone();
            } catch (CloneNotSupportedException ex) {
                throw new IllegalStateException();
            }
        }

        @Override
        public void draw(Shape s) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean drawImage(Image img, AffineTransform xform, ImageObserver obs) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawImage(BufferedImage img, BufferedImageOp op, int x, int y) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawRenderedImage(RenderedImage img, AffineTransform xform) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawRenderableImage(RenderableImage img, AffineTransform xform) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawString(String str, int x, int y) {
            this.d.setTextBaseline("ideographic");
            this.d.fillText(str, x, y);
        }

        @Override
        public void drawString(String str, float x, float y) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawString(AttributedCharacterIterator iterator, int x, int y) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawString(AttributedCharacterIterator iterator, float x, float y) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawGlyphVector(GlyphVector g, float x, float y) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void fill(Shape s) {
            if (s instanceof Path2D) {
                Path2D p = (Path2D) s;
                double[] to = new double[6];
                d.beginPath();
                for (PathIterator it = p.getPathIterator(new AffineTransform()); !it.isDone(); it.next()) {
                    switch (it.currentSegment(to)) {
                        case PathIterator.SEG_CLOSE:
                            d.closePath();
                            break;
                        case PathIterator.SEG_MOVETO:
                            d.moveTo(to[0], to[1]);
                            break;
                        case PathIterator.SEG_LINETO:
                            d.lineTo(to[0], to[1]);
                            break;
                        default:
                            throw new IllegalStateException("Unknown type: " + it.currentSegment(to));
                    }
                }
                d.stroke();
                return;
            }
            Rectangle r = s.getBounds();
            d.fillRect(r.x, r.y, r.width, r.height);
        }

        @Override
        public boolean hit(Rectangle rect, Shape s, boolean onStroke) {
            throw new UnsupportedOperationException();
        }

        @Override
        public GraphicsConfiguration getDeviceConfiguration() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setComposite(Composite comp) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setPaint(Paint paint) {
            this.paint = paint;
            if (paint instanceof Color) {
                setColor((Color) paint);
            } else {
                throw new IllegalArgumentException("unknown: " + paint);
            }
        }

        @Override
        public void setStroke(Stroke s) {
            this.stroke = s;
        }

        @Override
        public void setRenderingHint(RenderingHints.Key hintKey, Object hintValue) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object getRenderingHint(RenderingHints.Key hintKey) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setRenderingHints(Map<?, ?> hints) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void addRenderingHints(Map<?, ?> hints) {
            throw new UnsupportedOperationException();
        }

        @Override
        public RenderingHints getRenderingHints() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void translate(int x, int y) {
            tx.translate(x, y);
            setTransform(tx);
        }

        @Override
        public void translate(double tx, double ty) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void rotate(double theta) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void rotate(double theta, double x, double y) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void scale(double sx, double sy) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void shear(double shx, double shy) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void transform(AffineTransform Tx) {
            if (this.tx != null) {
                this.tx.preConcatenate(Tx);
                setTransform(tx);
            } else {
                this.tx = Tx;
                setTransform(tx);
            }
        }


        private AffineTransform tx = new AffineTransform();
        @Override
        public void setTransform(AffineTransform tx) {
            tx.getClass();
            this.tx = tx;
            d.setTransform(
                tx.getScaleX(),
                tx.getShearX(),
                tx.getShearY(),
                tx.getScaleY(),
                tx.getTranslateX(),
                tx.getTranslateY()
            );
        }

        @Override
        public AffineTransform getTransform() {
            return this.tx == null ? null : new AffineTransform(this.tx);
        }

        @Override
        public Paint getPaint() {
            return this.paint;
        }

        @Override
        public Composite getComposite() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setBackground(Color color) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Color getBackground() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Stroke getStroke() {
            return this.stroke;
        }

        @Override
        public void clip(Shape s) {
            if (clip == null) {
                clip = s;
            } else {
                Rectangle2D r1 = clip.getBounds2D();
                Rectangle2D r2 = s.getBounds2D();
                clip = r1.createIntersection(r2);
            }
        }

        @Override
        public FontRenderContext getFontRenderContext() {
            return new FontRenderContext() {
            };
        }

        @Override
        public Graphics create() {
            return clone();
        }

        @Override
        public Color getColor() {
            return color;
        }

        private Color color;
        @Override
        public void setColor(Color c) {
            this.color = c;
            if (c != null) {
                Style.Color web = new Style.Color(toWebColor(c));
                this.d.setStrokeStyle(web);
                this.d.setFillStyle(web);
            }
        }

        private String toWebColor(Color c) {
            return "rgb(" + c.getRed() + "," + c.getGreen() + "," + c.getBlue() + ")";
        }

        @Override
        public void setPaintMode() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void setXORMode(Color c1) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Font getFont() {
            return this.font;
        }

        @Override
        public void setFont(Font font) {
            this.font = font;
        }

        @Override
        public FontMetrics getFontMetrics(Font f) {
            return new FontMetrics(f) {
            };
        }

        @Override
        public Rectangle getClipBounds() {
            return clip == null ? null : clip.getBounds();
        }

        @Override
        public void clipRect(int x, int y, int width, int height) {
            clip(new Rectangle(x, y, width, height));
        }

        @Override
        public void setClip(int x, int y, int width, int height) {
            clip = new Rectangle(x, y, width, height);
        }

        private Shape clip;
        @Override
        public Shape getClip() {
            return this.clip;
        }

        @Override
        public void setClip(Shape clip) {
            this.clip = clip;
        }

        @Override
        public void copyArea(int x, int y, int width, int height, int dx, int dy) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawLine(int x1, int y1, int x2, int y2) {
            d.beginPath();
            d.moveTo(x1, y1);
            d.lineTo(x2, y2);
            d.stroke();
        }

        @Override
        public void fillRect(int x, int y, int width, int height) {
            d.fillRect(x, y, width, height);
        }

        @Override
        public void clearRect(int x, int y, int width, int height) {
            d.clearRect(x, y, width, height);
        }

        @Override
        public void drawRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void fillRoundRect(int x, int y, int width, int height, int arcWidth, int arcHeight) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawOval(int x, int y, int width, int height) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void fillOval(int x, int y, int width, int height) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void fillArc(int x, int y, int width, int height, int startAngle, int arcAngle) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawPolyline(int[] xPoints, int[] yPoints, int nPoints) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void drawPolygon(int[] xPoints, int[] yPoints, int nPoints) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void fillPolygon(int[] xPoints, int[] yPoints, int nPoints) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean drawImage(Image img, int x, int y, ImageObserver observer) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean drawImage(Image img, int x, int y, int width, int height, ImageObserver observer) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean drawImage(Image img, int x, int y, Color bgcolor, ImageObserver observer) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean drawImage(Image img, int x, int y, int width, int height, Color bgcolor, ImageObserver observer) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, ImageObserver observer) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2, Color bgcolor, ImageObserver observer) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void dispose() {
        }

    }
}
