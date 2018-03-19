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
 * MainWindow is a class that will create a single main help 
 * window for an application. Although there is generally only one per
 * application there can  be multiple MainWindow.
 * By default it is a tri-paned fully decorated window
 * consisting of a toolbar, navigator pane, and help content view. By default
 * the class is not destroyed when the window exits.
 *
 * @author Roger D.Brinkley
 * @version	1.3	10/30/06
 * @since 2.0
 *
 * @see javax.help.WindowPresentation
 * @see javax.help.Presentation
 */

public class MainWindow extends WindowPresentation {

    private MainWindow(HelpSet hs) {
	super(hs);
    }

    /**
     * Creates a new MainWindow for a given HelpSet and 
     * HelpSet.Presentation "name". If the "name"d HelpSet.Presentation
     * does not exist in HelpSet then the default HelpSet.Presentation
     * is used.
     * 
     * @param hs The HelpSet used in this presentation
     * @param name The name of the Presentation to create - also the name
     *             of the HelpSet.Presentation to use.
     * @returns Presentation A unique MainWindow. 
     */
    static public Presentation getPresentation(HelpSet hs, String name) {
	MainWindow mwp = new MainWindow(hs);
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
	    mwp.setHelpSetPresentation(presentation);
	}
	return mwp;
    }

    /**
     * Debugging code...
     */

    private static final boolean debug = false;
    private static void debug(Object msg) {
	if (debug) {
	    System.err.println("MainWindow: "+msg);
	}
    }
 
}


