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

/**
 * Defines the interface for an object that listens to
 * changes from a SearchQuery instance.
 *
 * @author Roger D. Brinkley
 * @version	1.11	10/30/06
 */

public interface SearchListener extends java.util.EventListener
{
    /**
     * Tells the listener that matching SearchItems have been found.
     */
    void itemsFound(SearchEvent e);

    /**
     * Tells the listener that the search has started.
     */
    void searchStarted(SearchEvent e);


    /**
     * Tells the listener that the search has finished
     */
    void searchFinished(SearchEvent e);
}

