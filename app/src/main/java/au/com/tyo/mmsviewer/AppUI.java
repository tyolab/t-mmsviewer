package au.com.tyo.mmsviewer;

import au.com.tyo.app.ui.UIBase;
import au.com.tyo.mmsviewer.ui.UI;
import au.com.tyo.mmsviewer.ui.activity.ActivityLogin;

/**
 * Created by Eric Tang (eric.tang@tyo.com.au) on 27/11/17.
 */

public class AppUI extends UIBase implements UI {

    public AppUI(Controller controller) {
        super(controller);
    }

    @Override
    public void gotoLoginPage() {
        gotoPage(ActivityLogin.class);
    }
}
