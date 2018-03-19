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

import javax.help.JHelp;
import javax.help.HelpModel;
import java.awt.Component;

/**
 * Print handler for JavaHelp. Handles all printer requests for printing
 * in JDK1.2 and above. Because JDK1.1 is not supported since JavaHelp version 2.0
 * all code was moved into JHelpPrintHandler. This class is preserved for backward 
 * compatibility.
 *
 * @author Roger D. Brinkley
 * @author Stepan Marek
 * @version   1.12     10/30/06
 */

public class JHelpPrintHandler1_2 extends JHelpPrintHandler {

    public JHelpPrintHandler1_2(JHelp help) {
        super(help);
    }    

    /*
    public JHelpPrintHandler1_2(HelpModel model) {
        super(model);
    }
     */
    
    /**
     * JHelpPrintHandler constructor. Creates a JHelpPrintHandler with a null
     * Document.
     * @param model The HelpModel to derive URLs from.
     * @param comp The component from which the Frame can be optained for 1.1 printing
     */
    /*
    public JHelpPrintHandler1_2(HelpModel model, Component comp) {
        super (model, comp);
    }
     */
}
