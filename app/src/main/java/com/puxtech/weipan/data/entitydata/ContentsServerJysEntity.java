package com.puxtech.weipan.data.entitydata;

import java.util.List;

/**
 * <b>Description:</b>
 * <p>
 * 交易所
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-4-18 下午5:09:31
 * @version V1.0
 */
public class ContentsServerJysEntity {

	/**
	 * 1:有环境<br>
	 * 0:没有环境
	 */
	int type;
	int code;
	String name;
	String shortName;
	List<ContentsServerEnvEntity> envList;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public List<ContentsServerEnvEntity> getEnvList() {
		return envList;
	}

	public void setEnvList(List<ContentsServerEnvEntity> envList) {
		this.envList = envList;
	}

}
