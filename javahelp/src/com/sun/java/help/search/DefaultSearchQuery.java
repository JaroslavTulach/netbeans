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
 * DefaultSearchQuery is the query using the default search engine. 
 *
 * Search results are returned through SearchEvents to
 * listeners that register with this instance.
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @version	1.7	10/30/06
 *
 * @see javax.help.search.SearchEngine
 * @see javax.help.search.SearchQuery
 * @see javax.help.search.SearchEvent
 * @see javax.help.search.SearchListener
 */
public class DefaultSearchQuery extends SearchQuery implements Runnable {

    private Thread thread = null;
    private DefaultSearchEngine dhs;

    /**
     * Create a DefaultSearchEngine 
     */
    public DefaultSearchQuery(SearchEngine hs) {
	super(hs);
	if (hs instanceof DefaultSearchEngine) {
	    dhs = (DefaultSearchEngine) hs;
	}
    }


    /**
     * Starts the search. The implementation is up to subclasses of SearchEngine.
     * This method will invoke searchStarted on SearchListeners.
     * @exception IllegalArgumentException The parameters are not 
     * understood by this engine
     * @exception IllegalStateException There is an active search in progress in this instance
     */
    public void start(String searchparams, Locale l) 
	 throws IllegalArgumentException, IllegalStateException 
    {
	debug ("Starting Search");
	if (isActive()) {
	    throw new IllegalStateException();
	}

	// initialization
	super.start(searchparams, l);

	// Actually do the search
	thread = new Thread(this, "QueryThread");
	thread.start();
    }

    /**
     * Stops the search. The implementation is up to the subcalsses of 
     * SearchEngine. This method will invoke searchStopped on 
     * SearchListeners.
     */
    public void stop() throws IllegalArgumentException, IllegalStateException {
	debug ("Stop Search");
	// Can no longer do a stop
	// Let it continue to operate until it's completed
	// on it's own. This is due to to the enherent problem 
	// with thread.stop
    }

    public boolean isActive() {
	if (thread == null) { 
	    return false;
	}
	return thread.isAlive();
    }

    public void run() throws IllegalArgumentException{
	QueryEngine qe = dhs.getQueryEngine();
	try {
	    qe.processQuery(searchparams, l, this);
	} catch (Exception e) {
	    e.printStackTrace();
	    throw new IllegalArgumentException();
	}
	fireSearchFinished();
	thread = null;
    }

    /**
     * For printf debugging.
     */
    private static final boolean debugFlag = false;
    private static void debug(String str) {
        if( debugFlag ) {
            System.out.println("DefaultSearchQuery: " + str);
        }
    }

}
