package com.puxtech.weipan.data.entitydata;

public class WeiXinGetTokenEntity {
	private String access_token;//接口调用凭证
	private int expires_in;//access_token接口调用凭证超时时间，单位（秒）
	private String refresh_token;//用户刷新access_token
	private String openid;//授权用户唯一标识
	private String scope;//用户授权的作用域，使用逗号（,）分隔
	private int errcode;//错误码
	private String errmsg;//返回的消息
	
	
	public WeiXinGetTokenEntity() {
		super();
	}
	public WeiXinGetTokenEntity(String access_token, int expires_in,
			String refresh_token, String openid, String scope,int errcode,String errmsg) {
		super();
		this.access_token = access_token;
		this.expires_in = expires_in;
		this.refresh_token = refresh_token;
		this.openid = openid;
		this.scope = scope;
		this.errcode =errcode;
		this.errmsg=errmsg;
	}
	
	
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	
	public int getErrcode() {
		return errcode;
	}
	public void setErrcode(int errcode) {
		this.errcode = errcode;
	}
	public String getAccess_token() {
		return access_token;
	}
	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public int getExpires_in() {
		return expires_in;
	}
	public void setExpires_in(int expires_in) {
		this.expires_in = expires_in;
	}
	public String getRefresh_token() {
		return refresh_token;
	}
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getScope() {
		return scope;
	}
	public void setScope(String scope) {
		this.scope = scope;
	}
	
}
