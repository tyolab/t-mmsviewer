package au.com.tyo.mmsviewer;

import java.util.List;

import au.com.tyo.mmsviewer.ui.UI;

/**
 * Created by Eric Tang (eric.tang@tyo.com.au) on 24/11/17.
 */

public interface Controller extends au.com.tyo.app.Controller<UI> {

    boolean hasUserLoggedIn();

    String[] parseText(String mms);

    List downloadImages(String credential, String credential1, boolean forceDownload);

    List loadLastAlbum();
}
