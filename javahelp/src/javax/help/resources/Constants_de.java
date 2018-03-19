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
 * Constants used for localizing JavaHelp
 *
 * These are in the form of key, value.
 * Translators take care to only translate the values.
 */

public class Constants_de extends ListResourceBundle {
    /**
     * Overrides ListResourceBundle
     */
    public Object[][] getContents() {
        return new Object[][] {
	    //  Constant strings and patterns
	    { "helpset.wrongPublicID",
		  "Unbekannte PublicID {0}"},
	    { "helpset.wrongTitle",
		  "Versuche, Titel auf {0} zu setzen, aber Wert {1} ist schon gesetzt."},
	    { "helpset.wrongHomeID",
		  "Versuche HomeID auf {0} zu setzen, aber Wert {1} ist schon gesetzt."},
	    { "helpset.subHelpSetTrouble",
		  "Probleme beim Erzeugen des Subhelpset: {0}."},
	    { "helpset.malformedURL",
		  "Formfehler in URL: {0}."},
	    { "helpset.incorrectURL",
		  "Fehlerhafte URL: {0}."},
	    { "helpset.wrongText",
		  "{0} darf nicht den Text {1} enthalten."},
	    { "helpset.wrongTopLevel",
		  "{0} darf kein Top Level Tag sein."},
	    { "helpset.wrongParent",
		  "Parent Tag f\u00fcr {0} darf nicht {1} sein."},
	    { "helpset.unbalanced",
		  "Einseitiger Tag {0}."},
	    { "helpset.wrongLocale",
		  "Warning: xml:lang-Attribut {0} widerspricht Voreinstellung {1} und Voreinstellung {2}"},
	    { "helpset.unknownVersion",
		  "Unbekannte Version {0}."},

		// IndexView messages
	    { "index.invalidIndexFormat",
		  "Warnung: Ung\u00fcltiges Index-Format"},
	    { "index.unknownVersion",
		  "Unbekannte Version {0}."},

		// TOCView messages
	    { "toc.wrongPublicID",
		  "Unbekannte PublicID {0}"},
	    { "toc.invalidTOCFormat",
		  "Warnung: Ung\u00fcltiges Format f\u00fcr Inhaltsverzeichnis"},
	    { "toc.unknownVersion",
		  "Unbekannte Version {0}."},

            // FavoritesView messages
	    { "favorites.invalidFavoritesFormat",
		  "Warnung: Ung\u00fcltiges Favorites-Format"},
	    { "favorites.unknownVersion",
		  "Unbekannte Version {0}."},

		// Map messages
	    { "map.wrongPublicID",
		  "Unbekannte PublicID {0}"},
	    { "map.invalidMapFormat",
		  "Warnung: Ung\u00fcltiges Map-Format"},
	    { "map.unknownVersion",
		  "Unbekannte Version {0}."},


	    // GUI components
	    // Labels
	    { "index.findLabel", "Suche: "},

	    { "search.findLabel", "Suche: "},
	    { "search.hitDesc", "Number of occurances in document"},
	    { "search.qualityDesc", "Lowest penality value in document" },
	    { "search.high", "High"},
	    { "search.midhigh", "Medium high"},
	    { "search.mid", "Medium"},
	    { "search.midlow", "Medium low"},
	    { "search.low", "Low"},
            
            { "favorites.add", "Hinzuf\u00fcgen"},
            { "favorites.remove", "Entfernem"},
            { "favorites.folder", "Neuer Ordner"},
            { "favorites.name", "Name"},
            { "favorites.cut", "Ausschneiden"},
            { "favorites.paste", "Einf\u00fcgen"},
            { "favorites.copy" , "Kopieren"},

            { "history.homePage", "Startseite"},
            { "history.unknownTitle", "<Unbekannter Titel>"},

	    // ToolTips
	    { "tooltip.BackAction", "Voriger"},
	    { "tooltip.ForwardAction", "N\u00e4chster"},
	    { "tooltip.PrintAction", "Drucken"},
	    { "tooltip.PrintSetupAction", "Seite einrichten"},
	    { "tooltip.ReloadAction", "Neu laden"},
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
