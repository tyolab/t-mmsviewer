package au.com.tyo.mmsviewer;

import au.com.tyo.mmsviewer.ui.UI;

/**
 * Created by Eric Tang (eric.tang@tyo.com.au) on 24/11/17.
 */

public interface Controller extends au.com.tyo.app.Controller<UI> {

    boolean hasUserLoggedIn();

}
