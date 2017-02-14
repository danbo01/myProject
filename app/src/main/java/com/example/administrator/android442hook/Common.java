package com.example.administrator.android442hook;

import android.os.Build;

/**
 * Created by Administrator on 2017/2/12.
 */

public class Common {

    ///
    public static final int SDK = Build.VERSION.SDK_INT;

    ///
    public static final String PACKAGEMANAGERSERVICE = "com.android.server.pm.PackageManagerService";
    public static final String PREF_ENABLE_MODULE = "enable_module";
    public static final int SYSTEM_UID = 1000;
    public static final String PREF_DISABLE_CHECK_LUCKY_PATCHER = "disable_check_lucky_patcher";
    public static final String LUCKYPATCHER_PKG = "com.android.vending.billing.InAppBillingService.LUCK";
    public static final String EMPTY_STRING = "";
    public static final String PREF_ENABLE_BACKUP_ALL_APPS = "enable_backup_all_apps";
   // public static final boolean MARSHMALLOW_NEWER = SDK >= Build.VERSION_CODES.M; // MARSHMALLOW
    public static final String ANDROID_PKG = "android";
    public static final String PACKAGEINSTALLER_PKG = "com.android.packageinstaller";
    public static final String GOOGLE_PACKAGEINSTALLER_PKG = "com.google.android.packageinstaller";
    public static final String PACKAGEINSTALLERACTIVITY = "com.android.packageinstaller.PackageInstallerActivity";
    public static final String PREF_ENABLE_INSTALL_UNKNOWN_APP = "enable_install_unknown_apps";
    public static final String PREF_DISABLE_VERIFY_APP = "disable_verify_apps";
    public static final String PACKAGEUTILS="com.android.packageinstaller.PackageUtil";

    public static final String BROADCAST_ACTION="";



}
