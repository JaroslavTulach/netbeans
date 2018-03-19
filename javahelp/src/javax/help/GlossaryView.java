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

import java.awt.Component;
import java.util.Hashtable;
import java.util.Locale;
import javax.help.HelpSet;
import javax.help.HelpModel;
import javax.help.IndexView;

/**
 * View information for a Glossary Navigator
 *
 * @author	Roger Brinkley
 * @version   1.2     10/30/06
 */

public class GlossaryView extends IndexView {
    /**
     * Construct a GlossaryView with some given data.  Locale defaults
     * to that of the HelpSet
     *
     * @param hs The HelpSet that provides context information
     * @param name The name of the View
     * @param label The label (to show the user) of the View
     * @param params A Hashtable providing different key/values for this type
     */
    public GlossaryView(HelpSet hs,
			String name,
			String label,
			Hashtable params) {
	super(hs, name, label, hs.getLocale(), params);
    }

    /**
     * Construct a GlossaryViewer VIew with some given data.
     *
     * @param hs The HelpSet that provides context information
     * @param name The name of the View
     * @param label The label (to show the user) of the View
     * @param locale The default locale to interpret data in this View
     * @param params A Hashtable providing different key/values for this type
     */
    public GlossaryView(HelpSet hs,
			String name,
			String label,
			Locale locale,
			Hashtable params) {
	super(hs, name, label, locale, params);
    }

    /**
     * create a navigator for a given model
     */
    public Component createNavigator(HelpModel model) {
	return new JHelpGlossaryNavigator(this, model);
    }
}




