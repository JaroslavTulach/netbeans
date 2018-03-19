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

package javax.help.resources;

import java.util.ListResourceBundle;

/**
 * Constants used for localizing JavaHelp.
 *
 * These are in the form of key, value.
 * Translators take care to only translate the values.
 */

public class Constants extends ListResourceBundle {
    /**
     * Overrides ListResourceBundle.
     */
    public Object[][] getContents() {
        return new Object[][] {

	    //  Constant strings and patterns
	    { "helpset.wrongPublicID",
		  "Unknown PublicID {0}"},
	    { "helpset.wrongTitle",
		  "Attempting to set title to {0} but already has value {1}."},
	    { "helpset.wrongHomeID",
		  "Attempting to set homeID to {0} but already has value {1}."},
	    { "helpset.subHelpSetTrouble",
		  "Trouble creating subhelpset: {0}."},
	    { "helpset.malformedURL",
		  "Malformed URL: {0}."},
	    { "helpset.incorrectURL",
		  "Incorrect URL: {0}."},
	    { "helpset.wrongText",
		  "{0} cannot contain text {1}."},
	    { "helpset.wrongTopLevel",
		  "{0} cannot be a top level tag."},
	    { "helpset.wrongParent",
		  "The parent tag for {0} cannot be {1}."},
	    { "helpset.unbalanced",
		  "Unbalanced tag {0}."},
	    { "helpset.wrongLocale",
		  "Warning: xml:lang attribute {0} conflicts with default {1} and with default {2}"},
	    { "helpset.unknownVersion",
		  "Unknown Version {0}."},

		// IndexView messages
	    { "index.invalidIndexFormat",
		  "Warning: Invalid Index format"},
	    { "index.unknownVersion",
		  "Unknown Version {0}."},

		// TOCView messages
	    { "toc.wrongPublicID",
		  "Unknown PublicID {0}"},
	    { "toc.invalidTOCFormat",
		  "Warning: Invalid TOC format"},
	    { "toc.unknownVersion",
		  "Unknown Version {0}."},
                  
            // FavoritesView messages
	    { "favorites.invalidFavoritesFormat",
		  "Warning: Invalid Favorites format"},
	    { "favorites.unknownVersion",
		  "Unknown Version {0}."},

		// Map messages
	    { "map.wrongPublicID",
		  "Unknown PublicID {0}"},
	    { "map.invalidMapFormat",
		  "Warning: Invalid Map format"},
	    { "map.unknownVersion",
		  "Unknown Version {0}."},

	    // GUI components
	    // Labels
	    { "index.findLabel", "Find: "},
            
	    { "search.findLabel", "Find: "},
	    { "search.hitDesc", "Number of occurances in document"},
	    { "search.qualityDesc", "Lowest penality value in document" },
	    { "search.high", "High"},
	    { "search.midhigh", "Medium high"},
	    { "search.mid", "Medium"},
	    { "search.midlow", "Medium low"},
	    { "search.low", "Low"},
            
            { "favorites.add", "Add"},
            { "favorites.remove", "Remove"},
            { "favorites.folder", "New Folder"},
            { "favorites.name", "Name"},
            { "favorites.cut", "Cut"},
            { "favorites.paste", "Paste"},
            { "favorites.copy" , "Copy"},

            { "history.homePage", "Home page"},
            { "history.unknownTitle", "<unknown page title>"},

            // ToolTips for Actions
            { "tooltip.BackAction", "Previous Page"},
	    { "tooltip.ForwardAction", "Next Page"},
	    { "tooltip.PrintAction", "Print"},
	    { "tooltip.PrintSetupAction", "Page Setup"},
	    { "tooltip.ReloadAction", "Reload"},
            { "tooltip.FavoritesAction", "Add to Favorites"},
            { "tooltip.HomeAction", "Go to home page"},

	    // Accessibility names
	    { "access.BackAction", "Previous Button"},
	    { "access.ForwardAction", "Next Button"},
	    { "access.HistoryAction", "History Button"},
	    { "access.PrintAction", "Print Button"},
	    { "access.PrintSetupAction", "Page Setup Button"},
	    { "access.ReloadAction", "Reload Button"},
            { "access.HomeAction", "Home Button"},
            { "access.FavoritesAction", "Add to Favorites Button"},
            { "access.contentViewer", "Content Viewer"}

       };
    }
}
