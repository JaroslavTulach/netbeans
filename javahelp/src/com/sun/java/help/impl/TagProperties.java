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

package com.sun.java.help.impl;

/**
 * The <code>TagProperties</code> class represents a persistent set of 
 * properties. Each key and its corresponding value in 
 * the property list is a string. 
 *
 * @see java.help.Tag
 */

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

public class TagProperties implements Cloneable {

    protected Hashtable	hashtable;
    protected int	initialSize;

static int count1 = 0, count2 = 0;

    /**
     * Creates an empty property list with no default values. 
     *
     * @since   JDK1.0
     */
    public TagProperties() {
	this(7);		// Prime number
    }

    public TagProperties(int initialSize) {
	this.initialSize = initialSize;
    }


    /**
     * Searches for the property with the specified key in this property list.
     * The method returns
     * <code>null</code> if the property is not found.
     *
     * @param   key   the property key.
     * @return  the value in this property list with the specified key value.
     * @since   JDK1.0
     */
    public String getProperty(String key) {
	return (String)get(key);
    }

    /**
     * Searches for the property with the specified key in this property list.
     * The method returns the
     * default value argument if the property is not found.
     *
     * @param   key            the hashtable key.
     * @param   defaultValue   a default value.
     *
     * @return  the value in this property list with the specified key value.
     * @since   JDK1.0
     */
    public String getProperty(String key, String defaultValue) {
	String val = getProperty(key);
	return (val == null) ? defaultValue : val;
    }

    /**
     * Returns an enumeration of all the keys in this property list
     *
     * @return  an enumeration of all the keys in this property list
     * @see     java.util.Enumeration
     * @since   JDK1.0
     */
    public Enumeration propertyNames() {
	Hashtable h = new Hashtable(11);
	enumerate(h);
	return h.keys();
    }

    /**
     * Prints this property list out to the specified output stream. 
     * This method is useful for debugging. 
     *
     * @param   out   an output stream.
     * @since   JDK1.0
     */
    public void list(PrintStream out) {
	out.println("-- listing properties --");
	Hashtable h = new Hashtable(11);
	enumerate(h);
	for (Enumeration e = h.keys() ; e.hasMoreElements() ;) {
	    String key = (String)e.nextElement();
	    String val = (String)h.get(key);
	    if (val.length() > 40) {
		val = val.substring(0, 37) + "...";
	    }
	    out.println(key + "=" + val);
	}
    }

    /**
     * Prints this property list out to the specified output stream. 
     * This method is useful for debugging. 
     *
     * @param   out   an output stream.
     * @since   JDK1.1
     */
    /*
     * Rather than use an anonymous inner class to share common code, this
     * method is duplicated in order to ensure that a non-1.1 compiler can
     * compile this file.
     */
    public void list(PrintWriter out) {
	out.println("-- listing properties --");
	Hashtable h = new Hashtable(11);
	enumerate(h);
	for (Enumeration e = h.keys() ; e.hasMoreElements() ;) {
	    String key = (String)e.nextElement();
	    String val = (String)h.get(key);
	    if (val.length() > 40) {
		val = val.substring(0, 37) + "...";
	    }
	    out.println(key + "=" + val);
	}
    }

    /**
     * Enumerates all key/value pairs in the specified hastable.
     * @param h the hashtable
     */
    private synchronized void enumerate(Hashtable h) {
	for (Enumeration e = keys() ; e.hasMoreElements() ;) {
	    String key = (String)e.nextElement();
	    h.put(key, get(key));
	}
    }

    /***** Implementation of a deferred hashtable *****/

    public int size() {
	if (hashtable != null) {
	    return hashtable.size();
	} else {
	    return 0;
	}
    }

    public boolean isEmpty() {
	if (hashtable != null) {
	    return hashtable.isEmpty();
	} else {
	    return true;
	}
    }

    public synchronized Enumeration keys() {
	if (hashtable != null) {
	    return hashtable.keys();
	} else {
	    return new EmptyEnumerator();
	}
    }

    public synchronized Enumeration elements() {
	if (hashtable != null) {
	    return hashtable.elements();
	} else {
	    return new EmptyEnumerator();
	}
    }

    public synchronized boolean contains(Object value) {
	if (hashtable != null) {
	    return hashtable.contains(value);
	} else {
	    return false;
	}
    }

    public synchronized boolean containsKey(Object key) {
	if (hashtable != null) {
	    return hashtable.containsKey(key);
	} else {
	    return false;
	}
    }

    public synchronized Object get(Object key) {
	if (hashtable != null) {
	    return hashtable.get(key);
	} else {
	    return null;
	}
    }

    public synchronized Object put(Object key, Object value) {
	if (hashtable == null) {
	    hashtable = new Hashtable(initialSize);
	}
	return hashtable.put(key, value);
    }

    public synchronized Object remove(Object key) {
	if (hashtable != null) {
	    return hashtable.remove(key);
	} else {
	    return null;
	}
    }

    public synchronized void clear() {
	if (hashtable != null) {
	    hashtable.clear();
	}
    }

    protected void setHashtable(Hashtable t) {
	hashtable = t;
    }

    /**
     * HERE - we probably should use plain Hashtable instead of TagProperties
     */
    public Hashtable getHashtable() {
	return hashtable;
    }

    public synchronized Object clone() {
	try { 
	    TagProperties tp = (TagProperties)super.clone();
	    if (hashtable != null) {
		tp.setHashtable((Hashtable)hashtable.clone());
	    }
	    return tp;
	} catch (CloneNotSupportedException e) { 
	    // this shouldn't happen, since we are Cloneable
	    throw new InternalError();
	}
    }

    public synchronized String toString() {
	if (hashtable != null) {
	    return hashtable.toString();
	} else {
	    return "{ }";
	}
    }

    class EmptyEnumerator implements Enumeration {
	
	public boolean hasMoreElements() {
	    return false;
    }
	
	public Object nextElement() {
	    throw new java.util.NoSuchElementException("EmptyEnumerator");
	}
    }

}

