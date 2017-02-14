package com.example.administrator.android442hook;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

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
    public XC_MethodHook onCreateDialogHook;

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        XposedBridge.log("This is my hook...");

        //if not this package which i need
        if (!loadPackageParam.packageName.equals(Common.PACKAGEINSTALLER_PKG))
            return;

        try {
            getPackageInfoHookMethod(loadPackageParam);
        }catch(Exception e){
            XposedBridge.log(e.getMessage());
        }

        //
        try {
            //onCreateDialogHookMethod(loadPackageParam);
        }catch(Exception e){
            XposedBridge.log(e.getMessage());
        }
        sendBrocastMethod();

    }//handleLoadPackage


    public void sendBrocastMethod(){
        XposedBridge.log("~~~+++++++++++++++++++~~sendBrocastMethod ");
        Intent intent = new Intent();

        intent.setAction(Common.BROADCAST_ACTION);
        intent.putExtra("name", "qqyumidi");

        //sendBroadcast(intent);

    }

    private void onCreateDialogHookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam) throws  Throwable{
        XposedBridge.log("~~~+++++++++++++++++++~~onCreateDialogHookMethod ");

        onCreateDialogHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("onCreateDialogHookMethod::beforeHookedMethod...");
                int id=(int)param.args[0];
                Bundle b=(Bundle) param.args[1];
                XposedBridge.log("id------"+id);
                id=1;
                param.args[0]=id;
                param.args[1]=b;
                param.setResult(true);


            }////beforeHookedMethod

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("onCreateDialogHookMethod::afterHookedMethod...");
            }////afterHookedMethod
        };

        Class<?> in=int.class;
        Class <?> bdl= Bundle.class;

        //Now we hook the function named getPackageInfo from the com.android.packageinstaller.PackageUtil.java
        XposedHelpers.findAndHookMethod(Common.PACKAGEINSTALLERACTIVITY,
                loadPackageParam.classLoader,
                "onCreateDialog",
                in,
                bdl,
                onCreateDialogHook
        );
    }

    private void getPackageInfoHookMethod(XC_LoadPackage.LoadPackageParam loadPackageParam) throws  Throwable{
        XposedBridge.log("~~~~~~~~~~~~~~~~~~~~~getPackageInfoHookMethod ");

        getPackageInfoHook = new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("getPackageInfoHook::beforeHookedMethod...");

                Uri packageName = (Uri) param.args[0];
                XposedBridge.log("+++++++++beforeHookedMethod::packageName:" + packageName.toString());
                XposedBridge.log("+++++++++beforeHookedMethod::path:" + packageName.getPath());
                XposedBridge.log("+++++++++beforeHookedMethod::FileName:"+packageName.getPathSegments().get(4));

            }////beforeHookedMethod

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                XposedBridge.log("getPackageInfoHook::afterHookedMethod...");
            }////afterHookedMethod
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
