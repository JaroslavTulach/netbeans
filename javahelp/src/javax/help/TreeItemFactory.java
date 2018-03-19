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

import java.util.Hashtable;
import java.util.Locale;
import java.util.Enumeration;
import java.net.URL;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 * A factory for creating TreeItems.  This can be used to reuse the parsers.
 *
 * @author Eduardo Pelegri-Llopart
 * @(#)TreeItemFactory.java 1.7 01/29/99
 */

public interface TreeItemFactory {
    /**
     * Starts parsing.
     *
     * @param source The URL of the document being parsed.
     */
    public void parsingStarted(URL source);


    /**
     * Processes a DOCTYPE.
     *
     * @param root The root tag of the document.
     * @param publicID PublicID from the DOCTYPE.
     * @param systemID SystemID from the DOCTYPE.
     */
    public void processDOCTYPE(String root, String publicID, String systemID);

    /**
     * A Processing Instruction.
     *
     * @param target The target of the PI.
     * @param data A String for the data in the PI.
     */
    public void processPI(HelpSet hs,
			  String target,
			  String data);

    /**
     * Creates a TreeItem from the given data.
     *
     * @param tagName The name of the tag (for example, treeItem, or tocItem)
     * @param attributes A hashtable with all the attributes.  Null is a valid value.
     * @param hs A HelpSet that provides context.
     * @param lang The locale.
     * @return A TreeItem.
     */
    public TreeItem createItem(String tagName,
			       Hashtable attributes,
			       HelpSet hs,
			       Locale locale);

    /**
     * Creates a default TreeItem.
     *
     * @return A TreeItem
     */
    public TreeItem createItem();

    /**
     * Reports a parsing error.
     *
     * @param msg The message to report.
     * @param validParse Whether the result of the parse is still valid.
     */
    public void reportMessage(String msg, boolean validParse);

    /**
     * Lists all the error messages.
     */
    public Enumeration listMessages();

    /**
     * Ends parsing.  Last chance to do something
     * to the node.  Return null to be sure the result is discarded.
     */
    public DefaultMutableTreeNode parsingEnded(DefaultMutableTreeNode node);
}

