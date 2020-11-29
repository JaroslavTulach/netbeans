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
package org.netbeans.modules.java.lsp.server.protocol;

import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;

final class DemoNodeData extends AbstractNode {

    private DemoNodeData(String dn, int cnt, DemoNodeData... ch) {
        super(ch == null || ch.length == 0 ? Children.LEAF : new Children.Array());
        setDisplayName(dn);
        setName("" + cnt);
        getChildren().add(ch);
    }

    public static DemoNodeData create() {
        return new DemoNodeData("root", 33,
            new DemoNodeData("chA", 1),
            new DemoNodeData("chB", 2,
                new DemoNodeData("deep1", 11),
                new DemoNodeData("deep2", 22)
            ),
            new DemoNodeData("chC", 3)
        );
    }
}
