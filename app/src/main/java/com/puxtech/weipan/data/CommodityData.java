package com.puxtech.weipan.data;

/**
 * Created by mac on 15/11/5.
 */
public class CommodityData {
    String code;//商品代码
    String name;//商品名称
    String W_D_T_P; // 撤销限建权限
    String W_D_S_L_P ;// 撤销止损权限
    String W_D_S_P_P; // 撤销止盈权限
    String B_X_O_P; // 买入限建权限
    String S_X_O_P; // 卖出限建权限
    String B_O_P; // 买入市建权限
    String B_L_P ; // 买入市平权限
    String S_O_P ; // 卖出市建权限
    String S_L_P ; // 卖出市平权限
    String B_S_L ; // 买入止损权限
    String B_S_P ; // 买入止盈权限
    String S_S_L ; // 卖出止损权限
    String S_S_P ; // 卖出止盈权限
    String C_L_B_O; // 撮合买入限建
    String C_L_B_C; // 撮合买入限平
    String C_L_C_O; //撮合卖出限建
    String C_L_C_C; //撮合卖出限平
    String CT_S; // 交易单位
    String M_H ; // 最大持仓量
    String STOP_L_P; // 止损下单点差
    String STOP_P_P; // 止盈下单点差
    String U_O_D_D_DF ;// 用户报价点差默认值
    String P_MIN_H ; // 单笔最小可委托数
    String P_M_H; // 单笔最大可委托数量
    String SPREAD ; // 最小变动价位
    String X_O_B_D_D; // 限价建仓买点差
    String B_P_D_D ; // 买点差
    String S_P_D_D ; // 止盈下单点差
    String X_O_S_D_D ; // 限价建仓卖点差
    String U_O_D_D_MIN ;// 用户报价点差最小值
    String U_O_D_D_MAX ;// 用户报价点差最大值
    String MA_V ; // 保证金系数
    String CON_U ;
    String TRADEMODE ;
    String MIN_HQ_MOVE;

        //首页需要显示的三个值
    String buy;
    String cha;
    String baifenbi;

    public String getCT_S() {
        return CT_S;
    }

    public void setCT_S(String CT_S) {
        this.CT_S = CT_S;
    }

    public String getM_H() {
        return M_H;
    }

    public void setM_H(String m_H) {
        M_H = m_H;
    }

    public String getX_O_B_D_D() {
        return X_O_B_D_D;
    }

    public void setX_O_B_D_D(String x_O_B_D_D) {
        X_O_B_D_D = x_O_B_D_D;
    }

    public String getB_P_D_D() {
        return B_P_D_D;
    }

    public void setB_P_D_D(String b_P_D_D) {
        B_P_D_D = b_P_D_D;
    }

    public String getS_P_D_D() {
        return S_P_D_D;
    }

    public void setS_P_D_D(String s_P_D_D) {
        S_P_D_D = s_P_D_D;
    }

    public String getX_O_S_D_D() {
        return X_O_S_D_D;
    }

    public void setX_O_S_D_D(String x_O_S_D_D) {
        X_O_S_D_D = x_O_S_D_D;
    }

    public String getSTOP_L_P() {
        return STOP_L_P;
    }

    public void setSTOP_L_P(String STOP_L_P) {
        this.STOP_L_P = STOP_L_P;
    }

    public String getSTOP_P_P() {
        return STOP_P_P;
    }

    public void setSTOP_P_P(String STOP_P_P) {
        this.STOP_P_P = STOP_P_P;
    }

    public String getU_O_D_D_MIN() {
        return U_O_D_D_MIN;
    }

    public void setU_O_D_D_MIN(String u_O_D_D_MIN) {
        U_O_D_D_MIN = u_O_D_D_MIN;
    }

    public String getU_O_D_D_MAX() {
        return U_O_D_D_MAX;
    }

    public void setU_O_D_D_MAX(String u_O_D_D_MAX) {
        U_O_D_D_MAX = u_O_D_D_MAX;
    }

    public String getMA_V() {
        return MA_V;
    }

    public void setMA_V(String MA_V) {
        this.MA_V = MA_V;
    }

    public String getCON_U() {
        return CON_U;
    }

    public void setCON_U(String CON_U) {
        this.CON_U = CON_U;
    }

    public String getTRADEMODE() {
        return TRADEMODE;
    }

