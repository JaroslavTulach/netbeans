/*******************************************************************************
* Copyright (c) 2017 Microsoft Corporation and others.
* All rights reserved. This program and the accompanying materials
* are made available under the terms of the Eclipse Public License v1.0
* which accompanies this distribution, and is available at
* http://www.eclipse.org/legal/epl-v10.html
*
* Contributors:
*     Microsoft Corporation - initial API and implementation
*******************************************************************************/

package org.netbeans.modules.java.lsp.server.debugging;

import java.util.List;

import com.sun.jdi.ThreadReference;
import com.sun.jdi.VirtualMachine;

public interface IDebugSession {
    void start();

    void suspend();

    void resume();

    void detach();

    void terminate();

    void setExceptionBreakpoints(boolean notifyCaught, boolean notifyUncaught);

    // TODO: createFunctionBreakpoint

    Process process();

    List<ThreadReference> getAllThreads();

    VirtualMachine getVM();
}
