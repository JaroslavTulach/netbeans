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

import javax.help.event.EventListenerList;
import javax.help.search.SearchEvent;
import javax.help.search.SearchListener;
import java.net.URL;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Locale;
import java.security.InvalidParameterException;

/**
 * Defines the methods used to access a search engine.
 * Each instance is created by a engine factory.
 * 
 * Extensions of SearchEngine can perform the search or negotiate the search
 * results with an outside agent. A server search engine is an an example
 * of an outside agent.
 *
 * Search results are returned through SearchEvents to listeners that
 * register with a SearchQuery instance. The SearchQuery
 * is returned from the method createQuery.
 *
 * @author Roger D. Brinkley
 * @version	1.6	03/19/99
 *
 * @see javax.help.search.SearchEvent
 * @see javax.help.search.SearchListener
 */

public abstract class SearchEngine {

    protected URL base;		// the base for resolving URLs against
    protected Hashtable params;	// other parameters to the engine

    /**
     * Creates a SearchEngine using the standard JavaHelp SearchEngine
     * parameters. Only this constructor is used to create a SearchEngine
     * from within a search view.
     *
     * @param base The base address of the data.
     * @param params A hashtable of parameters from the search view.
     */
    public SearchEngine(URL base, Hashtable params) 
	throws InvalidParameterException
    {
	this.base = base;
	this.params = params;
    }

    /**
     * Creates a SearchEngine.
     */
    public SearchEngine() {
    }

    /**
     * Creates a new search query.
     */
    public abstract SearchQuery createQuery() throws IllegalStateException;

    private static final boolean debug = false;
    private static void debug(String msg) {
	if (debug) {
	    System.err.println("SearchEngine: "+msg);
	}
    }
}
