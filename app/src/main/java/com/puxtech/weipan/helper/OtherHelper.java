package com.puxtech.weipan.helper;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.entitydata.ContentsServerEnvEntity;
import com.puxtech.weipan.util.ActivityUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by mac on 16/1/7.
 */
public class OtherHelper {

    public static final String SELECTTIME_NOT_BEYOND_TODAY = "选择的时间不得晚于今天";
    public static final String STARTTIME_MORE_THAN_NOW = "开始日期不得晚于今天";
    public static final String ENDTIME_MORE_THAN_NOW = "结束日期不得晚于今天";
    public static final String FABUENDTIME_MORE_THAN_NOW = "发布结束日期不得晚于今天";
    public static final String START_NOT_EARLY_ONE_YEAR = "开始时间不得早于一年以前的日期";
    public static final String SELECT_TIME = "选择时间";
    public static final String CENTER_TIME_SHORT_ONT_MONTHS = "查询日期间隔不能大于三十一天";

    public static final String STARTTIME_MORE_ENDTIME_IN = "发布开始时间不得晚于结束时间";
    public static final String STARTTIME_MORE_ENDTIME = "开始时间不得晚于结束时间";
    public static final String STARTTIME_MORE_ENDTIME_UN = "失效开始时间不得晚于结束时间";

    /**
     * 获取ImageView视图的同时加载显示url
     *
     * @param
     * @return
     */
    public static ImageView getImageView(Context context, String url) {
        ImageView imageView = (ImageView) LayoutInflater.from(context).inflate(
                R.layout.view_banner, null);
        ImageLoader.getInstance().displayImage(url, imageView);
        return imageView;
    }


    /**
     * 配置ImageLoder
     */
    public static void configImageLoader(Context context) {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                        // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context.getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return
     */
    public static int getVersionCode(Context context)// 获取版本号(内部识别号)
    {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    // 开始一个TIMER
    public void startTimer(Timer timer, TimerTask task) {
        if (timer == null) {
            timer = new Timer();
        }
//        if (task == null) {
//            task = new TimerTask() {
//
//                @Override
//                public void run() {
//                    updatedetail(-1);// 实时刷新
//
//                }
//            };
//
//        }
        if (timer != null && task != null) {
            timer.schedule(task, 2000, 1000);

        }
    }

    // 结束一个TIMER
    public void stopTimer(Timer timer, TimerTask task) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }

    }

