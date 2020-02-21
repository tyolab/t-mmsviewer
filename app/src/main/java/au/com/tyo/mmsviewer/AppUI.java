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
        acknowledgementGroup.addField("Witcher Photo", "by Daniel Lee on Unsplash");
    }
}
