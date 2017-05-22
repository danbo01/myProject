package com.example.administrator.android442hook;

/**
 * Created by Danbo on 2017/5/22.
 */

public class CheckApk {

    public static boolean checkApkFile(String fileName)
    {
        if(fileName.equals(ApkFileName.QQBRO))
        {
            return false;
        }
        return true;
    }

}
