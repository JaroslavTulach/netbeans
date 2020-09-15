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
package org.netbeans.modules.java.lsp.server.debugging;

import com.microsoft.java.debug.core.adapter.IDebugAdapterContext;
import com.microsoft.java.debug.core.protocol.Events;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import org.netbeans.api.debugger.DebuggerEngine;
import org.netbeans.api.debugger.DebuggerManager;
import org.netbeans.api.debugger.DebuggerManagerAdapter;
import org.netbeans.api.debugger.Session;
import org.netbeans.api.debugger.jpda.JPDADebugger;
import org.netbeans.spi.debugger.ui.DebuggingView.DVSupport;
import org.netbeans.spi.debugger.ui.DebuggingView.DVThread;

/**
 *
 * @author martin
 */
public final class NbThreads implements IThreadsProvider {

    private long lastId = 1L;
    private final AtomicBoolean initialized = new AtomicBoolean(false);
    private final Map<Long, DVThread> threads = new HashMap<>();
    private final Map<DVThread, Long> threadIds = new HashMap<>();

    @Override
    public void initialize(IDebugAdapterContext context, Map<String, Object> options) {
        DebuggerManager.getDebuggerManager().addDebuggerListener(DebuggerManager.PROP_SESSIONS, new DebuggerManagerAdapter() {
            @Override
            public void sessionAdded(Session session) {
                DebuggerManager.getDebuggerManager().removeDebuggerListener(DebuggerManager.PROP_SESSIONS, this);
                JPDADebugger debugger = session.lookupFirst(null, JPDADebugger.class);
                initThreads(context, debugger);
            }
        });
    }

    private void initThreads(IDebugAdapterContext context, JPDADebugger debugger) {
        debugger.addPropertyChangeListener(JPDADebugger.PROP_STATE, evt -> {
            int newState = (int) evt.getNewValue();
            switch (newState) {
                case JPDADebugger.STATE_DISCONNECTED:
                    //debugger.removePropertyChangeListener(this);
                    context.setVmTerminated();
                    context.getProtocolServer().sendEvent(new Events.TerminatedEvent());
                    // Terminate eventHub thread.
                    try {
                        context.getDebugSession().getEventHub().close();
                    } catch (Exception e) {
                        // do nothing.
                    }
                    break;
            }
        });
        DebuggerEngine engine = debugger.getSession().getCurrentEngine();
        if (engine == null) {
            debugger.getSession().addPropertyChangeListener(Session.PROP_CURRENT_LANGUAGE, new PropertyChangeListener() {
                @Override
                public void propertyChange(PropertyChangeEvent evt) {
                    DebuggerEngine currentEngine = debugger.getSession().getCurrentEngine();
                    if (currentEngine != null) {
                        debugger.getSession().removePropertyChangeListener(Session.PROP_CURRENT_LANGUAGE, this);
                        if (!initialized.getAndSet(true)) {
                            initThreads(context, currentEngine);
                        }
                    }
                }
            });
            engine = debugger.getSession().getCurrentEngine();
        }
        if (engine != null && !initialized.getAndSet(true)) {
            initThreads(context, engine);
        }
    }

    private void initThreads(IDebugAdapterContext context, DebuggerEngine engine) {
        DVSupport dvSupport = engine.lookupFirst(null, DVSupport.class);
        dvSupport.addPropertyChangeListener(evt -> {
            switch (evt.getPropertyName()) {
                case DVSupport.PROP_THREAD_STARTED:
                    DVThread dvThread = (DVThread) evt.getNewValue();
                    long id;
                    synchronized (threads) {
                        Long idLong = threadIds.get(dvThread);
                        if (idLong == null) {
                            id = lastId++;
                            threads.put(id, dvThread);
                            threadIds.put(dvThread, id);
                        } else {
                            // It could be among all threads already
                            id = idLong;
                        }
                    }
                    Events.ThreadEvent threadStartEvent = new Events.ThreadEvent("started", id);
                    context.getProtocolServer().sendEvent(threadStartEvent);
                    break;
                case DVSupport.PROP_THREAD_DIED:
                    dvThread = (DVThread) evt.getNewValue();
                    id = 0L;
                    synchronized (threads) {
                        Long idObject = threadIds.remove(dvThread);
                        if (idObject != null) {
                            id = idObject;
                            threads.remove(id);
                        }
                    }
                    if (id > 0) {
                        Events.ThreadEvent threadDeathEvent = new Events.ThreadEvent("exited", id);
                        context.getProtocolServer().sendEvent(threadDeathEvent);
                    }
                    break;
                case DVSupport.PROP_THREAD_SUSPENDED:
                    dvThread = (DVThread) evt.getNewValue();
                    id = getId(dvThread);
                    assert id > 0: "Unknown ID for thread " + dvThread;
                    if (id > 0) {
                        String eventName;
                        if (dvThread.isInStep()) {
                            eventName = "step";
                        } else if (dvThread.getCurrentBreakpoint() != null) {
                            eventName = "breakpoint";
                        } else {
                            eventName = "pause";
                        }
                        context.getProtocolServer().sendEvent(new Events.StoppedEvent(eventName, id));
                    }
                    break;
                case DVSupport.PROP_THREAD_RESUMED:
                    dvThread = (DVThread) evt.getNewValue();
                    id = getId(dvThread);
                    assert id > 0: "Unknown ID for thread " + dvThread;
                    // Should not sponaneously resume, the client should request resume and thus knows about it.
                    break;
            }
        });
        // Assure that all threads are added:
        synchronized (threads) {
            for (DVThread dvThread : dvSupport.getAllThreads()) {
                if (!threadIds.containsKey(dvThread)) { // We could get it twice if thread start event comes now
                    long id = lastId++;
                    threads.put(id, dvThread);
                    threadIds.put(dvThread, id);
                }
            }
        }
    }

    /**
     * Get the thread ID, or <code>0</code> if the thread was not found.
     */
    @Override
    public long getId(DVThread thread) {
        long id = 0L;
        Long idObject;
        synchronized (threads) {
            idObject = threadIds.get(thread);
        }
        if (idObject != null) {
            id = idObject;
        }
        return id;
    }

    /**
     * Get thread by its ID.
     * @return the thread, or <code>null</code> when no thread with that ID exists.
     */
    @Override
    public DVThread getThread(long id) {
        synchronized (threads) {
            return threads.get(id);
        }
    }

    @Override
    public void visitThreads(BiConsumer<Long, DVThread> threadsConsumer) {
        synchronized (threads) {
            for (Map.Entry<Long, DVThread> entry : threads.entrySet()) {
                threadsConsumer.accept(entry.getKey(), entry.getValue());
            }
        }
    }
}
