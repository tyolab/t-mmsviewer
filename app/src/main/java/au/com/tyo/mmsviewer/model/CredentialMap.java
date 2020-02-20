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

