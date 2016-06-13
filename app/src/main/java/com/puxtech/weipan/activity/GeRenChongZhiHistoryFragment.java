package com.puxtech.weipan.activity;

import android.app.Dialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.puxtech.weipan.Constant;
import com.puxtech.weipan.R;
import com.puxtech.weipan.adapter.InOutHistoryAdapter;
import com.puxtech.weipan.anim.SlideInOutRightItemAnimator;
import com.puxtech.weipan.data.InOutHistoryResponseData;
import com.puxtech.weipan.data.entitydata.InOutHistoryEntity;
import com.puxtech.weipan.helper.OtherHelper;
import com.puxtech.weipan.helper.TradeHelper;
import com.puxtech.weipan.network.HttpManagerMoney;
import com.puxtech.weipan.util.ActivityUtils;

import java.util.ArrayList;
import java.util.Calendar;

public class GeRenChongZhiHistoryFragment extends BaseFragment implements
        View.OnClickListener {
    private static int REQUEST_INIT = 1;
    private static int REQUEST_HAVE_TIME = 2;
    private static int REQUEST_MORE = 3;
    String startTime, endTime;//请求的开始和结束时间
    int startNum;//从第几条开始请求
    TextView tv_start, tv_end, tv_nodata;
    Button bt_commit;
    private RecyclerView recyclerView;
    private Context context;
    private View view;
    ArrayList<InOutHistoryEntity> showList;


    public GeRenChongZhiHistoryFragment(Context context) {
        super();
        this.context = context;
    }

    public GeRenChongZhiHistoryFragment() {
    }

    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        } else {
            try {
                view = inflater.inflate(R.layout.recyclerview, container, false);
                initWidget(view);
                initRequestNeedData();
                initListViewHeader();
                if(new TradeHelper(context).hasOpen()) {
                    request(REQUEST_INIT);
                }
                setClickListener();
            } catch (InflateException ignored) {

            }
        }
        return view;
    }

    /**
     * 初始化一个带HEAD选择时间的LISTVIEW
     */
    private void initListViewHeader() {

        showList = new ArrayList<>();
        adapter = new InOutHistoryAdapter(headView, showList);
        recyclerView.setAdapter(adapter);
    }

    /**
     * 初次进入.默认查询.最新的五条
     */
    private void initRequestNeedData() {
        canLoadingMore = true;
        startNum = 1;
        startTime = "1";
        endTime = System.currentTimeMillis() + "";
    }

    /**
     * 如果是选择日期的查询
     */
    private void initHaveTimeNeedData() {
        canLoadingMore = true;

        showList.clear();
//        adapter = new InOutHistoryAdapter(headView, showList);
//        recyclerView.setAdapter(adapter);
//        tv_nodata.setVisibility(View.VISIBLE);

        startNum = 1;
        startTime = ActivityUtils.getTimeMillisFromYYYYMMDDset000000(tv_start.getText().toString(), false);
        endTime = ActivityUtils.getTimeMillisFromYYYYMMDDset235959(tv_end.getText().toString(), false);

    }

    private void setClickListener() {
        bt_commit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Calendar startCalendar = OtherHelper.getCalendarOfString(tv_start.getText().toString());
                Calendar endCalendar = OtherHelper.getCalendarOfString(tv_end.getText().toString());
                if (OtherHelper.isDateOneAfterDateTwo(startCalendar, endCalendar)) {
                    //开始日期是否晚于结束日期
                    ActivityUtils.showCenterToast(context,
                            OtherHelper.STARTTIME_MORE_ENDTIME, Toast.LENGTH_LONG);
                    return;
                }
                if (OtherHelper.isDateOneBeyongMonthDateTwo(startCalendar, endCalendar)) {
                    ActivityUtils.showCenterToast(context,
                            OtherHelper.CENTER_TIME_SHORT_ONT_MONTHS, Toast.LENGTH_LONG);
                    return;
                }
                initHaveTimeNeedData();
                request(REQUEST_HAVE_TIME);
            }
        });
    }

    /**
     *
     *
     */
    private void request(final int type) {
        putAsyncTask(new AsyncTask<Void, Void, Boolean>() {
            InOutHistoryResponseData responseData;
            Dialog dialog;

            protected void onPreExecute() {
                dialog = ActivityUtils.showLoadingProgressDialog(context);
//                if (type == REQUEST_HAVE_TIME) {
//                    startNum=1;
////                    InOutHistoryAdapter adapter = new InOutHistoryAdapter(context, new ArrayList<InOutHistoryEntity>());
////                    recyclerView.setAdapter(adapter);
//                }

                super.onPreExecute();
            }

            @SuppressWarnings("ResourceType")
            protected Boolean doInBackground(Void... params) {
                try {
                    HttpManagerMoney httpManager = new HttpManagerMoney(context);

                    if (type == REQUEST_INIT) {
                        responseData = httpManager.requestInOutHistory(startTime, endTime, startNum);
                    } else if (type == REQUEST_HAVE_TIME) {
                        responseData = httpManager.requestInOutHistory(startTime, endTime, startNum);
                    } else if (type == REQUEST_MORE) {
                        startNum = startNum + 5;
                        responseData = httpManager.requestInOutHistory(startTime, endTime, startNum);
                    }

                    //获取开户信息

                } catch (Exception e) {
                    e.printStackTrace();
                    responseData.setRetCode(Constant.CODE_ERROR_UNKNOW);
                    responseData.setRetMessage(Constant.MESSAGE_ERROR_UNKNOW);
                }
                return true;
            }

            protected void onPostExecute(Boolean result) {
                super.onPostExecute(result);
                dialog.dismiss();

                if (0 == responseData.getRetCode()) {
                    if (type == REQUEST_INIT) {
                        startNum += 5;

                        showList = responseData.getInOutHistoryEntityArrayList();
                        adapter = new InOutHistoryAdapter(headView, showList);
                        recyclerView.setAdapter(adapter);

                    } else if (type == REQUEST_HAVE_TIME) {
                        showList.addAll(responseData.getInOutHistoryEntityArrayList());
                        adapter.notifyDataSetChanged();
                        //   isLoadingMore=false;

                    } else if (type == REQUEST_MORE) {
                        startNum += 5;
                        showList.addAll(responseData.getInOutHistoryEntityArrayList());

                        adapter.notifyDataSetChanged();
                    }

                    canLoadingMore = responseData.getInOutHistoryEntityArrayList().size() == 5;
///如果没有数据,显示"没有更多的数据"
                    if (showList.size() != 0) {
                        tv_nodata.setVisibility(View.GONE);
                    } else {
                        tv_nodata.setVisibility(View.VISIBLE);

                    }
                } else {

                    if (type == REQUEST_MORE) {
                        //之前加过一次.如果失败,需要不加
                        startNum = startNum - 5;

                    }
                    ActivityUtils.showAlert(context, responseData.getRetMessage() + "(" + responseData.getRetCode() + ")");
                }
            }
        });
    }

    View headView;
    LinearLayoutManager mLayoutManager;
    boolean canLoadingMore;
    InOutHistoryAdapter adapter;

    private void initWidget(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.listView);
        mLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new SlideInOutRightItemAnimator(recyclerView));

        headView = LayoutInflater.from(context).inflate(R.layout.congzhi_history_listview_head, null);

//        recyclerView.addHeaderView(headView);
        tv_start = (TextView) headView.findViewById(R.id.tv_start);
        tv_end = (TextView) headView.findViewById(R.id.tv_end);
        bt_commit = (Button) headView.findViewById(R.id.bt_commit);
        tv_nodata = (TextView) headView.findViewById(R.id.tv_nodata);
        String date = ActivityUtils.getYYYYMMDDforTimeMillis(System.currentTimeMillis());
        tv_start.setText(date);
        tv_end.setText(date);
        tv_start.setOnClickListener(this);
        tv_end.setOnClickListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                int totalItemCount = mLayoutManager.getItemCount();
                //lastVisibleItem >= totalItemCount - 1 表示剩下1个item自动加载，各位自由选择
                // dy>0 表示向下滑动
                if (lastVisibleItem >= totalItemCount - 1 && dy > 0) {
                    if (canLoadingMore) {
                        canLoadingMore = false;

                        request(REQUEST_MORE);//这里多线程也要手动控制isLoadingMore
//                        Log.d(TAG,"ignore manually update!");
                    }
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        OtherHelper.selectData(context, v, true);
    }
}
