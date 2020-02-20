package au.com.tyo.mmsviewer.ui.page;

import android.app.Activity;
import android.graphics.Color;

import au.com.tyo.app.ui.page.PageInitializer;
import au.com.tyo.app.ui.page.PageWebView;
import au.com.tyo.mmsviewer.Controller;
import au.com.tyo.app.ui.page.Page;
import au.com.tyo.mmsviewer.R;

/**
 * Created by Eric Tang (eric.tang@tyo.com.au) on 27/11/17.
 */

public class PageCommon extends Page<Controller> {

    protected Controller controller;

    static {
        PageInitializer.setInstance(new PageInitializer() {
            @Override
            public void initializePageOnConstruct(Page page) {
                page.setTitleTextColor(Color.WHITE);
            }

            @Override
            public void initializePageOnActivityStart(Page page) {
                int color = page.getActivity().getResources().getColor(R.color.toolbarColor);
                page.setStatusBarColor(color);
                page.setPageToolbarColor(color);
            }
        });
    }

    /**
     * @param controller
     * @param activity
     */
    public PageCommon(Controller controller, Activity activity) {
        super(controller, activity);

        this.controller = controller;

        toolbarColor = activity.getResources().getColor(R.color.toolbarColor);
        titleTextColor = activity.getResources().getColor(R.color.white);
    }

}
