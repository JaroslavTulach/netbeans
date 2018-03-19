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

import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.net.URL;
import java.util.Locale;
import javax.help.TOCItem;
import javax.help.TOCView;
import javax.help.Map;
import javax.help.HelpUtilities;
import javax.help.Map.ID;

/**
 * Basic cell renderer for TOC UI.
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @version   1.25     10/30/06
 */

public class BasicTOCCellRenderer extends DefaultTreeCellRenderer
{
    protected Map map;
    protected TOCView view;

    public BasicTOCCellRenderer(Map map) {
	this(map, null);
    }
	
    public BasicTOCCellRenderer(Map map, TOCView view) {
	super();
	this.map = map;
	this.view = view;
    }

    /**
      * Configures the renderer based on the components passed in.
      * Sets the value from messaging value with toString().
      * The foreground color is set based on the selection and the icon
      * is set based on on leaf and expanded.
      */
    public Component getTreeCellRendererComponent(JTree tree, Object value,
						  boolean sel,
						  boolean expanded,
						  boolean leaf, int row,
						  boolean hasFocus)
    {

        String stringValue = "";

        // variable hasFocus was private to DefaultTreeCellRenderer since jdk1.3
        try {
            this.hasFocus = hasFocus;
        } catch (IllegalAccessError e) {
        }

        TOCItem item
	    = (TOCItem) ((DefaultMutableTreeNode) value).getUserObject();

	if (item != null) {
	    stringValue = item.getName();
	}

	setText(stringValue);
	if (sel) {
	    setForeground(getTextSelectionColor());
	} else {
	    setForeground(getTextNonSelectionColor());
	}

	ImageIcon icon = null;
	if (item != null) {
	    ID id = item.getImageID();
	    if (id != null) {
		try {
		    URL url = map.getURLFromID(id);
		    icon = new ImageIcon(url);
		} catch (Exception e) {
		}
	    }
	}

	// Set the locale of this if there is a lang value
	if (item != null) {
	    Locale locale = item.getLocale();
	    if (locale != null) {
		setLocale(locale);
	    }
	}

	// determine which icon to display
	if (icon != null) {
	    setIcon(icon);
	} else if (leaf) {
	    setIcon(getLeafIcon());
	} else if (expanded) {
	    setIcon(getOpenIcon());
	} else {
	    setIcon(getClosedIcon());
	}
	    
	selected = sel;

	return this;
    }

    public Icon getLeafIcon() {
	Icon icon = null;
	if (view != null) {
	    ID id = view.getTopicImageID();
	    if (id != null) {
		try {
		    URL url = map.getURLFromID(id);
		    icon = new ImageIcon(url);
		    return icon;
		} catch (Exception e) {
		}
	    }
	}
	return super.getLeafIcon();
    }

    public Icon getOpenIcon() {
	Icon icon = null;
	if (view != null) {
	    ID id = view.getCategoryOpenImageID();
	    if (id != null) {
		try {
		    URL url = map.getURLFromID(id);
		    icon = new ImageIcon(url);
		    return icon;
		} catch (Exception e) {
		}
	    }
	}
	return super.getOpenIcon();
    }

    public Icon getClosedIcon() {
	Icon icon = null;
	if (view != null) {
	    ID id = view.getCategoryClosedImageID();
	    if (id != null) {
		try {
		    URL url = map.getURLFromID(id);
		    icon = new ImageIcon(url);
		    return icon;
		} catch (Exception e) {
		}
	    }
	}
	return super.getClosedIcon();
    }

}
