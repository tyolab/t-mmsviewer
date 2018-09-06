package au.com.tyo.sample.ui.page;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import au.com.tyo.sample.Controller;
import au.com.tyo.sample.R;

/**
 * Created by Eric Tang (eric.tang@tyo.com.au) on 11/9/17.
 */

public class PageEmailPassword extends PageCommon {

    // UI references.
    protected AutoCompleteTextView mEmailView;
    protected EditText mPasswordView;

    protected View mProgressView;

    private View mInputFormView;

    /**
     * @param controller
     * @param activity
     */
    public PageEmailPassword(Controller controller, Activity activity) {
        super(controller, activity);
    }

    public static boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public static boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    @Override
    public void setupComponents() {
        super.setupComponents();

        // setupActionBar();
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mProgressView = findViewById(R.id.progress);

        mInputFormView = findViewById(R.id.input_form);

        populateAutoComplete();
    }

    protected void populateAutoComplete() {
        // no ops
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    protected String getEmailString() {
        return mEmailView.getText().toString().trim();
    }

    protected String getPasswordString() {
        return mPasswordView.getText().toString().trim();
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    protected void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mInputFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mInputFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mInputFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mInputFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
