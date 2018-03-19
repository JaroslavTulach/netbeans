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
 * ParserListener defines the interface for an object that listens to 
 * parsing events in a Parser.
 *
 * @author Roger D. Brinkley
 * @version	1.11	10/30/06
 */

public interface ParserListener extends java.util.EventListener {
    /**
     * This tells the listener that a Tag was parsed
     *
     * @param e The event
     */
    public void tagFound(ParserEvent e);

    /**
     * This tells the listener that a PI was parsed
     *
     * @param e The event
     */
    public void piFound(ParserEvent e);

    /**
     * This tells the listener that a DOCTYPE was parsed
     *
     * @param e The event
     */
    public void doctypeFound(ParserEvent e);

    /**
     * This tells the listener that a continous block of text was parsed
     *
     * @param e The event
     */
    public void textFound(ParserEvent e);

    /**
     * This tells the listener that a comment was parsed
     *
     * @param e The event
     */
    public void commentFound(ParserEvent e);

    /**
     * This tells the listener that a error was parsed
     *
     * @param e The event
     */
    public void errorFound(ParserEvent e);

}
