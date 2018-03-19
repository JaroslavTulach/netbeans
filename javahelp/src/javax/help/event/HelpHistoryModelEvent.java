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
 * Notifies interested parties that a change in a
 * Help History Model source has occurred.
 *
 * @author  Richard Gregor
 * @version   1.4     10/30/06
 */

public class HelpHistoryModelEvent extends java.util.EventObject{

    private boolean next;
    private boolean previous;    
    
    /**
     * Represents a history change 
     *
     * @param source The source for this event.
     * @param previous If true a previous action is allowed.
     * @param next If true a next action is allowed.
     * @throws IllegalArgumentException if source is null.
     */
    public HelpHistoryModelEvent(Object source, boolean previous, 
				 boolean next) {
        super(source);
        this.next = next;
        this.previous = previous;        
    }
    
    /**
     * Returns if action "previous" is allowed.
     * 
     * @return True if action is allowed, false otherwise. 
     */
    public boolean isPrevious(){
        return previous;
    }
    
    /**
     * Returns if action "next" is allowed
     *
     * @return True if action is allowed, false otherwise.
     */
    public boolean isNext() {
        return next;
    }
    
}
