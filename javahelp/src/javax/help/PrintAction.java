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

package javax.help;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;
import java.util.Locale;
import javax.swing.UIManager;
import com.sun.java.help.impl.JHelpPrintHandler;

/**
 *
 * @author Stepan Marek
 * @version	1.3	10/30/06
 */
public class PrintAction extends AbstractHelpAction implements PropertyChangeListener, ActionListener {

    private static final String NAME = "PrintAction";
    
    private JHelpPrintHandler handler = null;

    /** Creates new BackAction */
    public PrintAction(Object control) {
        
        super(control, NAME);
        
        if (control instanceof JHelp) {
            
            JHelp help = (JHelp)control;
            
            handler = JHelpPrintHandler.getJHelpPrintHandler(help);
            handler.addPropertyChangeListener(this);
            
	    Locale locale = null;
	    try {
		locale = help.getModel().getHelpSet().getLocale();
	    } catch (NullPointerException npe) {
		locale = Locale.getDefault();
	    }
            putValue("tooltip", HelpUtilities.getString(locale, "tooltip." + NAME));
            putValue("access", HelpUtilities.getString(locale, "access." + NAME));

        }
        
        putValue("icon", UIManager.getIcon(NAME + ".icon"));
        
    }

    public void actionPerformed(ActionEvent event) {
        if (handler != null) {
            JHelp help = (JHelp)getControl();
            URL[] urls = null;
            TreeItem[] items = help.getSelectedItems();
            if (items != null) {
                urls = new URL[items.length];
                for (int i = 0; i < items.length; i++) {
                    urls[i] = items[i].getURL();
                }
            }
            if ((urls != null) && (urls.length > 0)) {
                handler.print(urls);
            } else {
                handler.print(help.getModel().getCurrentURL());
            }
        }
    }
    
        
    /**
     * This method gets called when a bound property is changed.
     * @param evt A PropertyChangeEvent object describing the event source
     *  	and the property that has changed.
     */
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("enabled")) {
            setEnabled(((Boolean)evt.getNewValue()).booleanValue());
        }
    }
}
