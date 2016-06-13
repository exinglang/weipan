package com.puxtech.weipan.data.entitydata;

public class PriceSecretEntity {
	
	private String key;//密钥标识
	private byte[] secret;//密钥
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public  byte[] getSecret() {
		return secret;
	}
	public void setSecret( byte[] secret) {
		this.secret = secret;
	}
	
}
