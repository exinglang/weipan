package com.puxtech.weipan.data.entitydata;

import java.util.List;

/**
 * <b>Description:</b>
 * <p>
 * 交易所-交易环境-业务
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-4-18 下午6:06:07
 * @version V1.0
 */
public class ContentsServerYwEntity {
	/**
	 * 1:交易<br>
	 * 2:报表和公告<br>
	 * 3:出入金<br>
	 */
	int type;
	String name;
	List<ContentsServerLlEntity> llList;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ContentsServerLlEntity> getLlList() {
		return llList;
	}

	public void setLlList(List<ContentsServerLlEntity> llList) {
		this.llList = llList;
	}

}
