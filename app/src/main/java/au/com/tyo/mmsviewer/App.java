package au.com.tyo.mmsviewer;

import android.content.Context;
import android.content.Intent;

import au.com.tyo.app.CommonApp;
import au.com.tyo.app.PageAgent;
import au.com.tyo.app.ui.page.PageFormEx;
import au.com.tyo.mmsviewer.ui.UI;
import au.com.tyo.mmsviewer.ui.page.PageCommon;

/**
 * Created by Eric Tang (eric.tang@tyo.com.au) on 24/11/17.
 */

public class App extends CommonApp<UI, Controller, AppSettings> implements Controller {

    static {
        PageAgent.setPagesPackage(PageCommon.class.getPackage().getName());
    }

    public App(Context context) {
        super(context);
    }

    @Override
    public boolean hasUserLoggedIn() {
        return true;
    }

    @Override
    public void bindDataFromOtherApps(Intent intent) {
        // do nothing until we have such requirement
    }

    @Override
    public PageFormEx.FormHandler getFormHandler() {
        return null;
    }
}
