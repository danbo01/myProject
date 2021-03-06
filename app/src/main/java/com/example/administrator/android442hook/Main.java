package com.example.administrator.android442hook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.pm.ApplicationInfo;
import android.net.Uri;

import java.io.File;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


/**
 * Created by Danbo on 2017/2/13.
 */
public class Main implements IXposedHookLoadPackage {
    public XC_MethodHook getPackageInfoHook;
    public XC_MethodHook getAppSnippetHook;
    public XC_MethodHook onCreateDialogHook;
    private Activity activity = null;

    private String apkFilePath;
    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam)
            throws Throwable {
        XposedBridge.log("This is my hook>>>");

        if (!loadPackageParam.packageName.equals(Common.PACKAGEINSTALLER_PKG))
            return;

        try {
            getAppSnippetHookMethod(loadPackageParam);
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
        }

        try {
            getPackageInfoHookMethod(loadPackageParam);
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
        }

        try {
            //good apk
            if(!CheckApk.checkApkFile(apkFilePath))
                onCreateDialogHookMethod(loadPackageParam);
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
        }


    }//handleLoadPackage

    private void onCreateDialogHookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedBridge.log("~~~onCreateDialogHookMethod2 ");
        onCreateDialogHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Dialog dlg = new AlertDialog.Builder(activity)
                        .setTitle("温馨提醒")
                        .setMessage("亲，你安装软件有问题！")
                        .setPositiveButton("朕知道了", null)
                        .create();
                dlg.show();
            }
        };

        XposedHelpers.findAndHookMethod(Common.PACKAGEINSTALLERACTIVITY,
                loadPackageParam.classLoader,
                "startInstallConfirm",
                onCreateDialogHook);

    }

    private void getAppSnippetHookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam)
            throws Throwable {
        XposedBridge.log("~~~getAppSnippetHookMethod ");
        getAppSnippetHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param)
                    throws Throwable {
                activity = (Activity) param.args[0];
                if (activity != null) {
                    XposedBridge.log("~~~getAppSnippetHookMethod::activity>>>" + activity.getClass().toString());
                } else {
                    XposedBridge.log("~~~getAppSnippetHookMethod::activity>>> is null");
                }
            }
        };

        XposedHelpers.findAndHookMethod(Common.PACKAGEUTILS,
                loadPackageParam.classLoader,
                "getAppSnippet",
                Activity.class,
                ApplicationInfo.class,
                File.class,
                getAppSnippetHook);
    }

    private void getPackageInfoHookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam)
            throws Throwable {
        XposedBridge.log("~~~getPackageInfoHookMethod ");

        getPackageInfoHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param)
                    throws Throwable {
                XposedBridge.log("getPackageInfoHook::beforeHookedMethod...");

                File packageName = (File) param.args[0];
                XposedBridge.log("+++++++++beforeHookedMethod::packageName:" + packageName.toString());
                apkFilePath=packageName.toString();
                XposedBridge.log("apkFileName:"+CheckFileName.getFileName(apkFilePath));
            }////beforeHookedMethod

        };

        //Now we hook the function named getPackageInfo from the com.android.packageinstaller.PackageUtil.java
        XposedHelpers.findAndHookMethod(Common.PACKAGEUTILS,
                loadPackageParam.classLoader,
                "getPackageInfo",
                File.class,
                getPackageInfoHook
        );
    }//getPackageInfoHookMethod

}
