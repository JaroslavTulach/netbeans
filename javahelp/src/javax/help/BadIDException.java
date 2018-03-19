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

/**
 * An ID was attempted to be created with incorrect arguments
 *
 * @author Roger D. Brinkley
 * @author Eduardo Pelegri-Llopart
 * @version	1.15	10/30/06
 */

public class BadIDException extends IllegalArgumentException {
    private Map map;
    private String id;
    private HelpSet hs;

    /**
     * Create the exception. Null values are allowed for each parameter.
     * 
     * @param map The Map in which the ID wasn't found
     * @param msg A generic message
     * @param id The ID in Map that wasn't found
     * @see javax.help.Map
     */
    public BadIDException(String msg, Map map, String id, HelpSet hs) {
	super(msg);
	this.map = map;
	this.id = id;
	this.hs = hs;
    }

    /**
     * The HelpSet in which the ID wasn't found
     */
    public Map getMap() {
	return map;
    }

    /**
     * The ID that wasn't found in the Map
     */
    public String getID() {
	return id;
    }

    /**
     * The HelpSet that wasn't found in the Map
     */
    public HelpSet getHelpSet() {
	return hs;
    }
}

