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
'use strict';

import {
    NotificationType,
    RequestType,
    ShowMessageParams
} from 'vscode-languageclient';

export interface ShowStatusMessageParams extends ShowMessageParams {
    /**
     * The timeout
     */
    timeout?: number;
}

export namespace StatusMessageRequest {
    export const type = new NotificationType<ShowStatusMessageParams, void>('window/showStatusBarMessage');
};

export namespace NodeQueryRequest {
    export const type = new RequestType<string, string, void, void>('nodes/delete');
};

export namespace NodeInfoRequest {
    export const type = new RequestType<number, Data, void, void>('nodes/info');

    export interface Data {
        name : string; /* Node.getName() */
        displayName : string; /* Node.getDisplayName() */
        shortDescription : string; /* Node.getShortDescription() */
        leaf : boolean; /* Node.getChildren() == LEAF */
    }
};

export namespace NodeChildrenRequest {
    export const type = new RequestType<number, string[], void, void>('nodes/children');
};

export namespace NodeReleaseNotification {
    export const type = new NotificationType<number, void>('nodes/release');
};
