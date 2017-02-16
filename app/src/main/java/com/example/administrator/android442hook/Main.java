package com.example.administrator.android442hook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AndroidAppHelper;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;


/**
 * Created by zhangqijun on 2017/2/13.
 */

public class Main implements IXposedHookLoadPackage {
    public XC_MethodHook getPackageInfoHook;
    public XC_MethodHook showDialogHook;
    public XC_MethodHook onCreateDialogHook;
    public Activity activity;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        XposedBridge.log("This is my hook>>>>>>>>>");

        //if not this package which i need
        if (!loadPackageParam.packageName.equals(Common.PACKAGEINSTALLER_PKG))
            return;

        try {
            getPackageInfoHookMethod(loadPackageParam);
        } catch (Exception e) {
            XposedBridge.log(e.getMessage());
        }

        try{
            showDialogHookMethod(loadPackageParam);
        }catch (Exception e){
            XposedBridge.log(e.getMessage());
        }

        try{
            onCreateDialogHookMethod(loadPackageParam);
        }catch (Exception e){
            XposedBridge.log(e.getMessage());
        }

    }//handleLoadPackage

    private void onCreateDialogHookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam) {
        XposedBridge.log("~~~~~~~~~~~~~~~~~~~~~onCreateDialogHookMethod ");
        onCreateDialogHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                int id=(int)param.args[0];
                XposedBridge.log("~~~~~~~~~~~~~~~~~~~~~onCreateDialogHookMethod::id>>>"+id);
            }
        };

        XposedHelpers.findAndHookMethod(Common.ACTIVITY,
                loadPackageParam.classLoader,
                "onCreateDialog",
                int.class,
                Bundle.class,
                showDialogHook);
    }

    private void showDialogHookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam)  throws Throwable  {
        XposedBridge.log("~~~~~~~~~~~~~~~~~~~~~showDialogHookMethod ");
        showDialogHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                int id=(int)param.args[0];
                XposedBridge.log("~~~~~~~~~~~~~~~~~~~~~showDialogHookMethod::id>>>>"+id);
                //param.args[0]=++id;
                param.setResult(++id);

            }
        };

        XposedHelpers.findAndHookMethod(Common.ACTIVITY,
                loadPackageParam.classLoader,
                "showDialog",
                int.class,
                showDialogHook);
    }


    private void getPackageInfoHookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedBridge.log("~~~~~~~~~~~~~~~~~~~~~getPackageInfoHookMethod ");

        getPackageInfoHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("getPackageInfoHook::beforeHookedMethod...");

                Uri packageName = (Uri) param.args[0];
                XposedBridge.log("+++++++++beforeHookedMethod::packageName:" + packageName.toString());
                XposedBridge.log("+++++++++beforeHookedMethod::path:" + packageName.getPath());

            }////beforeHookedMethod

        };

        //Now we hook the function named getPackageInfo from the com.android.packageinstaller.PackageUtil.java
        XposedHelpers.findAndHookMethod(Common.PACKAGEUTILS,
                loadPackageParam.classLoader,
                "getPackageInfo",
                Uri.class,
                getPackageInfoHook
        );
    }//getPackageInfoHookMethod

}
