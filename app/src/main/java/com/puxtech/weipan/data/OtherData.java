package com.puxtech.weipan.data;

/**
 * Created by mac on 15/11/5.
 */
public class OtherData {


    private String version="1";
    private String sid;// sessionId


    private byte[] key;// 会话ID
    private byte[] token;// 服务器token
    private Boolean aes;

    private boolean isEncrypt;
    private String RSA="";

    public String getRSA() {
        return RSA;
    }

    public void setRSA(String RSA) {
        this.RSA = RSA;
    }

    public boolean isEncrypt() {
        return isEncrypt;
    }

    public void setIsEncrypt(boolean isEncrypt) {
        this.isEncrypt = isEncrypt;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public byte[] getToken() {
        return token;
    }

    public void setToken(byte[] token) {
        this.token = token;
    }

    public Boolean getAes() {
        return aes;
    }

    public void setAes(Boolean aes) {
        this.aes = aes;
    }
}
