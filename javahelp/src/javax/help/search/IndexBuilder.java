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

import java.io.*;
import java.util.Enumeration;

/**
 * Abstract base class that builds an index for a search database.
 *
 * @author Roger D. Brinkley
 * @version	1.8	10/30/06
 */

public abstract class IndexBuilder
{

    protected String indexDir;

    /**
     * Builds an index at indexDir. If indexDir already exists
     * the index is opened and the new doucments are merged into
     * the existing document.
     */
    public IndexBuilder(String indexDir) throws Exception
    {
	debug("indexDir=" + indexDir);
	this.indexDir = indexDir;
	File test = new File(indexDir);	
	try {
	    if (!test.exists()) {
		debug ("file " + indexDir + " didn't exist - creating");
		test.mkdirs();
	    }
	} catch (java.lang.SecurityException e) {
	}
    }

    /**
     * Closes the index. 
     */
    public abstract void close() throws Exception;

    /**
     * Sets the stopwords in an index. If the stopwords are already 
     * defined for an index, the stop words are merged with the existing
     * set of stopwords.
     * @params stopWords An Enumeration of Strings.
     */
    public abstract void storeStopWords(Enumeration stopWords);

    /**
     * Returns the list of stopwords for an index.
     * @returns Enumeration An enumeration of Strings. Returns null if there are no stopwords.
     */
    public abstract Enumeration getStopWords();

    /**
     * Opens a document to store information.
     */
    public abstract void openDocument(String name) throws Exception;
  
    /**
     * Closes the document. This prevents any additional information from being
     * stored.
     */
    public abstract void closeDocument() throws Exception;

    /**
     * Stores a concept at a given position.
     */
    public abstract void storeLocation(String text, int position) throws Exception;
    
    /**
     * Stores the title for the document.
     */
    public abstract void storeTitle(String title) throws Exception;

    private boolean debug=false;
    private void debug(String msg) {
	if (debug) {
	    System.err.println("IndexBuilder: "+msg);
	}
    }

}
