package org.netbeans.api.visual.widget;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.net.URL;
import java.net.URLStreamHandlerFactory;
import java.util.concurrent.Exchanger;
import java.util.function.Supplier;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.border.LineBorder;
import org.netbeans.junit.NbTestCase;
import org.openide.util.Lookup;

public class CompareTest extends NbTestCase {

    public CompareTest(String name) {
        super(name);
    }

    static {
        for (URLStreamHandlerFactory f : Lookup.getDefault().lookupAll(URLStreamHandlerFactory.class)) {
            URL.setURLStreamHandlerFactory(f);
            break;
        }
    }

    public void testDrawLine() throws Exception {
        assertScreens(with(CompareTest::createHelloWorld), with(CompareTest::createHelloWorld));
    }

    public static <T> T with(Supplier<T> compute) {
        try {
            Exchanger<T> value = new Exchanger<>();
            EventQueue.invokeLater(() -> {
                try {
                    value.exchange(compute.get());
                } catch (InterruptedException ex) {
                    throw new IllegalStateException(ex);
                }
            });
            return value.exchange(null);
        } catch (InterruptedException ex) {
            throw new IllegalStateException(ex);
        }
    }

    private static Scene createHelloWorld() {
        Scene s = new Scene();
        final LabelWidget l1 = new LabelWidget(s);
        s.addChild(l1);
        l1.setLabel("Hello");
        l1.setBackground(Color.RED);
        l1.setForeground(Color.GREEN);
        l1.setBorder(new LineBorder(Color.blue));
        l1.setPreferredLocation(new Point(100, 50));
        final LabelWidget l2 = new LabelWidget(s);
        s.addChild(l2);
        l2.setPreferredLocation(new Point(500, 500));
        l2.setLabel("World!");
        l2.setBackground(Color.YELLOW);
        l2.setBorder(new LineBorder(Color.BLACK));
        return s;
    }

    public static void assertScreens(Scene s1, Scene s2) throws Exception {
        Rectangle[] bounds = new Rectangle[1];
        EventQueue.invokeAndWait(() -> {
            JComponent c = s1.createView();
            JFrame f = new JFrame("test");
            f.getContentPane().setLayout(new FlowLayout());
            f.getContentPane().add(c);
            f.pack();
            f.setVisible(true);
            bounds[0] = c.getBounds();
        });
        HtmlScene.open(s2, bounds[0]);
        System.in.read();
        Thread.sleep(20000);
    }
}
