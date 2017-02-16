package com.example.administrator.android442hook;

import android.app.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;

import de.robv.android.xposed.XposedBridge;


/**
 * Created by Administrator on 2017/2/13.
 */

public class MyActivity extends Activity {
    public static MyActivity ma;


    public static MyActivity getInstance(){
        XposedBridge.log("MyActivity....");
        if(ma==null){
            ma=new MyActivity();
        }
        return ma;
    }


    @Override
    protected  void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Dialog dlg=new AlertDialog.Builder(this).setTitle("提醒").setMessage("消息提示！")
                .setPositiveButton("朕知道了",null).create();
        dlg.show();
        XposedBridge.log("MyActivity:: is starting");
    }


 }
