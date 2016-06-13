package com.puxtech.weipan.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.puxtech.weipan.Constant;
import com.puxtech.weipan.MyApplication;
import com.puxtech.weipan.R;
import com.puxtech.weipan.data.AccountInfoData;
import com.puxtech.weipan.data.CommodityData;
import com.puxtech.weipan.data.HoldDetailData;
import com.puxtech.weipan.data.TradeDealData;
import com.puxtech.weipan.data.TradeReportDealData;
import com.puxtech.weipan.data.TradeTrustData;
import com.puxtech.weipan.data.entitydata.OpenAccountInfoEntity;
import com.puxtech.weipan.data.entitydata.PriceCommodityEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.CustomException;
import com.puxtech.weipan.util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class TradeHelper {

    private Context context;
    private MyApplication myapp;

    public TradeHelper(Context context) {
        this.context = context;
        this.myapp = (MyApplication) context.getApplicationContext();

    }

    /**
     * 得到商品名称列表
     *
     * @return 商品名称List
     */
    public List getPriceCommodityNameList() {
        ArrayList<String> list = new ArrayList<>();
        for (PriceCommodityEntity priceCommodityEntity : myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllCommodityList()) {
            list.add(priceCommodityEntity.getName());
        }

        return list;
    }

    /**
     * 得到商品名称列表
     *
     * @return 商品名称List
     */
    public List getCommodityNameList() {

        ArrayList<String> list = new ArrayList<>();
        for (CommodityData cd : myapp.getCurrentTradeEntity().getTradeData().getCommodityDataList()) {

            list.add(cd.getName());
        }

        return list;
    }

    /**
     * 根据传进来的商品代码得到商品名称
     *
     * @param code 商品代码
     * @return 商品名称
     */
    public String getCommodityNameByCode(String code) {
        for (CommodityData cd : myapp.getCurrentTradeEntity().getTradeData().getCommodityDataList()) {
            if (cd.getCode().equals(code)) {
                return cd.getName();
            }
        }
        return "";
    }

    /**
     * 根据传进来的商品代码得到交易商品实体类s
     *
     * @param code 商品代码
     * @return 商品名称
     */
    public CommodityData getCommodityDataByCode(String code) {
        for (CommodityData cd : myapp.getCurrentTradeEntity().getTradeData().getCommodityDataList()) {
            if (cd.getCode().equals(code)) {
                return cd;
            }
        }
        return null;
    }

    /**
     * 根据传进来的商品代码得到交易商品实体类s
     *
     * @param code 商品代码
     * @return 商品名称
     */
    public PriceCommodityEntity getPriceCommodityEntityDataByCode(String code) {
        for (PriceCommodityEntity cd : myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllCommodityList()) {
            if (cd.getCode().equals(code)) {
                return cd;
            }
        }
        return null;
    }


    /**
     * 得到商品的最小变动价
     */
    public String getCommodityZuiXiaoBianDongJia(String code) {

        String point, spread = null;
        try {
            //  ArrayList stuff =
            // getCode = priceEntity.getCode();

            spread = getCommodityDataByCode(code).getSPREAD();
        } catch (Exception e) {
            MyApplication myapp = (MyApplication) context.getApplicationContext();
            if (myapp.getShipanTradeEntity().isHasLogin()) {
               // e.printStackTrace();
            }

        }

        if (spread == null) {
            point = "0.01";
        } else {
            point = spread;
            if (point.equals("0.1")) {
                point = "0.01";
            }
        }

        return point;
    }

    /**
     * 行情配置的CODE可能和最新一口价配置的商品不一样.在此处比较.如没有匹配到.返回一个无数据的priceEntity
     * 查找allPriceList有没有此商品
     */
    public PriceEntity getNewPrice(PriceEntity priceEntity) {
        PriceEntity mPriceEntity = new PriceEntity(
                priceEntity.getName(),
                priceEntity.getNumber());
        List<PriceEntity> allPriceList = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getAllPriceList();
        if (allPriceList != null) {
            for (int i = 0; i < allPriceList.size(); i++) {
                if (allPriceList.get(i).getNumber() == priceEntity
                        .getNumber()) {
                    mPriceEntity = allPriceList.get(i);
                    break;
                }
            }
        }
        return mPriceEntity;
    }

    // 左(高)区间的价格
    public String getHighPrice(CommodityData commodityData, PriceEntity priceEntity) {
//        HashMap<String, HashMap<String, String>> s = myapp.getNyTrade()
//                .getJyData().getNewProducesMap();
//
//        HashMap<String, String> map = s.get(stuffCode);
        Double a;
        try {
            // String sellp = map.get("sell");
            a = priceEntity.getSale() - Double.parseDouble(commodityData.getX_O_S_D_D())
                    * Double.parseDouble(commodityData.getSPREAD());
        } catch (Exception e) {

            return "0";
        }
        return ActivityUtils.changeDouble(String.valueOf(a), commodityData.getSPREAD());
    }

    // 右(低)区间的价格
    public String getLowPrice(CommodityData commodityData, PriceEntity priceEntity) {
//
//        HashMap<String, String> map = myapp.getNyTrade().getJyData()
//                .getNewProducesMap().get(stuffCode);
        Double a;
        // String buyp = null;
        try {
            //    buyp = map.get("buy");
            a = priceEntity.getBuy() + Double.parseDouble(commodityData.getX_O_S_D_D())
                    * Double.parseDouble(commodityData.getSPREAD());

        } catch (Exception e) {

            return "0";
        }

        return ActivityUtils.changeDouble(String.valueOf(a), commodityData.getSPREAD());
    }

    /**
     * 重新登录
     */

    public void reLogon() {
    }

    /**
     * 保存所需的sharedPerference文件数据
     */
    public void saveSpf(SharedPreferenceManager spf, long broadId, long dealCount, long lastId, String td) {
        spf.putLong(context, SharedPreferenceManager.BROAD_ID, broadId);
        spf.putLong(context, SharedPreferenceManager.DEAL_COUNT, dealCount);
        spf.putLong(context, SharedPreferenceManager.LAST_ID, lastId);
        spf.putString(context, SharedPreferenceManager.TD, td);
    }

    /**
     * 取0-position 的今日成交数据
     */
    public List<TradeDealData> getTodayDealDataListFilter(int endPosition) {
        ArrayList<TradeDealData> holdDetailDataArrayList = myapp.getCurrentTradeEntity().getTradeData().getDealDataList();
        //为""显示所有
        //最多返回五个

        try {
            return holdDetailDataArrayList.subList(0, endPosition);
        } catch (Exception e) {
            e.printStackTrace();
            return holdDetailDataArrayList;
        }

    }

    /**
     * 取0-position 的持仓明细数据
     */
    public List<TradeReportDealData> getHistoryDealDataListFilter(int endPosition) {
        ArrayList<TradeReportDealData> holdDetailDataArrayList = myapp.getCurrentTradeEntity().getTradeData().getReportDealDataList();
        //为""显示所有
        //最多返回五个

        try {
            return holdDetailDataArrayList.subList(0, endPosition);
        } catch (Exception e) {
            e.printStackTrace();
            return holdDetailDataArrayList;
        }

    }

    /**
     * 取0-position 的持仓明细数据
     *
     * @param code 可能需要过滤的商品名
     */
    public List<HoldDetailData> getHoldDetailDataListFilter(String code, int position) {
        MyApplication myapp = (MyApplication) context.getApplicationContext();
        ArrayList<HoldDetailData> holdDetailDataArrayList = myapp.getCurrentTradeEntity().getTradeData().getHoldDetailDataList();
        if (code.equals("")) {
            //为""显示所有
            //最多返回五个
            try {
                return holdDetailDataArrayList.subList(0, position);
            }
            catch (IndexOutOfBoundsException e) {
                e.printStackTrace();
                return holdDetailDataArrayList;
            }
            catch (Exception e) {
                e.printStackTrace();
                return holdDetailDataArrayList;
            }

        }
        ArrayList<HoldDetailData> newArrayList = new ArrayList<>();
        for (HoldDetailData holdDetailData : holdDetailDataArrayList) {
            if (holdDetailData.getStuffId().equals(code)) {
                newArrayList.add(holdDetailData);
            }
        }
        try {
            return newArrayList.subList(0, position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return newArrayList;
        }
    }

    public List<TradeTrustData> getTrustDataListFilter(String code, int startPosition) {
        MyApplication myapp = (MyApplication) context.getApplicationContext();
        ArrayList<TradeTrustData> holdDetailDataArrayList = myapp.getCurrentTradeEntity().getTradeData().getTrustDataList();
        if (code.equals("")) {
            //为""显示所有
            //最多返回五个
            try {
                return holdDetailDataArrayList.subList(0, startPosition);
            } catch (IndexOutOfBoundsException e) {

                return holdDetailDataArrayList;


            }
        }
        List<TradeTrustData> newArrayList = new ArrayList<>();
        for (TradeTrustData holdDetailData : holdDetailDataArrayList) {
            if (holdDetailData.getStuffId().equals(code)) {
                newArrayList.add(holdDetailData);
            }
        }
        try {
            return newArrayList.subList(0, startPosition);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return newArrayList;

        }
        //return newArrayList;
    }

    /**
     * 根据商品代码获取商品的交易类型
     *
     * @param stuffId 商品编号
     * @return 如果没有返回空
     */

    public int getCommodityModeByCode(String stuffId) {
        String mode = getCommodityDataByCode(stuffId).getTRADEMODE();
        return Integer.valueOf(mode);
    }

    /**
     * 设置持仓明细的浮动盈亏
     */
    public HoldDetailData setHoldDetailFlp(HoldDetailData holdDetailData) {
        if (!myapp.getCurrentTradeEntity().isTradeState()) {
            holdDetailData.setFlp("--");
        } else {
            try {
                PriceEntity priceEntity = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getPriceEntityByCode(holdDetailData.getStuffId());
                String direct = null;
                String newPrice = null;
                switch (new TradeHelper(context).getCommodityModeByCode(holdDetailData.getStuffId())) {
                    case 2:
                    case 5:
                        if (holdDetailData.getBuyOrSell().equals(Constant.BUY)) {
                            direct = "1";
                            newPrice = priceEntity.getSale() + "";
                        } else if (holdDetailData.getBuyOrSell().equals(Constant.SELL)) {
                            direct = "-1";
                            newPrice = priceEntity.getBuy() + "";
                        }
                        break;
                    case 4:
                        //4模式商品用最新价计算
                        direct = "1";
                        newPrice = priceEntity.getPrice() + "";
                        break;
                    default:
                        break;
                }
                if (Double.valueOf(newPrice) != 0) {
                    holdDetailData.setFlp(ActivityUtils.changeDouble(getFlp(
                            newPrice,
                            holdDetailData.getHoldPrice(),
                            holdDetailData.getHoldNumber(),
                            getCommodityDataByCode(holdDetailData.getStuffId()).getCT_S(), direct)));
                } else {
                    holdDetailData.setFlp("---");
                }

            } catch (Exception e) {
                e.printStackTrace();
                holdDetailData.setFlp("--");
            }


        }
        return holdDetailData;
    }

    public String getCommodityNameFromCode(String code) {

        for (CommodityData cd : myapp.getCurrentTradeEntity().getTradeData().getCommodityDataList()) {
            if (cd.getCode().equals(code)) {

                return cd.getName();
            }
        }
        new CustomException("getCommodityNameFromCode:未从code找到对应的商品名").printStackTrace();
        return "";

    }

    /**
     * 计算浮动盈亏 单持仓浮动盈亏计算 =（最新价-持仓价）*持仓量*合约单位*方向 方向：买单：1；卖单：-1
     *
     */
    public String getFlp(String newPrice, String holdprice, String amount,
                         String unit, String direct) {
        //Log.i("doitp","最新价:"+newPrice+","+"持仓价:"+holdprice+","+"持仓量"+amount+","+"合约单位:"+unit+","+"方向:"+direct);

        double a = Double.parseDouble(newPrice);
        double b = Double.parseDouble(holdprice);
        double c = Double.parseDouble(amount);
        double d = Double.parseDouble(unit);
        double e = Double.parseDouble(direct);
        return String.valueOf((a - b) * c * d * e);
    }

    /**
     * 计算浮动盈亏,风险率,当前权益,可用保证金,并保存进Myapp中的AccountInfoData;
     */
    public AccountInfoData calSaveAccountInfo() {
        AccountInfoData infoData = myapp.getShipanTradeEntity().getTradeData().getAccountInfoData();
        double keyongbaozhenjin;
        double dangqianquanyi, fenxianlv;
        double allflp = 0;
        boolean heartState = myapp.getShipanTradeEntity().isTradeState();
        ArrayList<HoldDetailData> total = myapp.getShipanTradeEntity().getTradeData().getHoldDetailDataList();
        if (!heartState) {
            // 市场未处于交易状态
            setNoData(infoData);

        } else {
            try {
                if (total != null) {

                    for (HoldDetailData detailData : myapp.getCurrentTradeEntity().getTradeData().getHoldDetailDataList()) {
                        detailData = setHoldDetailFlp(detailData);
                        if (detailData.getFlp().equals("--")) {
                            continue;
                        }
                        allflp = allflp + Double.valueOf(detailData.getFlp());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                setNoData(infoData);
            }
        }

        // if (type != -1) {
        // IOF 当日出入金,PL 当日平仓盈亏合计,FEE 当日手续费合计,12 期初权益,当日浮动盈亏
        // 计算当前权益=期初权益 +当日 出入金+当日盈亏合计-当日手续费合计
        dangqianquanyi = Double.valueOf(infoData.getIOF())
                - Double.valueOf(infoData.getFEE()) + Double.valueOf(infoData.getPL())
                + Double.valueOf(infoData.getIB()) + allflp;
        // 可用保证金=当前权益- 占用保证金- 远期委托冻结资金
        keyongbaozhenjin = dangqianquanyi
                - Double.valueOf(infoData.getRM())
                - Double.valueOf(infoData.getOR_F());

        // 风险率=当前权益 /占用保证金
        double zhanyongbaozhenjin = Double.valueOf(infoData.getRM());
        if (zhanyongbaozhenjin == 0) {
            fenxianlv = 0;
        } else {
            fenxianlv = (Double.valueOf(dangqianquanyi))
                    / zhanyongbaozhenjin;
        }
        infoData.setDangqianquanyi(ActivityUtils.changeDouble(String.valueOf(dangqianquanyi)));// 当前权益

        infoData.setKeyongbaozhenjin(ActivityUtils.changeDouble(String.valueOf(keyongbaozhenjin)));// 可用保证金
        infoData.setFlp(ActivityUtils.changeDouble(String.valueOf(allflp)));// 浮动盈亏

        infoData.setFenxianlv(ActivityUtils.changeDouble(String.valueOf(Double
                .valueOf(fenxianlv * 100))) + "%");// 风险率
//        return update;
        return infoData;
    }

    private void setNoData(AccountInfoData infoData) {
        infoData.setFenxianlv("--");
        infoData.setDangqianquanyi("--");
        infoData.setFlp("--");
        infoData.setKeyongbaozhenjin("--");
    }


    /**
     * 是否已开户
     */
    public  boolean hasOpen() {
//           return myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getCustomer_no().equals("") ? false : true;
        return myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity()==null ? false : true;

        // return  false;
    }

    /**
     * 是否已开户
     */
    public  boolean hasMoNiContract() {
//           return myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getCustomer_no().equals("") ? false : true;
        return myapp.getMoniTradeEntity().getUserId().equals("") ? false : true;

        // return  false;
    }
    /**
     * 是否已签约
     */
    public   boolean hasContract() {
//        return myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity().getSignBankList().size() != 0 ? true : false;
        OpenAccountInfoEntity infoEntity=     myapp.getOpenAccountContractEntity().getOpenAccountInfoEntity();
                    return infoEntity==null ? false : infoEntity.getSignBankList().get(0).getAccount().equals("") ? false : true;



        }



}
