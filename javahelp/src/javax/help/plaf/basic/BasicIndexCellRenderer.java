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

package javax.help.plaf.basic;

import javax.help.*;
import javax.swing.tree.*;
import javax.swing.JTree;
import java.awt.Component;
import java.util.Locale;

/**
 * Cell Renderer for the index UI.
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @author Stepan Marek
 * @version   %I     10/30/06
 */
public class BasicIndexCellRenderer extends DefaultTreeCellRenderer {
    
    /**
      * Configures the renderer based on the passed in components.
      * The value is set from messaging the tree with
      * <code>convertValueToText</code>, which ultimately invokes
      * <code>toString</code> on <code>value</code>.
      * The foreground color is set based on the selection and the icon
      * is set based on on leaf and expanded.
      */
    public Component getTreeCellRendererComponent(JTree tree,
                                                  Object value,
						  boolean sel,
						  boolean expanded,
						  boolean leaf, int row,
						  boolean hasFocus) {

        // variable hasFocus was private to DefaultTreeCellRenderer since jdk1.3
        try {
            this.hasFocus = hasFocus;
        } catch (IllegalAccessError e) {
        }
        
    	IndexItem item
	    = (IndexItem) ((DefaultMutableTreeNode) value).getUserObject();

	String stringValue = "";

	if (item != null) {
	    stringValue = item.getName();
	}

        setText(stringValue);
        if (sel)
            setForeground(getTextSelectionColor());
        else
            setForeground(getTextNonSelectionColor());

        setIcon(null);
        
        selected = sel;

	// Set the locale of this if there is a lang value
	if (item != null) {
	    Locale locale = item.getLocale();
	    if (locale != null) {
		setLocale(locale);
	    }
	}

	return this;
    }
    
}
