package com.puxtech.weipan.data.entitydata;

import java.util.List;

/**
 * <b>Description:</b>
 * <p>
 * 目录服务器-业务系统
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-4-18 下午4:35:04
 * @version V1.0
 */
public class ContentsServerYwsysEntity {

	int code;
	String systemName;
	String systemShortName;

	/**
	 * services
	 */
	List<ContentsServerServiceEntity> serviceList;

	/**
	 * 交易所列表
	 */
	List<ContentsServerJysEntity> jysList;

	/**
	 * 默认商品列表
	 */
	List<ContentsServerCmmdEntity> cmmdsList;

	/**
	 * 版本信息
	 */
	VersionEntity version;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public String getSystemShortName() {
		return systemShortName;
	}

	public void setSystemShortName(String systemShortName) {
		this.systemShortName = systemShortName;
	}

	public List<ContentsServerJysEntity> getJysList() {
		return jysList;
	}

	public void setJysList(List<ContentsServerJysEntity> jysList) {
		this.jysList = jysList;
	}

	public List<ContentsServerCmmdEntity> getCmmdsList() {
		return cmmdsList;
	}

	public void setCmmdsList(List<ContentsServerCmmdEntity> cmmdsList) {
		this.cmmdsList = cmmdsList;
	}

	public List<ContentsServerServiceEntity> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ContentsServerServiceEntity> serviceList) {
		this.serviceList = serviceList;
	}

	public VersionEntity getVersion() {
		return version;
	}

	public void setVersion(VersionEntity version) {
		this.version = version;
	}

	// --------------------------------------------辅助方法

	/**
	 * 根据交易所编码和环境编码取得环境类型
	 */
	public int getEnvType(int platformNumber, int envNumber) {
		for (ContentsServerJysEntity jysEntity : jysList) {
			if (jysEntity.getCode() == platformNumber) {
				List<ContentsServerEnvEntity> envList = jysEntity.getEnvList();
				// 找到这个环境
				for (ContentsServerEnvEntity envEntity : envList) {
					if (envEntity.getCode() == envNumber) {
						return envEntity.getType();
					}
				}
			}
		}
		return -1;
	}
	
	/**
	 * 根据交易所编码和环境编码取得环境名称
	 */
	public String getEnvName(int platformNumber, int envNumber) {
		for (ContentsServerJysEntity jysEntity : jysList) {
			if (jysEntity.getCode() == platformNumber) {
				List<ContentsServerEnvEntity> envList = jysEntity.getEnvList();
				// 找到这个环境
				for (ContentsServerEnvEntity envEntity : envList) {
					if (envEntity.getCode() == envNumber) {
						return envEntity.getName();
					}
				}
			}
		}
		return "unknown";
	}

    /**
     * 获取行情service
     * @return
     */
    public ContentsServerServiceEntity getPriceServerEntity(){
        for (ContentsServerServiceEntity serviceEntity : serviceList) {
            // 行情服务前置机地址
            if (serviceEntity.getType() == 1) {
                return serviceEntity;
            }
        }
        return null;
    }

}
