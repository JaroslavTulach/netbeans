#!/bin/bash
# Licensed to the Apache Software Foundation (ASF) under one
# or more contributor license agreements.  See the NOTICE file
# distributed with this work for additional information
# regarding copyright ownership.  The ASF licenses this file
# to you under the Apache License, Version 2.0 (the
# "License"); you may not use this file except in compliance
# with the License.  You may obtain a copy of the License at
#
#   http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing,
# software distributed under the License is distributed on an
# "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
# KIND, either express or implied.  See the License for the
# specific language governing permissions and limitations
# under the License.


set -e

if [ -n "$1" ]; then
  JH="$1"
else 
  JH=../../javahelp
fi

rm -rf src/com/ src/javax/

cp -r $JH/jhMaster/JavaHelp/src/new/* src/
cp -r $JH/jhMaster/JavaHelp/src/impl/* src/
cp -r $JH/jhMaster/JSearch/client/* src/

for i in src/javax/help/tagext/*.java; do
  F=`basename $i .java`
  mv src/javax/help/tagext/$F.java src/javax/help/tagext/$F.jawa
done

mv src/javax/help/ServletHelpBroker.java src/javax/help/ServletHelpBroker.jawa

ant clean build

