package com.example.administrator.android442hook;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Created by Administrator on 2017/2/15.
 */

public class ActivityHook extends XC_MethodHook {

    private static volatile Activity _currentActivity = null;

    public static Activity getCurrentActivity() {
        return _currentActivity;
    }

    @Override
    protected void afterHookedMethod(MethodHookParam param)
            throws Throwable {
        _currentActivity = (Activity) param.getResult();
        Dialog dlg=new AlertDialog.Builder(_currentActivity).setTitle("提醒").setMessage("消息提示！")
                .setPositiveButton("朕知道了",null).create();
        dlg.show();
    }

}
