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
package org.netbeans.modules.java.mx.project;

import java.io.ByteArrayInputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.SequenceInputStream;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import org.netbeans.api.extexecution.base.ProcessBuilder;

final class SequenceProcess extends Process {
    private Process current;
    private final Enumeration<? extends ProcessBuilder> en;
    private final SeqIn in;
    private final SeqIn err;
    private final SeqOut out;

    SequenceProcess(Enumeration<ProcessBuilder> en) {
        this.en = en;
        this.in = new SeqIn();
        this.err = new SeqIn();
        this.out = new SeqOut(null);
    }
    
    @Override
    public OutputStream getOutputStream() {
        return out;
    }

    @Override
    public InputStream getInputStream() {
        return in;
    }

    @Override
    public InputStream getErrorStream() {
        return err;
    }
    
    private synchronized Process current() {
        if (current != null) {
            if (current.isAlive() || !en.hasMoreElements()) {
                return current;
            }
        }
        try {
            Process next = en.nextElement().call();
            this.in.assign(next.getInputStream());
            this.out.assign(next.getOutputStream());
            this.err.assign(next.getErrorStream());
            this.current = next;
            return current;
        } catch (IOException ex) {
            dryPending();
            return current;
        }
    }

    @Override
    public int waitFor() throws InterruptedException {
        for (;;) {
            Process now = current();
            now.waitFor();
            if (now == current()) {
                return now.waitFor();
            }
        }
    }

    @Override
    public int exitValue() {
        return current().exitValue();
    }

    @Override
    public synchronized void destroy() {
        current().destroy();
        dryPending();
    }

    private void dryPending() {
        while (en.hasMoreElements()) {
            en.nextElement();
        }
    }

    @Override
    public boolean isAlive() {
        return current().isAlive();
    }
    
    private static final class SeqOut extends FilterOutputStream {
        public SeqOut(OutputStream out) {
            super(out);
        }

        final void assign(OutputStream out) throws IOException {
            if (this.out != null) {
                this.out.flush();
            }
            this.out = out;
        }
    }

    private static final class SeqIn extends SequenceInputStream {
        private LinkedList<InputStream> arr;
        
        public SeqIn() {
            this(new LinkedList<>());
        }
        
        private SeqIn(LinkedList<InputStream> ll) {
            super(new Enumeration<InputStream>() {
                @Override
                public boolean hasMoreElements() {
                    return ll.size() > 0;
                }

                @Override
                public InputStream nextElement() {
                    return ll.pop();
                }
            });
            this.arr = ll;
            this.arr.push(new ByteArrayInputStream(new byte[0]));
        }

        final void assign(InputStream in) {
            this.arr.push(in);
        }
    }
}
