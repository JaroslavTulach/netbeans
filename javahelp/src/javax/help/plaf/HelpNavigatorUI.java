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

package javax.help.plaf;

import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.*;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.help.HelpModel;
import javax.help.event.HelpModelListener;
import javax.help.event.HelpModelEvent;
import javax.help.NavigatorView;
import javax.help.Map;
import java.net.URL;
import javax.swing.Action;
import javax.swing.AbstractAction;

/**
 * UI factory interface for JHelpNavigator.
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @author Stepan Marek
 * @version   %I     10/30/06
 */

public abstract class HelpNavigatorUI extends ComponentUI {

    private Icon icon;

    /**
     * Sets the icon for this HelpNavigator.
     *
     * @param icon the Icon
     */
    public void setIcon(Icon icon) {
	this.icon = icon;
    }

    /**
     * @return the Icon for this HelpNavigator
     */
    public Icon getIcon() {
	return icon;
    }

    /**
     * Merges a Navigator View.
     */
    public void merge(NavigatorView view){
        throw new UnsupportedOperationException("merge is not supported");
    }

    /**
     * Removes a Navigator View.
     */
    public void remove(NavigatorView view){
        throw new UnsupportedOperationException("remove is not supported");
    }
    
    /**
     * Returns icon associated with the view.
     *
     * @param view the view
     * @return the ImageIcon for the view
     */
    public ImageIcon getImageIcon(NavigatorView view) {
        ImageIcon icon = null;
        Map.ID id = view.getImageID();
        if (id != null) {
            try {
                Map map = view.getHelpSet().getCombinedMap();
                URL url = map.getURLFromID(id);
                icon = new ImageIcon(url);
		} catch (Exception e) {
		}
        }
        return icon;
    }
    
    /**
     * Returns an AddAction object. Has sense only for favorites navigator
     */
    public Action getAddAction(){
        throw new UnsupportedOperationException("getAddAction is not supported");
        //return (Action)null;
    }
}
