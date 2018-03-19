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

package javax.help.plaf.gtk;

import java.io.*;
import javax.swing.ImageIcon;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.util.ResourceBundle;
import java.util.MissingResourceException;
import java.util.PropertyResourceBundle;

/**
 * Factory object that can vend cursors appropriate for the GTK L & F.
 * <p>
 *
 * @version 1.2	10/30/06
 * @author Roger D. Brinkley
 */
public class GTKCursorFactory {
    private static Cursor onItemCursor;
    private static Cursor dndCursor;
    private static GTKCursorFactory theFactory;

    /**
     * Returns the OnItem cursor.
     */
    public static Cursor getOnItemCursor() {
	debug ("getOnItemCursor");
	if (theFactory == null) {
	    theFactory = new GTKCursorFactory();
	}
	if (onItemCursor == null) {
	    onItemCursor = theFactory.createCursor("OnItemCursor");
	}
	return onItemCursor;
    }

    /**
     * Returns the DnDCursor.
     */
    public static Cursor getDnDCursor(){
        debug("getDnDCursor");
        if (theFactory == null) {
	    theFactory = new GTKCursorFactory();
	}
	if (dndCursor == null) {
	    dndCursor = theFactory.createCursor("DnDCursor");
	}
	return dndCursor;
    }
    
    private Cursor createCursor(String name) {
 	String gifFile = null;
	String hotspot = null;
	ImageIcon icon;
	Point point;

	debug("CreateCursor for " + name);

	// Get the Property file
	InputStream is = getClass().getResourceAsStream("images/" + name + ".properties");
	if (is == null) {
	    debug(getClass().getName() + "/" + 
			       "images/" + name + ".properties" + " not found.");
	    return null;
	}
	try {
	    ResourceBundle resource = new PropertyResourceBundle(is);
	    gifFile = resource.getString("Cursor.File");
	    hotspot = resource.getString("Cursor.HotSpot");
	} catch (MissingResourceException e) {
	    debug(getClass().getName() + "/" + 
			       "images/" + name + ".properties" + " invalid.");
	    return null;
	} catch (IOException e2) {
	    debug(getClass().getName() + "/" + 
			       "images/" + name + ".properties" + " invalid.");
	    return null;
	}

	// Create the icon
	byte[] buffer = null;
	try {
	    /* Copies resource into a byte array.  This is
	     * necessary because several browsers consider
	     * Class.getResource a security risk because it
	     * can be used to load additional classes.
	     * Class.getResourceAsStream returns raw
	     * bytes, which JH can convert to an image.
	     */
	    InputStream resource = 
		getClass().getResourceAsStream(gifFile);
	    if (resource == null) {
		debug(getClass().getName() + "/" + 
				   gifFile + " not found.");
		return null; 
	    }
	    BufferedInputStream in = 
		new BufferedInputStream(resource);
	    ByteArrayOutputStream out = 
		new ByteArrayOutputStream(1024);
	    buffer = new byte[1024];
	    int n;
	    while ((n = in.read(buffer)) > 0) {
		out.write(buffer, 0, n);
	    }
	    in.close();
	    out.flush();
	    
	    buffer = out.toByteArray();
	    if (buffer.length == 0) {
		debug("warning: " + gifFile + 
				   " is zero-length");
		return null;
	    }
	} catch (IOException ioe) {
	    debug(ioe.toString());
	    return null;
	}

	icon = new ImageIcon(buffer);

	// create the point
	int k = hotspot.indexOf(',');
	point = new Point(Integer.parseInt(hotspot.substring(0,k)),
			  Integer.parseInt(hotspot.substring(k+1)));
	
	debug ("Toolkit fetching cursor");
	try {
            
            Image image = icon.getImage();
            int width = icon.getIconWidth();
            int height = icon.getIconHeight();
            
            Toolkit toolkit = Toolkit.getDefaultToolkit();
            Dimension d = toolkit.getBestCursorSize(width, height);

            if ((d.width > width) || (d.height > height)) {
                try {
                    Image bimage = new BufferedImage(d.width, d.height, BufferedImage.TYPE_INT_ARGB);
                    bimage.getGraphics().drawImage(icon.getImage(), 0, 0, icon.getImageObserver());
                    image = bimage;
                } catch (Exception ex) {
                }
            }
            
	    return toolkit.createCustomCursor(image, point, name);
            
	} catch (NoSuchMethodError err) {
	    //	    return Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	    return null;
	}
    }

    /**
     * For printf debugging.
     */
    private static final boolean debug = false;
    private static void debug(String str) {
        if (debug) {
            System.out.println("GTKCursorFactory: " + str);
        }
    }

}
