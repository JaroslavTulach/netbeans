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
import java.util.Vector;
import java.util.Enumeration;
import javax.help.event.*;
import javax.help.Map.ID;
import java.beans.*;

/**
 * The interface to the model of a JHelp that represents the 
 * HelpSet being presented to the user.
 * 
 * Note that a HelpSet can contain nested HelpSets within it; IDs
 * include both a String and the HelpSet to which the String applies.
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @author Richard Gregor
 * @version   1.27     10/30/06
 */
public interface HelpModel {
    /**
     * Sets the loaded (aka "top") HelpSet for this model.
     */
    public void setHelpSet(HelpSet hs);

    /**
     * Gets the loaded (aka "top") HelpSet for this model.
     */
    public HelpSet getHelpSet();

    /**
     * Sets the current ID relative to some HelpSet
     * HelpModelListeners and HelpVisitListeners are notified
     *
     * @param id the ID used to set
     * @exception InvalidHelpSetContextException The HelpSet of the ID is not
     * valid for the HelpSet currently loaded in the model
     */
    public void setCurrentID(ID id) throws InvalidHelpSetContextException;

    /**
     * Sets the current ID relative to some HelpSet
     * HelpModelListeners and HelpVisitListeners are notified
     *
     * @param id the ID used to set
     * @param historyName The name for history storage
     * @param navigator The JHelpNavigator
     * @exception InvalidHelpSetContextException The HelpSet of the ID is not
     * valid for the HelpSet currently loaded in the model
     */
    public void setCurrentID(ID id, String historyName, JHelpNavigator navigator) throws InvalidHelpSetContextException;
    
    /**
     * Gets the current ID.
     *
     * @return The current ID.
     */
    public ID getCurrentID();

    /**
     * Sets the current URL. 
     * HelpModelListeners are notified.
     * The current ID changes if there is a matching id for this URL
     *
     * @param The URL to set.
     */
    public void setCurrentURL(URL url);

    /**
     * Sets the current URL and the name wich will appear in history list.
     * HelpModelListeners are notified.
     * The current ID changes if there is a matching id for this URL
     *
     * @param url The URL to set.
     * @param historyName The name to set for history
     * @param navigator The JHelpNavigator
     */
    public void setCurrentURL(URL url, String historyName, JHelpNavigator navigator);
    
    /**
     * Returns The current URL.
     *
     * @return The current URL.
     */
    public URL getCurrentURL();

    /**
     * Adds a listener for the HelpModelEvent posted after the model has
     * changed.
     * 
     * @param l The listener to add.
     * @see javax.help.HelpModel#removeHelpModelListener
     */
    public void addHelpModelListener(HelpModelListener l);

    /**
     * Removes a listener previously added with <tt>addHelpModelListener</tt>
     *
     * @param l The listener to remove.
     * @see javax.help.HelpModel#addHelpModelListener
     */
    public void removeHelpModelListener(HelpModelListener l);

    /**
     * Adds a listener to monitor changes to the properties in this model
     *
     * @param l  The listener to add.
     */
    public void addPropertyChangeListener(PropertyChangeListener l);

    /**
     * Removes a listener monitoring changes to the properties in this model
     *
     * @param l  The listener to remove.
     */
    public void removePropertyChangeListener(PropertyChangeListener l);
}
