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

package javax.help.event;

import java.net.URL;
import java.util.Vector;
import java.util.Enumeration;
import javax.help.HelpSet;
import javax.help.Map.ID;
import javax.help.JHelpNavigator;


/**
 * Notifies interested parties that a change in a
 * Help Model source has occurred.
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @author Richard Gregor
 * @version	1.16	03/10/99
 */

public class HelpModelEvent extends java.util.EventObject {
    private ID id;
    private URL url;
    private String historyName;
    private JHelpNavigator navigator;

    /**
     * Represents a change in the JavaHelp in the current ID or URL.
     * @see javax.help.JavaHelp
     * 
     * @param source The source for this event.
     * @param id The ID that has changed. Should be null if URL is specified.
     * @param url The URL that has changed. Should be null if ID is specified.
     * @throws IllegalArgumentException if source is null.
     * @throws IllegalArgumentException of both ID and URL are null.
     */
    public HelpModelEvent(Object source, ID id, URL url) {
	this(source,id,url,(String)null, (JHelpNavigator)null);
    }
    
    /**
     * Represents a change in the JavaHelp in the current ID or URL.
     * @see javax.help.JavaHelp
     * 
     * @param source The source for this event.
     * @param id The ID that has changed. Should be null if URL is specified.
     * @param url The URL that has changed. Should be null if ID is specified.
     * @param historyName The name of selected entry
     * @param navigator The JHelpNavigator
     * @throws IllegalArgumentException if source is null.
     * @throws IllegalArgumentException of both ID and URL are null.
     */
    public HelpModelEvent(Object source, ID id, URL url, String historyName, JHelpNavigator navigator){
        super(source);
	if ((id == null) && (url == null)) {
	  throw new IllegalArgumentException("ID or URL must not be null");
	}
        this.id = id;
        this.url = url;
        this.historyName = historyName;
        this.navigator = navigator;
    }
        

    /**
     * Creates a HelpModelEvent for highlighting.
     *
     * @param source The source for this event.
     * @param pos0 Start position.
     * @param pos1 End position.
     * @throws IllegalArgumentException if source is null.
     */
    public HelpModelEvent(Object source, int pos0, int pos1) {
	super (source);
	this.pos0 = pos0;
	this.pos1 = pos1;
    }
    /**
     * Returns the current ID in the HelpModel.
     * @return The current ID.
     */
    public ID getID() {
	return id;
    }

    /**
     * Returns the current URL in the HelpModel.
     * @return The current URL.
     */
    public URL getURL() {
	return url;
    }
    
    /**
     * Returns the name of this entry
     *
     * @return The entry name
     */
    public String getHistoryName() {
        return historyName;
    }

    /**
     * Returns the navigator of this entry
     *
     * @return The navigator name
     */
    public JHelpNavigator getNavigator() {
        return navigator;
    }
    
    private int pos0, pos1;

    // HERE - Review this highlighting; it is a different type of beast than the rest - epll
    /**
     * @return The start position of this (highlighting) event.
     */
    public int getPos0() {
	return pos0;
    }

    /**
     * @return The end position of this (highlighting) event.
     */
    public int getPos1() {
	return pos1;
    }
}
