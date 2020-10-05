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

import * as fs from 'fs';
import * as os from 'os';
import * as path from 'path';
import { spawn, ChildProcessByStdio } from 'child_process';
import { Readable } from 'stream';

export interface LaunchInfo {
    clusters: string[];
    extensionPath: string;
    storagePath: string;
    jdkHome: string | unknown;
}

export function launch(
    info: LaunchInfo,
    ...extraArgs : string[]
): ChildProcessByStdio<null, Readable, Readable> {
    let nbexec = os.platform() === 'win32' ? 
        os.arch() === 'x64' ? 'nbexec64.exe' : 'nbexec.exe' 
        : 'nbexec';
    const nbexecPath = path.join(info.extensionPath, 'nbcode', 'platform', 'lib', nbexec);
    let nbexecPerm = fs.statSync(nbexecPath);
    if (!nbexecPerm.isFile()) {
        throw `Cannot execute ${nbexecPath}`;
    }

    const userDir = path.join(info.storagePath, "userdir");
    fs.mkdirSync(userDir, {recursive: true});
    let userDirPerm = fs.statSync(userDir);
    if (!userDirPerm.isDirectory()) {
        throw `Cannot create ${userDir}`;
    }

    let clusterPath = info.clusters.join(path.delimiter);
    let ideArgs: string[] = [
        "--nosplash", "--nogui", "--branding", "nbcode",
        "-J-Djava.awt.headless=true",
        "-J-Dnetbeans.logger.console=true",

        "-J--add-opens=java.base/java.net=ALL-UNNAMED",
        "-J--add-opens=java.base/java.lang.ref=ALL-UNNAMED",
        "-J--add-opens=java.base/java.lang=ALL-UNNAMED",
        "-J--add-opens=java.base/java.security=ALL-UNNAMED",
        "-J--add-opens=java.base/java.util=ALL-UNNAMED",
        "-J--add-opens=java.desktop/javax.swing.plaf.basic=ALL-UNNAMED",
        "-J--add-opens=java.desktop/javax.swing.text=ALL-UNNAMED",
        "-J--add-opens=java.desktop/javax.swing=ALL-UNNAMED",
        "-J--add-opens=java.desktop/java.awt=ALL-UNNAMED",
        "-J--add-opens=java.desktop/java.awt.event=ALL-UNNAMED",
        "-J--add-opens=java.prefs/java.util.prefs=ALL-UNNAMED",
        "-J--add-opens=jdk.jshell/jdk.jshell=ALL-UNNAMED",
        "-J--add-modules=jdk.jshell",
        "-J--add-exports=java.desktop/sun.awt=ALL-UNNAMED",
        "-J--add-exports=java.desktop/java.awt.peer=ALL-UNNAMED",
        "-J--add-exports=java.desktop/com.sun.beans.editors=ALL-UNNAMED",
        "-J--add-exports=java.desktop/sun.swing=ALL-UNNAMED",
        "-J--add-exports=java.desktop/sun.awt.im=ALL-UNNAMED",
        "-J--add-exports=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED",
        "-J--add-exports=java.management/sun.management=ALL-UNNAMED",
        "-J--add-exports=java.base/sun.reflect.annotation=ALL-UNNAMED",
        "-J-XX:+IgnoreUnrecognizedVMOptions",

        "--clusters", clusterPath, "--userdir", userDir
    ];
    if (info.jdkHome) {
        ideArgs.push('--jdkhome', info.jdkHome as string);
    }
    ideArgs.push(...extraArgs);

    let process: ChildProcessByStdio<any, Readable, Readable> = spawn(nbexecPath, ideArgs, {
        cwd : userDir,
        stdio : ["ignore", "pipe", "pipe"],
        shell : true
    });
    return process;
}

if (typeof process === 'object' && process.argv0 === 'node') {
    let extension = path.join(process.argv[1], '..', '..');
    let nbcode = path.join(extension, 'nbcode');
    if (!fs.existsSync(nbcode)) {
        throw `Cannot find ${nbcode}. Try npm run compile first!`;
    }
    let clusters = fs.readdirSync(nbcode).filter(c => c !== 'bin' && c !== 'etc').map(c => path.join(nbcode, c));
    let args = process.argv.slice(2);
    let globalStorage = path.join(os.homedir(), '.config', 'Code', 'User', 'globalStorage', 'jlahoda.apache-netbeans-java');
    let info = {
        clusters : clusters,
        extensionPath: extension,
        storagePath : globalStorage,
        jdkHome : null
    };
    let p = launch(info, ...args);
    p.stdout.on('data', function(data) {
        console.log(data.toString());
    });
    p.stderr.on('data', function(data) {
        console.log(data.toString());
    });
    p.on('close', (code) => {
        console.log(`nbcode finished with status ${code}`);
    });
}
