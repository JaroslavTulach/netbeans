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

import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.Locale;
import javax.swing.UIManager;

/**
 *
 * @author Roger Brinkley
 * @version   1.3     10/30/06
 */
public class FavoritesAction extends AbstractHelpAction implements ActionListener {
    
    private static final String NAME = "FavoritesAction";
    private JHelpFavoritesNavigator favorites=null;
    private ActionListener favoritesActionListener=null;

    /** Creates new FavoritesAction */
    public FavoritesAction(Object control) {
        super(control, NAME);
        if (control instanceof JHelp) {
            JHelp help = (JHelp)control;
	    for (Enumeration e = help.getHelpNavigators(); e.hasMoreElements();) {
		JHelpNavigator nav = (JHelpNavigator)e.nextElement();
		if(nav instanceof JHelpFavoritesNavigator) {
		    favorites = (JHelpFavoritesNavigator)nav;
		    favoritesActionListener = favorites.getAddAction();
		}
	    }
            
            setEnabled(favoritesActionListener != null);
            
            putValue("icon", UIManager.getIcon(NAME + ".icon"));
            
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
	if (favoritesActionListener != null) {
	    favoritesActionListener.actionPerformed(event);
	}
    }

}
