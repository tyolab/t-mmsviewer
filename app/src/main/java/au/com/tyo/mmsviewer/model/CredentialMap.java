/**
 * Copyright (c) 2020 TYO Lab (TYONLINE TECHNOLOGY PTY. LTD.). All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package au.com.tyo.mmsviewer.model;

import java.util.HashMap;
import java.util.Map;

public class CredentialMap {

    public static final String USER_ID = "msisdn";
    public static final String PASSWORD = "msgid";

    private Map credentialsMap;

    public CredentialMap() {

        credentialsMap = new HashMap();
        credentialsMap.put(USER_ID, null);
        credentialsMap.put(PASSWORD, null);
    }

    public void setUserId(String userId) {
        credentialsMap.put(USER_ID, userId);
    }

    public void setPassword(String password) {
        credentialsMap.put(PASSWORD, password);
    }

    public Map getCredentialsMap() {
        return credentialsMap;
    }
}

