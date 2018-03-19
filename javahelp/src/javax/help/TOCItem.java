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

import javax.help.Map.ID;
import java.util.Locale;

/**
 * A class for individual TOC items
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-llopart
 * @(#)TOCItem.java 1.13 01/29/99
 */

public class TOCItem extends TreeItem { 
    private ID imageID;

    /**
     * Creates a TOCItem.
     *
     * @param id ID for the item. A null ID is valid.
     * @param image The ID for image to be displayed for this item. A null
     * image is valid.
     * @param hs The HelpSet scoping this item.  In almost all cases
     * this is the same as the HelpSet of the id field. A null ID is valid.
     * @param lang The locale for this item. A null locale indicates the
     * default locale.
     */
    public TOCItem(ID id, ID imageID, HelpSet hs, Locale locale) {
	super(id, hs, locale);
	this.imageID = imageID;
    }

    /**
     * Creates a TOCItem with a default HelpSet based on its ID.
     *
     * @param id ID for the item. The ID can be null.
     * @param image The image to be displayed for this item.
     * @param lang The locale for this item
     */
    public TOCItem(ID id, ID imageID, Locale locale){
	super(id, locale);
	HelpSet hs = null;
	if (id != null) {
	    setHelpSet(id.hs);
	}
	this.imageID = imageID;
    }

    /**
     * Creates a default TOCItem.
     */

    public TOCItem() {
	super(null, null, null);
	this.imageID = null;
    }

    /**
     * Returns the image for this TOCItem.
     */
    public ID getImageID() {
	 return imageID;
    }

}
