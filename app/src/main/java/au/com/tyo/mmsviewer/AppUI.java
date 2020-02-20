package au.com.tyo.mmsviewer;

import au.com.tyo.app.ui.UIBase;
import au.com.tyo.json.form.FormGroup;
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

    @Override
    public void showInfo() {
        showInfo(true);
    }

    @Override
    protected void addAboutPageAcknowledgementFields(FormGroup acknowledgementGroup) {
        super.addAboutPageAcknowledgementFields(acknowledgementGroup);

        acknowledgementGroup.addField("Background Photo", "by el5ida rexhepi on Unsplash");
    }
}
