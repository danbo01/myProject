package com.example.administrator.android442hook;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Danbo on 2017/5/22.
 */
public class CheckFileName {
    /**
     *
     *
    public static void main (String[] args) throws java.lang.Exception
    {
        String abUrl = "C:\\file1\\02File";
        testInstance(abUrl);
        abUrl = "/storage/emulated/0/Tencent/QQfile_recv/SogouInput_android_v8.10.2_sweb.apk";
        testInstance(abUrl);
        abUrl = "/storage/emulated/0/Tencent/QQfile_recv/SogouInput_android_v8.10.2_sweb.apkk"; ///true
        testInstance(abUrl);
        abUrl = "/storage/emulated/0/Tencent/QQfile_recv/SogouInput_android_v8.10.2_sweb";
        testInstance(abUrl);
        abUrl = "C:/storage/emulated/0/Tencent/QQfile_recv/SogouInput_android_v8.10.2_sweb";
        testInstance(abUrl);
    }

    public static void testInstance(String abUrl)
    {
        System.out.println(" " +getFileName(abUrl));
    }
    */


    public static String getFileName(String abUrl){
        int len=abUrl.length();
        if(len<4)
        {
            return Common.NOAPK;
        }
        String subApk=abUrl.substring(len-3,len);
        String apk=Common.APK;
        if(!apk.equals(subApk))
        {
            return Common.NOAPK;
        }
        int dot=abUrl.lastIndexOf(Common.PIT);
        subApk=abUrl.substring(dot+1,len);
        return subApk;
    }

}
