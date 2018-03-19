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

import java.awt.*;
import java.awt.event.*;
import javax.help.*;
import javax.help.event.*;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Stack;
import javax.swing.*;

/**
 *
 * @author Stepan Marek
 * @version	1.4	10/30/06
 */
public class HomeAction extends AbstractHelpAction implements ActionListener {
    
    private static final String NAME = "HomeAction";
    
    /** Creates new HomeAction */
    public HomeAction(Object control) {
        super(control, NAME);
        
        putValue("icon", UIManager.getIcon(NAME + ".icon"));
        
        if (control instanceof JHelp) {
	    JHelp help = (JHelp) control;
	    Locale locale = null;
	    try {
		locale = help.getModel().getHelpSet().getLocale();
	    } catch (NullPointerException npe) {
		locale = Locale.getDefault();
	    }
            putValue("tooltip", HelpUtilities.getString(locale, "tooltip." + NAME));
            putValue("access", HelpUtilities.getString(locale, "access." + NAME));
        }
    }
    
    public void actionPerformed(java.awt.event.ActionEvent event) {
        try {
            JHelp help = (JHelp)getControl();
            HelpSet hs = help.getModel().getHelpSet();
            Map.ID homeID = hs.getHomeID();
            Locale locale = hs.getLocale();
            String string = HelpUtilities.getString(locale, "history.homePage");
            help.setCurrentID(homeID, string, help.getCurrentNavigator());
        } catch (Exception e) {
        }
    }
}
