package au.com.tyo.mmsviewer.ui.page;

import android.Manifest;
import android.app.Activity;
import android.content.ClipboardManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

import au.com.tyo.android.CommonPermission;
import au.com.tyo.mmsviewer.BuildConfig;
import au.com.tyo.mmsviewer.Controller;
import au.com.tyo.mmsviewer.R;
import au.com.tyo.mmsviewer.ui.ImagePagerAdapter;
import me.relex.circleindicator.CircleIndicator;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Eric Tang (eric.tang@tyo.com.au) on 27/11/17.
 */

public class PageMain extends PageCommon {

    private static final int OP_DOWNLOAD = 0;
    private static final int OP_LOAD = 1;

    private AppCompatEditText editText;

    private String url;

    private boolean canWrite;

    private ViewPager pager;

    private View pagerContainer;

    /**
     * @param controller
     * @param activity
     */
    public PageMain(Controller controller, Activity activity) {
        super(controller, activity);

        setContentViewResId(R.layout.page_main);

        setRequiredPermissions(CommonPermission.PERMISSIONS_STORAGE);
        setStatusBarColor(activity.getResources().getColor(R.color.toolbarColor));
        // setPageToolbarTitleColor(activity.getResources().getColor(R.color.toolbarColor));
    }

    @Override
    protected void createMenu(MenuInflater menuInflater, Menu menu) {
        menuInflater.inflate(R.menu.main, menu);

        createMenuItemAbout(menuInflater, menu);

        super.createMenu(menuInflater, menu);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.menuItemLoadLast) {
            startBackgroundTask(OP_LOAD);
            return true;
        }
        return super.onMenuItemClick(item);
    }

    private void loadLastDownload() {
        List files = controller.loadLastAlbum();
        setResult(files);
    }

    @Override
    public void onActivityStart() {
        super.onActivityStart();

        // getWebView().loadUrl("http://telstra.com/mmsview");

        if (!controller.hasUserLoggedIn()) {
            controller.getUi().gotoLoginPage();

            finish();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);

        if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClip().getItemCount() > 0) {
            String text = clipboard.getPrimaryClip().getItemAt(0).toString();

            if (text.contains("mmsview")) {
                editText.setText(text);
            }
            else if (BuildConfig.DEBUG)
                editText.setText("You have a new MMS Picture or Video Message. To view your message, go to http://telstra.com/mmsview & enter User ID:61469099825 and Password:1hh2g0 within 14 days");
        }
    }

    @Override
    public void setupComponents() {
        super.setupComponents();

        editText = findViewById(R.id.sms_input);
        pager = (ViewPager) findViewById(R.id.pager);
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(pager);

        pagerContainer = findViewById(R.id.photos_container);

        // editText.addTextChangedListener(new TextWatcher() {
        //     @Override
        //     public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        //
        //     }
        //
        //     @Override
        //     public void onTextChanged(CharSequence s, int start, int before, int count) {
        //         downloadImages(s.toString(), false);
        //     }
        //
        //     @Override
        //     public void afterTextChanged(Editable s) {
        //
        //     }
        // });

        Button btn = findViewById(R.id.btn_clear);
        btn.setText(R.string.clear);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });

        btn = findViewById(R.id.btn_go);
        btn.setText(R.string.download);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (canWrite)
                    startBackgroundTask(OP_DOWNLOAD, editText.getText().toString(), true);
                else {
                    Toast.makeText(getActivity(), "Please allow the write access to the external storage", Toast.LENGTH_LONG);
                    checkPermissions();
                }

            }
        });
    }

    private void downloadImages(String mms, boolean b) {
        List files  = null;

        pagerContainer.setVisibility(View.GONE);

        String[] credentials = controller.parseText(mms);

        if (null == credentials[0] || null == credentials[1]) {
            Toast.makeText(getActivity(), "Unable to locate user id and password", Toast.LENGTH_LONG);
        }
        else {
            files = controller.downloadImages(credentials[0], credentials[1], b);
        }

        setResult(files);
    }

    @Override
    protected void onPageBackgroundTaskFinished(int id, Object o) {
        if (id == OP_DOWNLOAD) {
            List files = (List) getResult();
            if (null != files && files.size() > 0) {
                showAlbum(files);
            } else {
                Toast.makeText(getActivity(), "Sorry, couldn't download the media file(s) for you", Toast.LENGTH_LONG);
            }
        }
        else if (id == OP_LOAD) {
            List files = (List) getResult();
            if (null != files && files.size() > 0) {
                showAlbum(files);
            }
            else
                Toast.makeText(getActivity(), "Sorry, no media files found", Toast.LENGTH_LONG);
        }
        else
            super.onPageBackgroundTaskFinished(id, o);
    }

    private void showAlbum(List files) {
        pagerContainer.setVisibility(View.VISIBLE);

        pager.setAdapter(new ImagePagerAdapter(getActivity(), getSupportFragmentManager(), files));
        pager.setPageMargin((int) getResources().getDimension(R.dimen.card_padding) / 4);
        pager.setOffscreenPageLimit(3);
    }

    @Override
    protected boolean executeBackgroundTask(int id, Object... params) {
        if (id == OP_DOWNLOAD) {
            downloadImages((String) params[0], (Boolean) params[1]);
            return true;
        }
        else if (id == OP_LOAD) {
            loadLastDownload();
            return true;
        }
        return super.executeBackgroundTask(id, params);
    }


    @Override
    public void onRequestedPermissionsGranted(String permission) {
        super.onRequestedPermissionsGranted(permission);

        if (permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            canWrite = true;
    }
}
