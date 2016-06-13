package com.puxtech.weipan.data.entitydata;

import java.util.List;

/**
 * <b>Description:</b>
 * <p>
 * TODO
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-4-30 下午2:48:53
 * @version V1.0
 */
public class ContentsServerServiceEntity {

	int type;
	String name;
	List<ContentsServerZuEntity> zuList;

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

	public List<ContentsServerZuEntity> getZuList() {
		return zuList;
	}

	public void setZuList(List<ContentsServerZuEntity> zuList) {
		this.zuList = zuList;
	}

}
