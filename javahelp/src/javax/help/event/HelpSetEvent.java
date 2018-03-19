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

import javax.help.HelpSet;

/**
 * Conveys information when a HelpSet is added/removed.
 *
 * @author Eduardo Pelegri-Llopart
 * @version	1.10	03/10/99
 */

public class HelpSetEvent extends java.util.EventObject {

    /**
     * Creates a HelpSetEvent.
     *
     * @param source Source of this Event.
     * @param helpset The HelpSet being added/removed.
     * @param action HELPSET_ADDED or HELPSET_REMOVED.
     * @throws IllegalArgumentException if source is null or if action is not
     * a valid action.
     */
     public HelpSetEvent(Object source, HelpSet helpset, int action) {
	 super(source);
         this.helpset = helpset;
	 if (helpset == null) {
	     throw new NullPointerException("helpset");
	 }
         this.action = action;
	 if (action < 0 || action > 1) {
	     throw new IllegalArgumentException("invalid action");
	 }
     }

    /**
     * A HelpSet was added
     */
     public static final int HELPSET_ADDED = 0;

    /**
     * A HelpSet was removed
     */
     public static final int HELPSET_REMOVED = 1;

    /**
     * @return The HelpSet.
     */
     public HelpSet getHelpSet() {
	return helpset;
     }

    /**
     * @return The action
     */
     public int getAction() {
        return action;
     }

    /**
     * Returns textual about the instance. 
     */
    public String toString() {
	if (action==HELPSET_ADDED) {
	    return "HelpSetEvent("+source+", "+helpset+"; added";
	} else {
	    return "HelpSetEvent("+source+", "+helpset+"; removed";
	}
    }

     private HelpSet helpset;
     private int action;
}
