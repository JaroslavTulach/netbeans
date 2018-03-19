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

package javax.help.search;

import java.util.Vector;
import java.util.Enumeration;

/**
 * Encapsulates information that describes changes to a SearchQuery.  It is used
 * to notify listeners of the change.
 *
 * @author Roger D. Brinkley
 * @version	1.16	03/19/99
 */

public class SearchEvent extends java.util.EventObject
{
    private String params;
    private boolean searching;
    private Vector items;

    /**
     * Represents a change in the SearchEngine. Used for starting the search
     * or ending the search.
     *
     * @param source The source of this event.
     * @param params The search parameters.
     * @param searching A boolean operator that indicates if searching is 
     * executing (true) or stopped (false).
     * @throws IllegalArgumentException if source, or params is NULL.
     */
    public SearchEvent(Object source, String params, boolean searching) {
	super (source);
	if (params == null) {
	    throw new IllegalArgumentException("null params");
	}
	this.params = params;
	this.searching = searching;
    }

    /**
     * Represents a change in the SearchEngine. Used to indicate that either a single
     * item or a group of items have matched the params.
     *
     * @param source The source of this event.
     * @param params The search parameters.
     * @param searching A boolean operator that indicates if a search is 
     * executing (true) or stopped (false).
     * @param items A Vector of SearchItems matching the the search params.
     *
     * @throws IllegalArgumentException if source, params, or items is NULL.
     * @see java.javahelp.SearchItems
     */
    public SearchEvent(Object source, String params, boolean searching, Vector items) {
	super(source);
	if (params == null) {
	    throw new IllegalArgumentException("null params");
	}
	this.params = params;
	this.searching = searching;
	if (items == null) {
	    throw new IllegalArgumentException("null items");
	}
	this.items = items;
    }


    /**
     * Returns the parameters to the query.
     */
    public String getParams() {
	return params;
    }

    /**
     * A boolean value that indicates if the search is completed.
     */
    public boolean isSearchCompleted() {
	return searching;
    }

    /**
     * An enumerated list of SearchItems that match parameters of the query.
     */
    public Enumeration getSearchItems() {
	if (items == null) {
	    return null;
	}
	return items.elements();
    }
}