    public static void selectData(final Context context, final View view, final boolean needPanDuan) {
//        final int id = view.getId();
        ActivityUtils mActivityUtils = new ActivityUtils();
        final Calendar initCalendar = getCalendarOfString(((TextView) view).getText().toString());
        DatePickerDialog ds = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year,
                                          int monthOfYear, int dayOfMonth) {
                        GregorianCalendar selectGregorianCalendar = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                        if (needPanDuan) {
                            //开始和结束日期都需要判断
                            if (isBeyondToday(selectGregorianCalendar)) {
                                //选择的日期是否超过了今天
                                ActivityUtils.showCenterToast(context,
                                        SELECTTIME_NOT_BEYOND_TODAY, Toast.LENGTH_LONG);
                                return;
                            } else if (isBeyondOneYear(selectGregorianCalendar, new GregorianCalendar())) {
                                //选择的日期是否超过了一年之前
                                ActivityUtils.showCenterToast(context,
                                        START_NOT_EARLY_ONE_YEAR, Toast.LENGTH_LONG);
                                return;
                            }
                        }
                        ((TextView) view).setText(ActivityUtils.getYYYYMMDDforTimeMillis(selectGregorianCalendar.getTimeInMillis()));
                    }
                }, initCalendar.get(Calendar.YEAR), initCalendar.get(Calendar.MONTH), initCalendar.get(Calendar.DAY_OF_MONTH));


        ds.setTitle(SELECT_TIME);
        ds.show();
    }

    /**
     * 从yyyy-mm-dd的string格式,转换为calendar
     *
     * @return
     */
    public static Calendar getCalendarOfString(String date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");// 设置日期格式
//        String date = df.format(data);
//        System.out.println(date);

        Long timeMillis1 = null;
        try {
            timeMillis1 = df.parse(date).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis1);

        return calendar;
    }

    /**
     * 判断传入的日期是否晚于今天
     *
     * @param selectData
     * @return
     */
    public static boolean isBeyondToday(GregorianCalendar selectData) {

        GregorianCalendar end = new GregorianCalendar();
        end.add(Calendar.DAY_OF_MONTH, -1);
        return isDateOneAfterDateTwo(selectData, end);

    }

    /**
     * 判断两个日期是否超过了一年
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isBeyondOneYear(GregorianCalendar start, GregorianCalendar end) {
        return isBeyondSpecificDays(start, end, 365);
    }

    /**
     * 判断两个日期是否超过指定的天数
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isBeyondSpecificDays(Calendar start, Calendar end, int specificDays) {
        if (start.after(end)) {
            return false;
        }
        long sl = start.getTimeInMillis();
        long el = end.getTimeInMillis();
        long days = ((el - sl) / (1000 * 60 * 60 * 24));
        if (days >= specificDays) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断第一个日期是否超过了第二个日期
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isDateOneAfterDateTwo(Calendar start, Calendar end) {
        end.add(Calendar.DAY_OF_MONTH, 1);
        if (end.after(start)) {
            return false;
        } else {

            return true;
        }
    }


    /**
     * 将String(毫秒数)类型的参数转换成201201021212 格式的时间类型
     *
     * @param data 1970 - 1 -1 0：0 ：0 至指定时间的毫秒数
     * @return
     */
    public static String getRealTimeOfInteger(String data) {
        Date d = new Date(Long.valueOf(data));
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        calendar.setTime(d);

        return getIntegerDate(calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
    }

    /**
     * 判断两个日期是否差距超过31天
     *
     * @param start
     * @param end
     * @return
     */
    public static boolean isDateOneBeyongMonthDateTwo(Calendar start, Calendar end) {
        return isBeyondSpecificDays(start, end, 31);
    }

    /**
     * 获取Integer类型的时间 如: 20120102
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getIntegerDate(int year, int month, int day) {
        StringBuffer sb = new StringBuffer();
        sb.append("").append(year);
        if (month < 10) {
            sb.append("0");
        }
        sb.append(month);
        if (day < 10) {
            sb.append("0");
        }
        sb.append(day);
        return sb.toString();
    }

    /**
     * 获取Integer类型的时间 如: 201201021212
     *
     * @param year
     * @param month
     * @param day
     * @return
     */
    public static String getIntegerDate(int year, int month, int day, int hour,
                                        int min) {
        StringBuffer sb = new StringBuffer();
        sb.append("").append(year);
        if (month < 10) {
            sb.append("0");
        }

        sb.append(month);
        if (day < 10) {
            sb.append("0");
        }
        sb.append(day);
        if (hour < 10) {
            sb.append("0");
        }
        sb.append(hour);
        if (min < 10) {
            sb.append("0");
        }
        sb.append(min);
        return sb.toString();
    }



    public String getJysCode(Context context) {
        return ((MyApplication) context.getApplicationContext())
                .getContentsServerEntity().getYwSystemEntity().getJysList().get(0).getCode()+"";

    }
    public String getShiPanEnvCode(Context context) {
        List<ContentsServerEnvEntity> envList = ((MyApplication) context.getApplicationContext())
                .getContentsServerEntity().getYwSystemEntity().getJysList().get(0).getEnvList();
        for (ContentsServerEnvEntity envEntity : envList) {
            //1实盘, 2模拟盘,3.会员验证实盘
            if (envEntity.getCategory() == 1) {
                //1是实盘

                return envEntity.getCode()+"";

            }
//            if (envEntity.getCategory() == 2) {
//                Constant.TRADE_URL_MONI = envEntity.getYwList().get(0).getLlList().get(0).getUrl();
//                Constant.TRADE_URL_MONI_MONEY = envEntity.getYwList().get(2).getLlList().get(0).getUrl();
//                Constant.TRADE_URL_MONI_REPORT = envEntity.getYwList().get(1).getLlList().get(0).getUrl();
//            }

        }
        return "";
    }
    public String getMoNiPanEnvCode(Context context) {
        List<ContentsServerEnvEntity> envList = ((MyApplication) context.getApplicationContext())
                .getContentsServerEntity().getYwSystemEntity().getJysList().get(0).getEnvList();
        for (ContentsServerEnvEntity envEntity : envList) {
            //1实盘, 2模拟盘,3.会员验证实盘
            if (envEntity.getCategory() == 2) {
                //1是实盘

                return envEntity.getCode()+"";

            }
//            if (envEntity.getCategory() == 2) {
//                Constant.TRADE_URL_MONI = envEntity.getYwList().get(0).getLlList().get(0).getUrl();
//                Constant.TRADE_URL_MONI_MONEY = envEntity.getYwList().get(2).getLlList().get(0).getUrl();
//                Constant.TRADE_URL_MONI_REPORT = envEntity.getYwList().get(1).getLlList().get(0).getUrl();
//            }

        }
        return "";
    }
}
