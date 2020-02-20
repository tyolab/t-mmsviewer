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
