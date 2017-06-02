package com.example.administrator.android442hook;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.net.URI;

import de.robv.android.xposed.XposedBridge;

/**
 * Created by Danbo on 2017/6/2.
 */

public class CheckPermission {

    ////addr: "/data/hellojni.apk"
    public static  boolean checkPackagePermission(String addr, Activity activity){
        boolean flag=false;
        PackageManager pm=activity.getPackageManager();

        PackageInfo info;
        try {
            info = pm.getPackageArchiveInfo(addr, PackageManager.GET_ACTIVITIES);
            //info = pm.getPackageInfo(addr, PackageManager.GET_PERMISSIONS);
            String result = null;
            String[] packagePermissions = info.requestedPermissions;
            XposedBridge.log("name"+info.packageName);
            if (packagePermissions != null) {
                for (int j = 0; j < packagePermissions.length; j++) {
                    XposedBridge.log("result"+packagePermissions[j])  ;

                }
                flag=true;
            }else {
                XposedBridge.log("name"+ info.packageName + ": no permissions");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return flag;
    }

}
