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

package javax.help.event;

/**
 * Defines the interface for an object that listens to 
 * changes in a TextHelpModel.
 *
 * @author Eduardo Pelegri-Llopart
 * @author Roger D. Brinkley
 * @version	1.5	10/30/06
 */

public interface TextHelpModelListener extends java.util.EventListener {
    /**
     * Invoked when the set of highlights changess.
     * Note that this event is <em>not</em> generated when an idChanged() event
     * is generated, since all the highlights are reset at that point.
     */
    public void highlightsChanged(TextHelpModelEvent e);

}
