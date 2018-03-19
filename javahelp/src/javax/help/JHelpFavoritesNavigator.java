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

import java.net.URL;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.Action;

/**
 * JHelpFavoritesNavigator is a JHelpNavigator for Favorites.
 *
 * All the tree navigation and selection has been delegated to the UI
 * where the JTree is created.
 *
 * @author Richard Gregor
 * @version   1.4     10/30/06
 */
public class JHelpFavoritesNavigator extends JHelpNavigator {
    
    /**
     * Creates an Index navigator.
     *
     * @param view The NavigatorView
     */
    public JHelpFavoritesNavigator(NavigatorView view) {
        super(view, null);
    }
    
    /**
     * Creates a Index navigator.
     *
     * @param view The NavigatorView.
     * @param model The model for the Navigator.
     */
    public JHelpFavoritesNavigator(NavigatorView view, HelpModel model) {
        super(view, model);
    }
            
    // HERE - label & Locale?
    // HERE - URL data vs Hashtable?
    /**
     * Creates an Index navigator with explicit arguments.  Note that this should not throw
     * an InvalidNavigatorViewException since we are implicitly passing the type.
     *
     * @param hs HelpSet
     * @param name The name identifying this HelpSet.
     * @param label The label to use (for this locale).
     * @param data The "data" part of the parameters, a URL location of the TOC data.
     */
    public JHelpFavoritesNavigator(HelpSet hs,
    String name, String label, URL data)
    throws InvalidNavigatorViewException {
        super(new FavoritesView(hs, name, label, createParams(data)));
    }
    
    /**
     * Gets the UID for this JComponent.
     */
    public String getUIClassID() {
        return "HelpFavoritesNavigatorUI";
    }
    
    /**
     * Determines if this instance of a JHelpNavigator can merge its data with another one.
     *
     * @param view The data to merge
     * @return Whether it can be merged
     *
     * @see merge(NavigatorView)
     * @see remove(NavigatorView)
     */
    public boolean canMerge(NavigatorView view) {
        return false;
    }
     
    /**
     * Sets state of navigation entry for given target to expanded. Non-empty entry is expanded. Empty entry is visible.
     *
     * @param target The target to expand
     */
    public void expandID(String target){
        firePropertyChange("expand"," ",target);
    }
    
    /**
     * Sets state of navigation entry for given target to collapsed if entry is visible. Parent is collapsed if entry is empty.
     *
     * @param target The target to collapse
     */
    public void collapseID(String target){
        firePropertyChange("collapse"," ",target);
    }
    
    /**
     * Returns an AddAction object for this navigator
     *
     */
    public Action getAddAction(){
        return this.getUI().getAddAction();
    }
    
    private static final boolean debug = false;
    private static void debug(String msg) {
        if (debug) {
            System.err.println("JHelpIndexNavigator: "+msg);
        }
    }
}



