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

import java.util.Hashtable;

/**
 * MainWindowPresentation is a class that will create a single main help 
 * window for an application. Although there is generally only one per
 * application there can  be multiple MainWindowPresentation.
 * By default it is a tri-paned fully decorated window
 * consisting of a toolbar, navigator pane, and help content view. By default
 * the class is not destroyed when the window exits.
 *
 * @author Roger D.Brinkley
 * @version	1.5	10/30/06
 * @since 2.0
 *
 * @see javax.help.WindowPresentation
 * @see javax.help.Presentation
 */

public class SecondaryWindow extends WindowPresentation {

    static private Hashtable windows = new Hashtable();
    private String name;

    private SecondaryWindow(HelpSet hs, String name) {
	super(hs);
	this.name = name;
    }

    /**
     * Get a named SecondaryWindow for a given HelpSet.
     * Named SecondaryWindows are stored. If a named 
     * SecondaryWindow exits then it is returned, otherwise a new
     * secondary window is created. If there is a HelpSet.Presentation of the
     * same name the presentation attibutes will be used, otherwise, the 
     * default HelpSet.Presentation is used.
     * 
     * @param hs The HelpSet used in this presentation
     * @param name The name of the Presentation to create - also the name
     *             of the HelpSet.Presentation to use.
     * @returns Presentation A unique MainWindowPresentation. 
     */
    static public Presentation getPresentation(HelpSet hs, String name) {
	debug ("getPresentation");
	SecondaryWindow swp;

	String winName = name;
	if (name == null) {
	    winName = "";
	}

	// Use the secondary window if one exists
	swp = (SecondaryWindow) windows.get(winName);
	if (swp != null) {
	    if (swp.getHelpSet() != hs) {
		swp.setHelpSet(hs);
	    }
	    return swp;
	}

	debug ("no Presentation - start again");
	swp = new SecondaryWindow(hs, winName);

	// Set the SecondaryWindow defaults
	swp.setViewDisplayed(false);
	swp.setToolbarDisplayed(false);
	swp.setDestroyOnExit(true);
	swp.setTitleFromDocument(true);

	if (hs != null) {
	    HelpSet.Presentation presentation = null;

	    // get a named presentation if one exists
	    if (name != null) {
		presentation = hs.getPresentation(name);
	    }

	    // get the default presentation if one exits
	    if (presentation == null) {
		presentation = hs.getDefaultPresentation();
	    }

	    // set the presentation
	    // a null is ok here as it will just return.
	    swp.setHelpSetPresentation(presentation);

	    windows.put(winName, swp);
	}
	return swp;
    }

    /**
     * Gets a SecondaryWindow if one exists. Does not 
     * create a Presentation if one does not exist.
     * 
     * @param name Name of the presentation to get
     * @return SecondaryWindow The found Presentation or null
     */
    static public SecondaryWindow getPresentation(String name) {
	debug ("getPresenation(name)");
	return (SecondaryWindow) windows.get(name);
    }

    /**
     * Destroy the SecondaryWindowPresentatin. Specifically remove it from
     * the list of SecondaryWindows.
     */
    public void destroy() {
	super.destroy();
	windows.remove(name);
    }

    /**
     * Debugging code...
     */

    private static final boolean debug = false;
    private static void debug(Object msg) {
	if (debug) {
	    System.err.println("SecondaryWindow: "+msg);
	}
    }
 
}
