package au.com.tyo.mmsviewer;

import android.content.Context;
import android.content.Intent;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.com.tyo.android.CommonCache;
import au.com.tyo.android.utils.SimpleDateUtils;
import au.com.tyo.app.CommonApp;
import au.com.tyo.app.CommonLog;
import au.com.tyo.app.PageAgent;
import au.com.tyo.app.ui.page.PageFormEx;
import au.com.tyo.data.ContentTypes;
import au.com.tyo.io.IO;
import au.com.tyo.io.WildcardFileStack;
import au.com.tyo.mmsviewer.model.CredentialMap;
import au.com.tyo.mmsviewer.ui.UI;
import au.com.tyo.mmsviewer.ui.page.PageCommon;
import au.com.tyo.services.HttpJavaNet;
import au.com.tyo.utils.XML2TXT;

import static au.com.tyo.android.utils.CacheManager.CacheLocation.EXTERNAL_STORAGE;

/**
 * Created by Eric Tang (eric.tang@tyo.com.au) on 24/11/17.
 */

public class App extends CommonApp<UI, Controller, AppSettings> implements Controller {

    static {
        PageAgent.setPagesPackage(PageCommon.class.getPackage().getName());
    }

    private CredentialMap credentialMap;
    private HttpJavaNet httpJavaNet;

    private CommonCache localStorage;

    public App(Context context) {
        super(context);

        httpJavaNet = new HttpJavaNet();
        credentialMap = new CredentialMap();

        localStorage = new CommonCache(context, "TyoMmsViewer", EXTERNAL_STORAGE);
        localStorage.makeDirectory();
    }

    @Override
    public boolean hasUserLoggedIn() {
        return true;
    }

    @Override
    public String[] parseText(String mms) {
        String[] credentials = new String[2];

        try {
            Pattern pattern = Pattern.compile("User ID:\\d+");
            Matcher matcher = pattern.matcher(mms);
            if (matcher.find()) {
                String strUserId = matcher.group();
                credentials[0] = strUserId.split(":")[1];
            }
        }
        catch (Exception ex) {
            CommonLog.e(this, "failed to get user id", ex);
        }

        try {
            Pattern pattern = Pattern.compile("Password:[a-zA-Z0-9]+ ");
            Matcher matcher = pattern.matcher(mms);
            if (matcher.find()) {
                String password = matcher.group();
                credentials[1] = password.split(":")[1].trim();
            }
        }
        catch (Exception ex) {
            CommonLog.e(this, "failed to get user id", ex);
        }

        return  credentials;
    }

    @Override
    public void bindDataFromOtherApps(Intent intent) {
        // do nothing until we have such requirement
    }

    @Override
    public PageFormEx.FormHandler getFormHandler() {
        return null;
    }

    @Override
    public List downloadImages(String userId, String password, boolean forceDownload) {
        List files = null;
        credentialMap.setPassword(password);
        credentialMap.setUserId(userId);
        try {
            InputStream is = httpJavaNet.post("https://mymessages.telstra.com/OTP/Otp", credentialMap.getCredentialsMap());

            String albumName = userId + "_" + password; // SimpleDateUtils.dateToYMDHM(Calendar.getInstance().getTime());

            File folder = null;
            if (localStorage.exists(albumName)) {
                folder = new File(localStorage.getCacheDir() + File.pathSeparator + albumName);
                WildcardFileStack stack = new WildcardFileStack(folder);
                stack.listFiles();

                if (stack.size() > 0 && !forceDownload) {
                    return loadLastAlbum();
                }
            }

            String html = httpJavaNet.processInputStreamAsString(is);
            Document doc = Jsoup.parse(html);
            Elements media = doc.select("a");
            for (Element link : media) {
                String linkHref = link.attr("href");

                if (linkHref.startsWith("getOtpMedia?id=")) {
                    String fileName = XML2TXT.cleanAllTags(link.html());

                    if (ContentTypes.isImage(fileName) || ContentTypes.isVideo(fileName)) {

                        if (null == folder)
                            folder = localStorage.createDirectory(albumName);

                        String imageUrl = "https://mymessages.telstra.com/OTP/" + linkHref;

                        // We have to reset the connection before next retrieval, but the cookies remain.
                        httpJavaNet.reset();
                        InputStream imageIs = httpJavaNet.getInputStream(imageUrl);

                        File targetFile = localStorage.createFile(folder.getName(), fileName);
                        IO.writeFile(targetFile, imageIs);

                        if (null == files)
                            files = new ArrayList();

                        files.add(targetFile);

                        imageIs.close();
                    }
                }
            }

        } catch (Exception e) {
            CommonLog.e(this, "Failed to login with user id: " + userId + ", password: " + password, e);
        }

        return files;
    }

    @Override
    public List loadLastAlbum() {
        List files = null;
        WildcardFileStack stack = new WildcardFileStack(localStorage.getCacheDir());
        stack.setFolderOnly(true);
        stack.listFiles();
        stack.sortByDate();

        if (stack.size() > 0) {
            File firstFolder = stack.get(0);
            loadAlbum(files, firstFolder);
        }
        return files;
    }

    public List loadAlbum(List files, File firstFolder) {
        WildcardFileStack lastStack = new WildcardFileStack(firstFolder);
        lastStack.listFiles();

        files = new ArrayList();
        File file = lastStack.next();
        while (null != file) {
            if (ContentTypes.isImage(file.getName()))
                files.add(file);
            file = lastStack.next();
        }
        return files;
    }
}
