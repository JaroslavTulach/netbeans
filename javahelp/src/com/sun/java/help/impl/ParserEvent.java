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
 * An event in the HTML/XML Parser
 *
 * @author Roger D. Brinkley
 * @version	1.12	10/30/06
 */

import java.net.URL;
import java.util.Vector;
import java.util.Enumeration;

public class ParserEvent extends java.util.EventObject {
    private Tag tag;
    private String text;
    private String target;
    private String data;
    private String root;
    private String publicId;
    private String systemId;

    /**
     * Represents a parsed Tag in the Parser
     * @see java.help.basic.Parser
     * 
     * @param source The Parser this came from
     * @param tag The parsed Tag.
     */
    public ParserEvent(Object source, Tag tag) {
	super (source);
	this.tag = tag;
    }

    /**
     * Represents a parsed continous block of text, a comment, or an error.
     * @see java.help.basic.Parser
     *
     * @param source The Parser this came from
     * @param String The text, comment, or error
     */
    public ParserEvent(Object source, String text) {
	super (source);
	this.text = text;
    }

    /**
     * Represents a PI (processing instruction)
     * @see java.help.basic.Parser
     *
     * @param source The Parser this came from
     * @param target The PI target
     * @param data The rest of the PI
     */
    public ParserEvent(Object source, String target, String data) {
	super (source);
	this.target = target;
	this.data = data;
    }

    /**
     * Represents a DOCTYPE
     * @see java.help.basic.Parser
     *
     * @param source The Parser this came from
     * @param root The root
     * @param publicId The publicID (may be null)
     * @param systemID The  systemID (may be null)
     */
    public ParserEvent(Object source, String root, String publicId, String systemId) {
	super (source);
	this.root = root;
	this.publicId = publicId;
	this.systemId = systemId;
    }

    /**
     * @return the Tag
     */
    public Tag getTag() {
	return tag;
    }

    /**
     * @return the text
     */
    public String getText() {
	return text;
    }

    /**
     * @return the target
     */
    public String getTarget() {
	return target;
    }

    /**
     * @return the data
     */
    public String getData() {
	return data;
    }

    /**
     * @return the root
     */
    public String getRoot() {
	return root;
    }

    /**
     * @return the publicId
     */
    public String getPublicId() {
	return publicId;
    }

    /**
     * @return the systemId
     */
    public String getSystemId() {
	return systemId;
    }
}
