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
 * This class provides is useful for the nightmare of parsing multi-part 
 * HTTP/RFC822 headers sensibly:
 * <p>
 * <pre>
 * From a String like: 'timeout=15, max=5'
 * create an array of Strings:
 * { {"timeout", "15"},
 *   {"max", "5"}
 * }
 * From one like: 'Basic Realm="FuzzFace" Foo="Biz Bar Baz"'
 * create one like (no quotes in literal):
 * { {"basic", null},
 *   {"realm", "FuzzFace"}
 *   {"foo", "Biz Bar Baz"}
 * }
 * keys are converted to lower case, vals are left as is....
 * </pre>
 *
 * @version	1.4	10/30/06
 * @author	Dave Brown
 * @author	Roger D. Brinkley
 */ 


public class HeaderParser {

    /* table of key/val pairs - maxes out at 10!!!!*/
    String raw;
    String[][] tab;
    
    public HeaderParser(String raw) {
	this.raw = raw;
	tab = new String[10][2];
	parse();
    }

    private void parse() {
	
	if (raw != null) {
	    raw = raw.trim();
	    char[] ca = raw.toCharArray();
	    int beg = 0, end = 0, i = 0;
	    boolean inKey = true;
	    boolean inQuote = false;
	    int len = ca.length;
	    while (end < len) {
		char c = ca[end];
		if (c == '=') { // end of a key
		    tab[i][0] = new String(ca, beg, end-beg).toLowerCase();
		    inKey = false;
		    end++;
		    beg = end;
		} else if (c == '\"') {
		    if (inQuote) {
			tab[i++][1]= new String(ca, beg, end-beg);
			inQuote=false;
			do {
			    end++;
			} while (end < len && (ca[end] == ' ' || ca[end] == ','));
			inKey=true;
			beg=end;
		    } else {
			inQuote=true;
			end++;
			beg=end;
		    }
		} else if (c == ' ' || c == ',') { // end key/val, of whatever we're in
		    if (inQuote) {
			end++;
			continue;
		    } else if (inKey) {
			tab[i++][0] = (new String(ca, beg, end-beg)).toLowerCase();
		    } else {
			tab[i++][1] = (new String(ca, beg, end-beg));
		    }
		    while (end < len && (ca[end] == ' ' || ca[end] == ',')) {
			end++;
		    }
		    inKey = true;
		    beg = end;
		} else {
		    end++;
		}
	    } 
	    // get last key/val, if any
	    if (--end > beg) {
		if (!inKey) {
		    if (ca[end] == '\"') {
			tab[i++][1] = (new String(ca, beg, end-beg));
		    } else {
			tab[i++][1] = (new String(ca, beg, end-beg+1));
		    }
		} else {
		    tab[i][0] = (new String(ca, beg, end-beg+1)).toLowerCase();
		}
	    } else if (end == beg) {
		if (!inKey) {
		    if (ca[end] == '\"') {
			tab[i++][1] = String.valueOf(ca[end-1]);
		    } else {
			tab[i++][1] = String.valueOf(ca[end]);
		    }
		} else {
		    tab[i][0] = String.valueOf(ca[end]).toLowerCase();
		}
	    } 
	}
	
    }

    public String findKey(int i) {
	if (i < 0 || i > 10)
	    return null;
	return tab[i][0];
    }

    public String findValue(int i) {
	if (i < 0 || i > 10)
	    return null;
	return tab[i][1];
    }

    public String findValue(String key) {
	return findValue(key, null);
    }

    public String findValue(String k, String Default) {
	if (k == null)
	    return Default;
	k.toLowerCase();
	for (int i = 0; i < 10; ++i) {
	    if (tab[i][0] == null) {
		return Default;
	    } else if (k.equals(tab[i][0])) {
		return tab[i][1];
	    }
	}
	return Default;
    }

    public int findInt(String k, int Default) {
	try {
	    return Integer.parseInt(findValue(k, String.valueOf(Default)));
	} catch (Throwable t) {
	    return Default;
	}
    }
 }
