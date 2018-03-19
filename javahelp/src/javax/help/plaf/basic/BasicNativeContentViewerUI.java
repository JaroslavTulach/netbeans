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

package javax.help.plaf.basic;

import javax.help.*;
import javax.help.plaf.HelpContentViewerUI;
import javax.help.event.*;
import java.util.Vector;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Hashtable;
import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.TextUI;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLConnection;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import javax.help.Map.ID;
import org.jdesktop.jdic.browser.WebBrowser;

/**
 * A native UI for JHelpContentViewer using the native browser. 
 *
 * @author Roger Brinkley
 * @version   1.2     10/30/06
 */

public class BasicNativeContentViewerUI extends HelpContentViewerUI
implements HelpModelListener, TextHelpModelListener, PropertyChangeListener, Serializable {
    protected JHelpContentViewer theViewer;
    
    private static Dimension PREF_SIZE = new Dimension(200, 300);
    private static Dimension MIN_SIZE = new Dimension(80, 80);
    
    private WebBrowser html;
    private JViewport vp;
    
    public static ComponentUI createUI(JComponent x) {
        debug("createUI");
        return new BasicNativeContentViewerUI((JHelpContentViewer) x);
    }
    
    public BasicNativeContentViewerUI(JHelpContentViewer b) {
        debug("createUI - sort of");
    }
    
    public void installUI(JComponent c) {
        debug("installUI");
        theViewer = (JHelpContentViewer)c;
        theViewer.setLayout(new BorderLayout());
        
        // listen to property changes...
        theViewer.addPropertyChangeListener(this);
        
        TextHelpModel model = theViewer.getModel();
        if (model != null) {
            // listen to id changes...
            model.addHelpModelListener(this);
            // listen to highlight changes...
            model.addTextHelpModelListener(this);
        }
        
	html = new WebBrowser();
        html.getAccessibleContext().setAccessibleName(HelpUtilities.getString(HelpUtilities.getLocale(html), "access.contentViewer"));
	if (debug) {
	    html.setDebug(true);
	}
	/**
	 * html future additions
	 * add any listeners here
	 */

	// if the model has a current URL then set it
	if (model != null) {
	    URL url = model.getCurrentURL();
	    if (url != null) {
		html.setURL(url);
	    }
	}

        JScrollPane scroller = new JScrollPane();
        scroller.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.white,
        Color.gray));
        vp = scroller.getViewport();
        vp.add(html);
        vp.setBackingStoreEnabled(true);
        
        theViewer.add("Center", scroller);
    }
    
    public void uninstallUI(JComponent c) {
        debug("uninstallUI");
        JHelpContentViewer viewer = (JHelpContentViewer) c;
        viewer.removePropertyChangeListener(this);
	/**
	 * html future additions
	 * remove all html listeners here - if we add any
	 */
        TextHelpModel model = viewer.getModel();
        if (model != null) {
            model.removeHelpModelListener(this);
            model.removeTextHelpModelListener(this);
        }
        viewer.setLayout(null);
        viewer.removeAll();
    }
    
    public Dimension getPreferredSize(JComponent c) {
        return PREF_SIZE;
    }
    
    public Dimension getMinimumSize(JComponent c) {
        return MIN_SIZE;
    }
    
    public Dimension getMaximumSize(JComponent c) {
        // This doesn't seem right. But I'm not sure what to do for now
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }
    
    public void idChanged(HelpModelEvent e) {
        ID id = e.getID();
        URL url = e.getURL();
        TextHelpModel model = theViewer.getModel();
        debug("idChanged("+e+")");
        debug("  = " + id + " " + url);
        debug("  my helpModel: "+model);
        
        model.setDocumentTitle(null);
        
	/**
	 * html future additions
	 * if we were doing any highlighting of the search text
	 * code would be needed here remove all the highlights before
	 * the new page is displayed
	 */

	html.setURL(url);

        debug("done with idChanged");
    }
    
    private void rebuild() {
        debug("rebuild");
        TextHelpModel model = theViewer.getModel();
        if (model == null) {
            debug("rebuild-end: model is null");
            return;
        }

	/**
	 * html future additions
	 * if we were doing any highlighting the highlights would need
	 * to be removed here
	 */

        HelpSet hs = model.getHelpSet();
        // for glossary - not set homeID page - glossary is not synchronized
        if(theViewer.getSynch()){
            try {
                Map.ID homeID = hs.getHomeID();
                Locale locale = hs.getLocale();
                String name = HelpUtilities.getString(locale, "history.homePage");
                model.setCurrentID(homeID, name, (JHelpNavigator)null);
                html.setURL(model.getCurrentURL());
            } catch (Exception e) {
                // ignore
            }
        }
        debug("rebuild-end");
    }
    
    public void propertyChange(PropertyChangeEvent event) {
        debug("propertyChange: " + event.getPropertyName() + "\n\toldValue:" + event.getOldValue() + "\n\tnewValue:" + event.getNewValue());
        
        if (event.getSource() == theViewer) {
            String changeName = event.getPropertyName();
            if (changeName.equals("helpModel")) {
                TextHelpModel oldModel = (TextHelpModel) event.getOldValue();
                TextHelpModel newModel = (TextHelpModel) event.getNewValue();
                if (oldModel != null) {
                    oldModel.removeHelpModelListener(this);
                    oldModel.removeTextHelpModelListener(this);
                }
                if (newModel != null) {
                    newModel.addHelpModelListener(this);
                    newModel.addTextHelpModelListener(this);
                }
                rebuild();
            } else if (changeName.equals("font")) {
                debug("font changed");
                Font newFont = (Font)event.getNewValue();
		/**
		 * ~~
		 * Put font change handling code here
		 */
            }else if (changeName.equals("clear")) {
		/**
		 * html future additions
		 * do not know how to do this at the current time
		 */
                // a~~ html.setText("");
            }else if (changeName.equals("reload")) {
		html.refresh();
            }
        }
    }
    
    /**
     * Determines if highlights have changed.
     * Collects all the highlights and marks the presentation.
     *
     * @param e The TextHelpModelEvent.
     */
    public void highlightsChanged(TextHelpModelEvent e) {
        debug("highlightsChanged "+e);
 
	// if we do anything with highlighting it would need to
	// be handled here.
    }
    
    /**
     * For printf debugging.
     */
    private final static boolean debug = false;
    private static void debug(String str) {
        if (debug) {
            System.out.println("NativeContentViewerUI: " + str);
        }
    }
}
