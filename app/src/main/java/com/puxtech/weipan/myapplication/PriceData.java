package com.puxtech.weipan.myapplication;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.puxtech.weipan.DiskLruCache;
import com.puxtech.weipan.data.entitydata.KLineEntity;
import com.puxtech.weipan.helper.PriceViewData;
import com.puxtech.weipan.runtimedata.PriceRuntimeData;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by mac on 15/11/9.
 */
public class PriceData {

    private PriceRuntimeData priceRuntimeData;
    Context context;
    PriceViewData priceViewData;
    private DiskLruCache mDiskLruCache;
String PRICE_URL;

    public String getPRICE_URL() {
        return PRICE_URL;
    }

    public void setPRICE_URL(String PRICE_URL) {
        this.PRICE_URL = PRICE_URL;
    }

    public PriceData(Context context) {
        this.context = context;
        priceRuntimeData = new PriceRuntimeData();
        priceViewData = new PriceViewData();
        try {
            // 获取图片缓存路径
            File cacheDir = getDiskCacheDir(context, "K_ZIP");
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            // 创建DiskLruCache实例，初始化缓存数据
            mDiskLruCache = DiskLruCache.open(cacheDir, getAppVersion(context), 1,
                    10 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public PriceRuntimeData getPriceRuntimeData() {
        return priceRuntimeData;
    }

    public void setPriceRuntimeData(PriceRuntimeData priceRuntimeData) {
        this.priceRuntimeData = priceRuntimeData;
    }

    public PriceViewData getPriceViewData() {
        return priceViewData;
    }

    public void setPriceViewData(PriceViewData priceViewData) {
        this.priceViewData = priceViewData;
    }

//    public KLineEntity getObjFromMemCache(int number, short cycle) {
//        String key = number + "_" + cycle;
//        return createMemoryCache().get(key);
//    }

    // ****************LRUCache相关*********************
//
//    public MyLruCache createMemoryCache() {
//        final int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
//                .getMemoryClass();
//        // Use 1/8th of the available memory for this memory cache.
//        final int cacheSize = 1024 * 1024 * memClass / 8;
//        MyLruCache mMemoryCache = new MyLruCache(context,cacheSize);
//        return mMemoryCache;
//    }
//    public String getHelperKey(KLineEntity entity) {
//        String key = entity.getNumber() + "_" + entity.getCycle()
//                + "_helper";
//        return key;
//    }
//    public void addObjToMemoryCache(KLineEntity entity) {
//        String key = getLRUKey(entity);
//        MyLruCache mMemoryCache = createMemoryCache();
//        if (mMemoryCache.get(key) != null) {
//            mMemoryCache.remove(key);
//        }
//        mMemoryCache.put(key, entity);
//    }
//    public String getLRUKey(KLineEntity entity) {
//        String key =entity.getNumber() + "_" + entity.getCycle();
//        return key;
//    }
//    public byte[] getObjFromDiskCache(String zipURL) {
//        String key = hashKeyForDisk(zipURL);
//        DiskLruCache.Snapshot ss = null;
//        try {
//            ss = mDiskLruCache.get(key);
//            if (ss != null) {
//                InputStream fis = (FileInputStream) ss.getInputStream(0);
//                ByteArrayOutputStream out = null;
//                BufferedInputStream in = null;
//
//                try {
//                    in = new BufferedInputStream(fis);
//                    out = new ByteArrayOutputStream();
//                    int len;
//                    while ((len = in.read()) != -1) {
//                        out.write(len);
//                    }
//                    return out.toByteArray();
//                } catch (final IOException e) {
//                    e.printStackTrace();
//                }
//            } else {
//                return null;
//            }
//        } catch (IOException e) {
//        }
//        return null;
//    }
    /**
     * 使用MD5算法对传入的key进行加密并返回。
     */
    public String hashKeyForDisk(String key) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(key.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(key.hashCode());
        }
        return cacheKey;
    }
    /**
     * 根据传入的uniqueName获取硬盘缓存的路径地址。
     */
    public File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + uniqueName);
    }
    private String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }
    /**
     * 获取当前应用程序的版本号。
     */
    public int getAppVersion(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 1;
    }
    public void addObjToFileCache(String zipURL, byte[] data) {
        String key = hashKeyForDisk(zipURL);
        DiskLruCache.Editor editor = null;
        try {
            editor = mDiskLruCache.edit(key);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (editor != null) {
            OutputStream os;
            try {
                os = editor.newOutputStream(0);
                BufferedOutputStream bos = new BufferedOutputStream(os);
                bos.write(data);
                editor.commit();
            } catch (IOException e) {
                try {
                    editor.abort();
                } catch (IOException e1) {
                }
            }
        }
    }
}
