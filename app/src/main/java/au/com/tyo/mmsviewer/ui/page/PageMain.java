package au.com.tyo.mmsviewer.ui.page;

import android.app.Activity;

import au.com.tyo.mmsviewer.Controller;

/**
 * Created by Eric Tang (eric.tang@tyo.com.au) on 27/11/17.
 */

public class PageMain extends PageCommon {

    /**
     * @param controller
     * @param activity
     */
    public PageMain(Controller controller, Activity activity) {
        super(controller, activity);
    }

    @Override
    public void onActivityStart() {
        super.onActivityStart();

        if (!controller.hasUserLoggedIn()) {
            controller.getUi().gotoLoginPage();

            finish();
        }
    }
}
