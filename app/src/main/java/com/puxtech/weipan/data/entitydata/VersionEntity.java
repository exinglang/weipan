package com.puxtech.weipan.data.entitydata;

/**
 * <b>Description:</b>
 * <p>
 * TODO
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-5-6 上午10:31:52
 * @version V1.0
 */
public class VersionEntity {

	/**
	 * 版本号
	 */
	int versionCode;
	/**
	 * 版本名称
	 */
	String versionName;
	/**
	 * 是否需要更新，0：不需要，1：需要
	 */
	int needUpdate;
	/**
	 * 下载地址
	 */
	String updateUrl;

	/**
	 * 更新内容
	 */
	String updateInfo;

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getNeedUpdate() {
		return needUpdate;
	}

	public void setNeedUpdate(int needUpdate) {
		this.needUpdate = needUpdate;
	}

	public String getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}

	public String getUpdateInfo() {
		return updateInfo;
	}

	public void setUpdateInfo(String updateInfo) {
		this.updateInfo = updateInfo;
	}

}
