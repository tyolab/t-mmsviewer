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

package au.com.tyo.mmsviewer.ui;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.io.File;
import java.util.List;


public class ImagePagerAdapter extends FragmentStatePagerAdapter {

    private Context context;
    private List files;

    public ImagePagerAdapter(Context context, FragmentManager fm, List files) {
        super(fm);
        this.context = context;
        this.files = files;
    }

    @Override
    public Fragment getItem(int position) {
        return ImageCardFragment.newInstance(position, (File) files.get(position));
    }

    @Override
    public int getCount() {
        return files.size();
    }
}
