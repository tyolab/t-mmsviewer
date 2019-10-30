package au.com.tyo.mmsviewer.ui.page;

import android.app.Activity;

import au.com.tyo.mmsviewer.Controller;
import au.com.tyo.mmsviewer.R;

/**
 * Created by Eric Tang (eric.tang@tyo.com.au) on 27/11/17.
 */

public class PageLogin extends PageEmailPassword {

    /**
     * @param controller
     * @param activity
     */
    public PageLogin(Controller controller, Activity activity) {
        super(controller, activity);

        setContentViewResId(R.layout.login);
    }
}
