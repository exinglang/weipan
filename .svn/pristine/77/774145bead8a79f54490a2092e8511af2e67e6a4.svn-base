package com.puxtech.weipan.data;

import com.puxtech.weipan.Constant;

/**
 * <b>Description:</b>
 * <p>
 *
 * </p>
 * 
 * @Package com.happyinsource.htjy.android.entity
 */
public class AccountInfoData extends BaseResponseData {

	String FN;// 账户名称
	String IF;// 期初余额

	String IB;// 期初权益
	String dangqianquanyi;// 当前权益


	String CM;// 上日保证金
	String UF;// 当前可用保证金(可用资金?)
	String IOF;// 当日出入金
	String PL ;// 当日盈亏合计
	String FEE;// 当日手续费合计,当日手续费
	String RM ;//当日保证金(占用保证金)
	String CD ;// 上日延期费
	String OR_F_M;// 远期委托冻结保证金（冻结保证金）(冻结定金?)
	String OR_F_F;// 冻结手续费//远期委托冻结手续费（冻结手续费）
	String C_STA;
	String COMM ;
	String UN_LIVE;
	String NORMAL ;
	String COLD;
	String FINAL;
	String flp;//当日浮动盈亏
	String HAS_BORN;
	String OR_F;//远期委托冻结资金(冻结资金?)
	String fenxianlv;//风险率
	String keyongbaozhenjin;//可用保证金

	public String getKeyongbaozhenjin() {
		return keyongbaozhenjin;
	}

	public void setKeyongbaozhenjin(String keyongbaozhenjin) {
		this.keyongbaozhenjin = keyongbaozhenjin;
	}

    public String getFlp() {
        return flp;
    }

    public void setFlp(String flp) {
        this.flp = flp;
    }

    public String getFenxianlv() {
		return fenxianlv;
	}

	public void setFenxianlv(String fenxianlv) {
		this.fenxianlv = fenxianlv;
	}

    public String getDangqianquanyi() {
        return dangqianquanyi;
    }

    public void setDangqianquanyi(String dangqianquanyi) {
        this.dangqianquanyi = dangqianquanyi;
    }

    public String getOR_F() {
		return OR_F;
	}

	public void setOR_F(String OR_F) {
		this.OR_F = OR_F;
	}

	public String getFN() {
		return FN;
	}

	public void setFN(String FN) {
		this.FN = FN;
	}

	public String getIF() {
		return IF;
	}

	public void setIF(String IF) {
		this.IF = IF;
	}

	public String getCM() {
		return CM;
	}

	public void setCM(String CM) {
		this.CM = CM;
	}

	public String getUF() {
		return UF;
	}

	public void setUF(String UF) {
		this.UF = UF;
	}

	public String getIOF() {
		return IOF;
	}

	public void setIOF(String IOF) {
		this.IOF = IOF;
	}

	public String getIB() {
		return IB;
	}

	public void setIB(String IB) {
		this.IB = IB;
	}

	public String getPL() {
		return PL;
	}

	public void setPL(String PL) {
		this.PL = PL;
	}

	public String getFEE() {
		return FEE;
	}

	public void setFEE(String FEE) {
		this.FEE = FEE;
	}

	public String getRM() {
		return RM;
	}

	public void setRM(String RM) {
		this.RM = RM;
	}

	public String getCD() {
		return CD;
	}

	public void setCD(String CD) {
		this.CD = CD;
	}

	public String getOR_F_M() {
		return OR_F_M;
	}

	public void setOR_F_M(String OR_F_M) {
		this.OR_F_M = OR_F_M;
	}

	public String getOR_F_F() {
		return OR_F_F;
	}

	public void setOR_F_F(String OR_F_F) {
		this.OR_F_F = OR_F_F;
	}

	public String getC_STA() {

	String state=null;
		if ("U".equals(C_STA)) {
			state = Constant.UN_LIVE;// 未激活
		} else if ("N".equals(C_STA)) {
			state = Constant.NORMAL;// 正常
		} else if ("F".equals(C_STA)) {
			state = Constant.COLD;// 冻结
		} else if ("D".equals(C_STA)) {
			state = Constant.FINAL;// 终止
		} else if ("C".equals(C_STA)) {
			state = Constant.HAS_BORN;// 已创建
		}else{

			state="错误";
		}
		return state;
	}

	public void setC_STA(String c_STA) {
		C_STA = c_STA;
	}

	public String getCOMM() {
		return COMM;
	}

	public void setCOMM(String COMM) {
		this.COMM = COMM;
	}

	public String getUN_LIVE() {
		return UN_LIVE;
	}

	public void setUN_LIVE(String UN_LIVE) {
		this.UN_LIVE = UN_LIVE;
	}

	public String getNORMAL() {
		return NORMAL;
	}

	public void setNORMAL(String NORMAL) {
		this.NORMAL = NORMAL;
	}

	public String getCOLD() {
		return COLD;
	}

	public void setCOLD(String COLD) {
		this.COLD = COLD;
	}

	public String getFINAL() {
		return FINAL;
	}

	public void setFINAL(String FINAL) {
		this.FINAL = FINAL;
	}

	public String getHAS_BORN() {
		return HAS_BORN;
	}

	public void setHAS_BORN(String HAS_BORN) {
		this.HAS_BORN = HAS_BORN;
	}
}
