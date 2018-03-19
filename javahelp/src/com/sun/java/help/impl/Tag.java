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
 * This class contains a reference to a tag in a parsed document, the type of
 * tag, and the tag's attributes.
 *
 * @author Roger D. Brinkley
 * @version	1.8	10/30/06
 *
 * @see Parser
 * @see HTMLParser
 */


public class Tag {

    /** The name of the tag */
    public String name;

    /** The type of tag, false if an opening tag or true if a closing tag. */
    public boolean isEnd;

    /** The type of tag, true if an empty tag. */
    public boolean isEmpty;

    /** The tag attributes, in identifier, value pairs. */
    public TagProperties atts;


    /**
     * Sets the tag, position, and type.
     * @param tag	   the tag descriptor
     * @param pos	   the position in the text
     * @param isEnd   true if a &lt;/tag&gt; or &lt;tag/&gt; tag 
     * @param isEmpty   true if a &lt;tag/&gt; tag
     * @see Tag
     **/

    public Tag(String name, TagProperties atts, boolean isEnd, boolean isEmpty) {
	this.name = name;
	this.atts = atts;
	this.isEnd = isEnd;
	this.isEmpty = isEmpty;
    }

}