    public void setTRADEMODE(String TRADEMODE) {
        this.TRADEMODE = TRADEMODE;
    }

    public String getMIN_HQ_MOVE() {
        return MIN_HQ_MOVE;
    }

    public void setMIN_HQ_MOVE(String MIN_HQ_MOVE) {
        this.MIN_HQ_MOVE = MIN_HQ_MOVE;
    }

    public String getU_O_D_D_DF() {
        return U_O_D_D_DF;
    }

    public void setU_O_D_D_DF(String u_O_D_D_DF) {
        U_O_D_D_DF = u_O_D_D_DF;
    }

    public String getP_MIN_H() {
        return P_MIN_H;
    }

    public void setP_MIN_H(String p_MIN_H) {
        P_MIN_H = p_MIN_H;
    }

    public String getP_M_H() {
        return P_M_H;
    }

    public void setP_M_H(String p_M_H) {
        P_M_H = p_M_H;
    }

    public String getSPREAD() {
        return SPREAD;
    }

    public void setSPREAD(String SPREAD) {
        this.SPREAD = SPREAD;
    }

    public String getW_D_S_L_P() {
        return W_D_S_L_P;
    }

    public void setW_D_S_L_P(String w_D_S_L_P) {
        W_D_S_L_P = w_D_S_L_P;
    }

    public String getW_D_S_P_P() {
        return W_D_S_P_P;
    }

    public void setW_D_S_P_P(String w_D_S_P_P) {
        W_D_S_P_P = w_D_S_P_P;
    }

    public String getB_X_O_P() {
        return B_X_O_P;
    }

    public void setB_X_O_P(String b_X_O_P) {
        B_X_O_P = b_X_O_P;
    }

    public String getS_X_O_P() {
        return S_X_O_P;
    }

    public void setS_X_O_P(String s_X_O_P) {
        S_X_O_P = s_X_O_P;
    }

    public String getB_O_P() {
        return B_O_P;
    }

    public void setB_O_P(String b_O_P) {
        B_O_P = b_O_P;
    }

    public String getB_L_P() {
        return B_L_P;
    }

    public void setB_L_P(String b_L_P) {
        B_L_P = b_L_P;
    }

    public String getS_O_P() {
        return S_O_P;
    }

    public void setS_O_P(String s_O_P) {
        S_O_P = s_O_P;
    }

    public String getS_L_P() {
        return S_L_P;
    }

    public void setS_L_P(String s_L_P) {
        S_L_P = s_L_P;
    }

    public String getB_S_L() {
        return B_S_L;
    }

    public void setB_S_L(String b_S_L) {
        B_S_L = b_S_L;
    }

    public String getB_S_P() {
        return B_S_P;
    }

    public void setB_S_P(String b_S_P) {
        B_S_P = b_S_P;
    }

    public String getS_S_L() {
        return S_S_L;
    }

    public void setS_S_L(String s_S_L) {
        S_S_L = s_S_L;
    }

    public String getS_S_P() {
        return S_S_P;
    }

    public void setS_S_P(String s_S_P) {
        S_S_P = s_S_P;
    }

    public String getC_L_B_O() {
        return C_L_B_O;
    }

    public void setC_L_B_O(String c_L_B_O) {
        C_L_B_O = c_L_B_O;
    }

    public String getC_L_B_C() {
        return C_L_B_C;
    }

    public void setC_L_B_C(String c_L_B_C) {
        C_L_B_C = c_L_B_C;
    }

    public String getC_L_C_O() {
        return C_L_C_O;
    }

    public void setC_L_C_O(String c_L_C_O) {
        C_L_C_O = c_L_C_O;
    }

    public String getC_L_C_C() {
        return C_L_C_C;
    }

    public void setC_L_C_C(String c_L_C_C) {
        C_L_C_C = c_L_C_C;
    }

    public String getW_D_T_P() {
        return W_D_T_P;
    }

    public void setW_D_T_P(String w_D_T_P) {
        W_D_T_P = w_D_T_P;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getCha() {
        return cha;
    }

    public void setCha(String cha) {
        this.cha = cha;
    }

    public String getBaifenbi() {
        return baifenbi;
    }

    public void setBaifenbi(String baifenbi) {
        this.baifenbi = baifenbi;
    }
}
