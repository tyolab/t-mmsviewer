package au.com.tyo.mmsviewer.ui.page;

import android.app.Activity;
import android.content.ClipboardManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.widget.AppCompatEditText;

import au.com.tyo.app.CommonLog;
import au.com.tyo.app.ui.page.PageWebView;
import au.com.tyo.mmsviewer.Controller;
import au.com.tyo.mmsviewer.R;

import static android.content.Context.CLIPBOARD_SERVICE;

/**
 * Created by Eric Tang (eric.tang@tyo.com.au) on 27/11/17.
 */

public class PageMain extends PageCommon implements PageWebView.WebPageListener {

    private AppCompatEditText editText;

    private String url;

    /**
     * @param controller
     * @param activity
     */
    public PageMain(Controller controller, Activity activity) {
        super(controller, activity);

        setContentViewResId(R.layout.page_main);
        setPageMonitor(this);
    }

    @Override
    public void onActivityStart() {
        super.onActivityStart();

        getWebView().loadUrl("http://telstra.com/mmsview");

        ClipboardManager clipboard = (ClipboardManager) getActivity().getSystemService(CLIPBOARD_SERVICE);

        if (clipboard.hasPrimaryClip() && clipboard.getPrimaryClip().getItemCount() > 0) {
            String text = clipboard.getPrimaryClip().getItemAt(0).toString();

            if (text.contains("mmsview")) {
                editText.setText(text);
            }
        }

        if (!controller.hasUserLoggedIn()) {
            controller.getUi().gotoLoginPage();

            finish();
        }
    }

    @Override
    public void setupComponents() {
        super.setupComponents();

        editText = findViewById(R.id.sms_input);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        Button btn = findViewById(R.id.btn_clear);
        btn.setText(R.string.clear);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

    @Override
    public void onPageFinishedLoading(WebView webView, String url) {
        CommonLog.i(this, "Url (Callback): " + url);
        CommonLog.i(this, "Url (Intended): " + getWebView().getUrl());

        
    }
}
