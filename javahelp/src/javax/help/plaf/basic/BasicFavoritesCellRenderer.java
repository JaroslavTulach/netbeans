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

/**
 * Cell Renderer for the favorites UI.
 *
 * @author Richard Gregor
 * @version	1.4	10/30/06
 */
import javax.swing.*;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.awt.*;
import java.net.URL;
import java.util.Locale;
import javax.help.TOCItem;
import javax.help.Map;
import javax.help.HelpUtilities;
import javax.help.Map.ID;
import javax.help.FavoritesItem;

public class BasicFavoritesCellRenderer extends DefaultTreeCellRenderer {
    
    public Component getTreeCellRendererComponent(JTree tree, Object value,
    boolean sel,
    boolean expanded,
    boolean leaf, int row,
    boolean hasFocus) {
        
        FavoritesItem item;
        
        Object o = ((DefaultMutableTreeNode)value).getUserObject();
        String stringValue = "";
                
        item = (FavoritesItem) o;
        if (item != null) {
            stringValue = item.getName();
        }
        
        // Set the locale of this if there is a lang value
        if (item != null) {
            Locale locale = item.getLocale();
            if (locale != null) {
                setLocale(locale);
            }
        }
        
        setText(stringValue);
        
        if (sel)
            setForeground(getTextSelectionColor());
        else
            setForeground(getTextNonSelectionColor());
        selected = sel;
        
        if(leaf)
            setIcon(getDefaultLeafIcon());
        else if(expanded)
            setIcon(getDefaultOpenIcon());
        else
            setIcon(getDefaultClosedIcon());        
       
        return this;
    }    
}
