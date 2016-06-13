package com.puxtech.weipan.helper;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.util.Base64;

import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.TradeEntity;
import com.puxtech.weipan.data.entitydata.KPointEntity;
import com.puxtech.weipan.data.entitydata.TimeLineEntity;
import com.puxtech.weipan.data.entitydata.TimeLinePaintEntity;
import com.puxtech.weipan.data.entitydata.TimePointEntity;
import com.puxtech.weipan.util.SharedPreferenceManager;
import com.puxtech.weipan.util.TimeUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class StockChartsKLineViewHelper {
    public StockChartsKLineViewHelper(Context context, TradeEntity tradeEntity) {
        MyApplication myapp = (MyApplication) context.getApplicationContext();
        if (tradeEntity == myapp.getShipanTradeEntity()) {

            KLINE_DIR = "/KLineDataShiPan/";
        } else {

            KLINE_DIR = "/KLineDataMoNiPan/";
        }


    }

    private String KLINE_DIR ;

    public boolean checkKLineDataExist(Context context, int commodityNumber,
                                       int cycle) {
        File cacheDir = context.getCacheDir();
        File fileDir = new File(cacheDir.getPath() + KLINE_DIR);
        if (!fileDir.exists()) {
            return false;
        }
        File kLineFile = new File(fileDir.getPath() + "/"
                + getFileName(commodityNumber, cycle));// 文件命名规则：组ID_周期_商品编号
        return kLineFile.exists();
    }

    /**
     * 获取缓存文件的名字，组ID_周期_商品编号
     */
    private String getFileName(int commodityNumber, int cycle) {
        return cycle + "_" + commodityNumber;
    }

    /**
     * 从本地缓存获取K线数据实体
     */
    public List<KPointEntity> getAllKPointFromFile(Context context,
                                                   int commodityNumber, int cycle) {
        List<KPointEntity> recordList = new ArrayList<>();
        byte[] bytes = null;
        try {
            bytes = getKLineDataFromFile(context, commodityNumber, cycle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bytes != null) {
            // 根据每个柱子的大小计算柱子总数量
            int recordNum = bytes.length / KPointEntity.getByteBufferSize();
            ByteBuffer buffer = ByteBuffer.wrap(bytes);
            for (int i = 0; i < recordNum; i++) {
                KPointEntity point = new KPointEntity();
                point.setTime(buffer.getLong());
                point.setOpenPrice(buffer.getDouble());
                point.setClosePrice(buffer.getDouble());
                point.setHighestPrice(buffer.getDouble());
                point.setLowestPrice(buffer.getDouble());
                point.setVolume(buffer.getInt());
                point.setPriceId(buffer.getLong());
                point.setPma5(buffer.getDouble());
                point.setPma10(buffer.getDouble());
                point.setPma20(buffer.getDouble());
                point.setPma30(buffer.getDouble());
                point.setShortEMA(buffer.getDouble());
                point.setLongEMA(buffer.getDouble());
                point.setDif(buffer.getDouble());
                point.setDea(buffer.getDouble());
                point.setMacd(buffer.getDouble());
                point.setRsv(buffer.getDouble());
                point.setK(buffer.getDouble());
                point.setD(buffer.getDouble());
                point.setJ(buffer.getDouble());
                recordList.add(point);
            }
        }
        return recordList;
    }

    /**
     * 从本地缓存获取K线数据的byte数组
     *
     * @throws IOException
     */
    private byte[] getKLineDataFromFile(Context context, int commodityNumber,
                                        int cycle) throws IOException {
        File cacheDir = context.getCacheDir();
        File fileDir = new File(cacheDir.getPath() + KLINE_DIR);
        if (!fileDir.exists()) {
            return null;
        }
        File kLineFile = new File(fileDir.getPath() + "/"
                + getFileName(commodityNumber, cycle));// 文件命名规则：周期_商品编号
        FileInputStream fis = new FileInputStream(kLineFile);
        ByteArrayOutputStream out = new ByteArrayOutputStream(1024);
        byte[] buffer = new byte[1024];
        int size;
        while ((size = fis.read(buffer)) != -1) {
            out.write(buffer, 0, size);
        }
        fis.close();
        return out.toByteArray();
    }

    /**
     * 把K线数据保存到文件中
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public void saveKLineDataToFile(Context context, int commodityNumber,
                                    int cycle, byte[] data) {
        SharedPreferenceManager preManager = new SharedPreferenceManager(
                context, SharedPreferenceManager.FILE_NAME_COMMODITY);
        long lastTime = preManager.getLong(context,
                SharedPreferenceManager.KEY_KLINE_LAST_REFRESH_TIME_PRENAME
                        + cycle + "_" + commodityNumber, 0l);
        // 如果上次更新的时间，在本交易日的06：00之后，就算作同一天
        long todaySix = TimeUtil.getCurrentTradeStartTime();
        boolean append;
        // 如果是同一交易日，就保留以前的,在末尾添加
// 如果不是同一交易日，就删除以前的
        append = lastTime >= todaySix;
        File cacheDir = context.getCacheDir();
        File fileDir = new File(cacheDir.getPath() + KLINE_DIR);
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File kLineFile = new File(fileDir.getPath() + "/"
                + getFileName(commodityNumber, cycle));// 文件命名规则：周期_商品编号
        try {
            FileOutputStream fos = new FileOutputStream(kLineFile, append);
            fos.write(data);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 记录下当前时间
        preManager.putLong(context,
                preManager.getKLineLastRefreshTimeKey(cycle, commodityNumber),
                System.currentTimeMillis());
    }

//    /**
//     * 去除相同的k点合在一起然后返回
//     *
//     */
//    public ArrayList<KPointEntity> mergeSameKPointEntity(List<KPointEntity> recordList,
//                                                List<KPointEntity> recordList2) {
//        ArrayList<KPointEntity> temp = new ArrayList<KPointEntity>();
//        KPointEntity kpe;
//        for (int i = 0, len = recordList.size(); i < len; i++) {
//            kpe = recordList.get(i);
//            if (!recordList2.contains(kpe)) {
//                temp.add(kpe);
//            }
//        }
//        temp.addAll(recordList2);
//        Collections.sort(temp, new Comparator<KPointEntity>() {
//            @Override
//            public int compare(KPointEntity lhs, KPointEntity rhs) {
//                if (lhs.getTime() > rhs.getTime()) {
//                    return 1;
//                } else if (lhs.getTime() < rhs.getTime()) {
//                    return -1;
//                }
//                return 0;
//            }
//        });
//        return temp;
//    }
//    /**
//     * 删除某个周期的某个商品的本地缓存文件
//     *
//     * @param commodityNumber
//     *            商品编号
//     * @param cycle
//     *            周期id
//     */
//    @SuppressWarnings("ResultOfMethodCallIgnored")
//    public void deleteKLineData(Context context, int commodityNumber, int cycle) {
//        File cacheDir = context.getCacheDir();
//        File fileDir = new File(cacheDir.getPath() + KLINE_DIR);
//        if (!fileDir.exists()) {
//            return;
//        }
//        File kLineFile = new File(fileDir.getPath() + "/"
//                + getFileName(commodityNumber, cycle));// 文件命名规则：周期_商品编号
//        kLineFile.delete();
//    }

    /**
     * 把文件的最后一个柱子删掉，补上传过来的柱子
     */
    public void replaceLastKPointInFile(Context context, int commodityNumber,
                                        int cycle, byte[] kPointBytes) {
        File cacheDir = context.getCacheDir();
        File fileDir = new File(cacheDir.getPath() + KLINE_DIR);
        if (!fileDir.exists()) {
            return;
        }
        File kLineFile = new File(fileDir.getPath() + "/"
                + getFileName(commodityNumber, cycle));// 文件命名规则：周期_商品编号
        try {
            // allBytes是所有缓存的柱子
            byte[] allBytes = getKLineDataFromFile(context, commodityNumber,
                    cycle);
            // oldBytes是去掉最后一个柱子后的所有缓存
            byte[] oldBytes;
            assert allBytes != null;
            oldBytes = new byte[allBytes.length
                    - KPointEntity.getByteBufferSize()];
            System.arraycopy(allBytes, 0, oldBytes, 0, allBytes.length
                    - KPointEntity.getByteBufferSize());
            // newBytes是oldBytes + 新柱子
            byte[] newBytes = new byte[oldBytes.length + kPointBytes.length];
            System.arraycopy(oldBytes, 0, newBytes, 0, oldBytes.length);
            System.arraycopy(kPointBytes, 0, newBytes, oldBytes.length,
                    kPointBytes.length);
            FileOutputStream fos = new FileOutputStream(kLineFile, false);
            fos.write(newBytes);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 保存在本地的时候，记录下最新刷新时间
        SharedPreferenceManager preManager = new SharedPreferenceManager(
                context, SharedPreferenceManager.FILE_NAME_COMMODITY);
        preManager.putLong(context,
                preManager.getKLineLastRefreshTimeKey(cycle, commodityNumber),
                System.currentTimeMillis());
    }


//    public KLineEntity getKLEFromZip(String zipUrl, int zuId) {
//        byte[] kData = null;
//        // 尝试从sd卡获取压缩包
//
//        String zipName=zipUrl.substring(zipUrl.lastIndexOf('/')+1);
//
//        kData = MyApplication.getInstance().getObjFromDiskCache(zipUrl);
//        if (kData == null || kData.length == 0) {
//            // sd卡没缓存压缩包从网络获取
//            kData = readByteArrayFromURL(zipUrl);
//            if (kData == null) {
//                PriceLogger.d("kLineLoadAll...kData == null");
//                return null;
//            }
//            MyApplication.getInstance().addObjToFileCache(zipUrl, kData);
//        }
//        byte[] secret = getSecret(zipName.substring(0,3), zuId);
//        try {
//            kData = AES.decrypt(kData,
//                    Base64.decode(new String(secret), Base64.DEFAULT));
//            kData=decompress(kData);
//        } catch (Exception e) {
//            return null;
//        }
//        if(kData==null)return null;
//        ByteBuffer buffer = ByteBuffer.wrap(kData);
//        KLineEntity kzip = convertOneKLine(buffer);
//        return kzip;
//    }
}
