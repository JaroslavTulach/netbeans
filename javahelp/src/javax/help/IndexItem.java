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

import java.util.Vector;
import java.util.Locale;
import javax.help.Map.ID;

/**
 * A class for individual index items.
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @version	1.18	10/30/06
 */

public class IndexItem extends TreeItem {

    /**
     * Create an IndexItem.
     *
     * @param id ID for the item. The ID can be null.
     * @param hs A HelpSet scoping this item.
     * @param locale The locale for this item
     */
    public IndexItem(ID id, HelpSet hs, Locale locale) {
	super(id, hs, locale);
    }

    /**
     * Create an IndexItem defaulting the HelpSet to that of its ID.
     *
     * @param id ID for the item. The ID can be null.
     * @param locale The locale to use for this item.
     */
    public IndexItem(ID id, Locale locale) {
	super(id, locale);
	HelpSet hs = null;
	if (id != null) {
	    setHelpSet(id.hs);
	}

    }

    /**
     * Create a default IndexItem.
     */
    public IndexItem() {
	super(null, null, null);
    }
}
