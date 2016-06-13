package com.puxtech.weipan.data.entitydata;

import java.util.ArrayList;
import java.util.List;

/**
 * <b>Description:</b>
 * <p>
 * 市场实体类
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-3-26 下午3:56:46
 * @version V1.0
 */
public class MarketInfoEntity {

	private int id;
	private String name;
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private List<EnvironmentEntity> envList;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<EnvironmentEntity> getEnvList() {
		return envList;
	}

	public void setEnvList(List<EnvironmentEntity> envList) {
		this.envList = envList;
	}

	public void addEnvironment(EnvironmentEntity env) {
		if (envList == null) {
			envList = new ArrayList<EnvironmentEntity>();
		}
		envList.add(env);
	}

	@Override
	public String toString() {
		return "MarketEntity [id=" + id + ", envList=" + envList + "]";
	}

}
