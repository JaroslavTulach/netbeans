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

/**
 * History Model for HelpModel
 *
 * @author  Richard Gregor
 * @version   1.3     10/30/06
 */

import java.awt.event.ActionListener;
import javax.help.event.*;
import java.io.Serializable;
import java.beans.*;
import java.util.Vector;
import javax.help.Map.ID;
import java.util.Enumeration;

/**
 * The interface to the history model.
 */
public interface HelpHistoryModel extends HelpModelListener, Serializable{
    /**
     * Adds a listener for the HelpHistoryModelEvent posted after the model has
     * changed.
     * 
     * @param l The listener to add.
     * @see javax.help.HelpHistoryModel#removeHelpHistoryModelListener
     */
    public void addHelpHistoryModelListener(HelpHistoryModelListener l);

    /**
     * Removes a listener previously added with <tt>addHelpHistoryModelListener</tt>
     *
     * @param l The listener to remove.
     * @see javax.help.HelpHistoryModel#addHelpHistoryModelListener
     */
    public void removeHelpHistoryModelListener(HelpHistoryModelListener l);
       
    /**
     * Discards a history
     */
    public void discard();
    /**
     * Sets a next history entry
     */
    public void goForward();
    
    /**
     * Sets a previous history entry
     */
    public void goBack();
    
    /**
     * Returns a backward history list
     */
    public Vector getBackwardHistory();
    
    /**
     * Returns a forward history list
     */
    public Vector getForwardHistory();
    
    /**
     * Sets the current history entry
     *
     * @param index The index of history entry
     */
    public void setHistoryEntry(int index);
    
    /**
     * Removes entries related to removed HelpSet from history
     *
     * @param hs The removed HelpSet
     */
    public void removeHelpSet(HelpSet hs);
    
    /**
     * Returns a history
     */
    public Vector getHistory();
    
    /**
     * Returns a current history position
     *
     * @return The history index
     */
    public int getIndex();
    
    /**
     * Sets the HelpModel
     *
     * @param model The HeplModel
     */
    public void setHelpModel(HelpModel model);
    

}

