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

package au.com.tyo.mmsviewer.ui.page;

import android.app.Activity;
import android.graphics.Color;

import au.com.tyo.app.ui.page.Page;
import au.com.tyo.app.ui.page.PageInitializer;
import au.com.tyo.mmsviewer.Controller;
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
