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

import javax.swing.Icon;
import javax.swing.UIManager;
import javax.swing.UIDefaults;
import javax.swing.LookAndFeel;
import javax.help.*;
import java.beans.*;

/**
 * JHelpGlossaryNavigator is a JHelpNavigator for a Glossary
 *
 * @author Roger Brinkley
 * @author Richard Gregor
 * @version   1.2     10/30/06
 */

public class JHelpGlossaryNavigator extends JHelpNavigator {

    /**
     * Creates JHelpGlossaryNavigator from given GlossaryView
     *
     * @param info The GlossaryView
     */
    public JHelpGlossaryNavigator(NavigatorView info) {
	super(info);
    }
    /**
     * Creates JHelpGlossaryNavigator from given GlossaryView and HelpModel
     *
     * @param info The GlossaryView
     * @param model The HelpModel
     */
    public JHelpGlossaryNavigator(NavigatorView info, HelpModel model) {
	super(info, model);
    }

    /**
     * Creates JHelpGlossaryNavigator from given HelpSet, name and title
     *
     * @param hs The HelpSet
     * @param name The name of GlossaryView
     * @param title The title
     */

    public JHelpGlossaryNavigator(HelpSet hs, String name, String title)
	throws InvalidNavigatorViewException
    {
	super(new GlossaryView(hs, name, title, null));
    }


    /**
     * Returns UIClassID
     *
     * @return The ID of UIClass representing JHelpGlossaryNavigator
     */
    public String getUIClassID() {
	return "HelpGlossaryNavigatorUI";
    }



    private static final boolean debug = false;
    private static void debug(Object m1, Object m2, Object m3) {
	if (debug) {
	    System.err.println("JHelpGlossaryNavigator: "+m1+m2+m3);
	}
    }
    private static void debug(Object m1) { debug(m1,null,null); }
    private static void debug(Object m1, Object m2) { debug(m1,m2,null); }
}
