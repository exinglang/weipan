package com.puxtech.weipan.util;

import android.content.Context;
import android.os.Environment;

import com.puxtech.weipan.Constant;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by mac on 15/11/6.
 */
public class FileUtils {
    /**
     * 在存储卡目录下创建对应路径下的文件,如果没有存储卡,则在系统CACHE中创建
     *
     * @param context
     * @param dirName  SDCARD/DIRNAME
     * @param fileName 文件名
     */
    public static File createNewFile(Context context, String dirName, String fileName) {


        File dir;
        String pkName = context.getPackageName(); // 获得应用程序的包名
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)
                )

        {
            dir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator
                    + dirName);
            if (!dir.exists())
                dir.mkdir();
            dir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator
                    + dirName + File.separator + pkName);
            if (!dir.exists())
                dir.mkdir();

        } else

        {
            dir = context.getCacheDir();
        }

        if (!dir.exists())
            dir.mkdir();
        File bitmapFile = new File(dir, fileName);
        if (!bitmapFile.exists())

        {
            try {
                bitmapFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bitmapFile;
    }

    /**
     * 获取指定目录下的指定文件名
     * @param context
     * @param dirName
     * @param fileName
     * @return
     */
    public static File getFile(Context context, String dirName, String fileName) {


        File dir;
        String pkName = context.getPackageName(); // 获得应用程序的包名
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)
                )

        {
            dir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator
                    + dirName);
            if (!dir.exists())
                dir.mkdir();
            dir = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath()
                    + File.separator
                    + dirName + File.separator + pkName);
            if (!dir.exists())
                dir.mkdir();

        } else

        {
            dir = context.getCacheDir();
        }

        if (!dir.exists())
            dir.mkdir();
        File bitmapFile = new File(dir, fileName);
//        if (!bitmapFile.exists())
//
//        {
//            try {
//                bitmapFile.createNewFile();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        return bitmapFile;
    }
}
