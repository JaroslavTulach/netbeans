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

package com.sun.java.help.search;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.Vector;
import java.util.Hashtable;
import java.util.EventListener;
import java.util.Locale;
import javax.help.search.*;
import com.sun.java.help.search.*;
import java.security.InvalidParameterException;

/**
 * DefaultSearchEngine is the default search engine. 
 *
 * Search results are returned through SearchEvents to
 * listeners that
 * register with a SearchEngine instance through the Search Query.
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @version	1.21	10/30/06
 *
 * @see javax.help.search.SearchEngine
 * @see javax.help.search.SearchEvent
 * @see javax.help.search.SearchListener
 */
public class DefaultSearchEngine extends SearchEngine {

    private String urldata;	// just for debugging really
    protected QueryEngine qe;

    /**
     * Create a DefaultSearchEngine 
     */
    public DefaultSearchEngine(URL base, Hashtable params) 
	throws InvalidParameterException
    {
	super(base, params);

	debug("Loading Search Database");
	debug("  base: "+base);
	debug("  params: "+params);

	// Load the Query Engine and Search DB here
	try {
	    urldata = (String) params.get("data");
	    qe = new QueryEngine(urldata, base);
	} catch (Exception e) {
	    if (debugFlag) {
		System.err.println(" =========== ");
		e.printStackTrace();
	    }
	    throw new InvalidParameterException();
	}
    }

    public SearchQuery createQuery() {
	return new DefaultSearchQuery(this);
    }

    protected QueryEngine getQueryEngine() {
	return qe;
    }

    /**
     * For printf debugging.
     */
    private static final boolean debugFlag = false;
    private static void debug(String str) {
        if( debugFlag ) {
            System.out.println("DefaultSearchEngine: " + str);
        }
    }

}
