package com.puxtech.weipan.network;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import android.util.Xml;


import com.puxtech.weipan.Constant;
import com.puxtech.weipan.helper.OtherHelper;
import com.puxtech.weipan.util.AES;
import com.puxtech.weipan.util.ActivityUtils;
import com.puxtech.weipan.util.Base64;
import com.puxtech.weipan.util.MD5;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xmlpull.v1.XmlSerializer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * <b>Description:</b>
 * <p>
 * tokenB管理类
 * </p>
 * 
 */
public class TokenBManager {
	public static String ENCODE = "UTF-8";
    // 请求内容的TAG
    public static final String TAG_LYSS = "LYSS";// 消息头
    public static final String TAG_REQ = "REQ";// 请求内容
    public static final String TAG_FUNC = "FUNC";
    public static final String TAG_VERSIONCODE = "VERSIONCODE";
    public static final String TAG_TKNA = "TKNA";
    public static final String TAG_APPK = "APPK";
    public static final String TAG_DEVID = "DEVID";
    public static final String TAG_SALT = "SALT";
    public static final String TAG_DATE = "DATE";
    public static final String TAG_SIGN = "SIGN";
	Context context;

	public TokenBManager(Context context) {
		super();
		this.context = context;
	}



	/**
	 * 生成请求目录服务器的xml字符串
	 */
	public String createCatalogRequestData(Context context) throws Exception {
		TokenBManager tokenBManager = new TokenBManager(context);
		String s = tokenBManager.getTokenB();
		if (TextUtils.isEmpty(s)) {
			// 没有tokenB
			return createGetInfoRequestDataWhithTokenA(context);
		} else {
			// 有tokenB
			return createGetInfoRequestDataWhithTokenB(context);
		}

	}

	/**
	 * 插入TokenB
	 */
	private void insertTokenB(XmlSerializer xmlSerializer,
							  StringWriter xmlWriter) throws Exception {
		// 插入tokenB
		xmlSerializer.flush();
		TokenBManager tokenBManager = new TokenBManager(context);
		xmlWriter.append(tokenBManager.getTokenB());
	}
	/**
	 * 使用tokenA生成目录服务器请求数据
	 */
	private String createGetInfoRequestDataWhithTokenA(Context context)
			throws Exception {
		byte[] salt = getSaltA();
		String devid = getDEVID(context);

		StringWriter xmlWriter = new StringWriter();
		XmlSerializer xmlSerializer = Xml.newSerializer();
		xmlSerializer.setOutput(xmlWriter);
		xmlSerializer.startDocument("UTF-8", true);
		xmlSerializer.startTag("", TAG_LYSS);
		xmlSerializer.startTag("", TAG_REQ);

		xmlSerializer.startTag("", TAG_FUNC);
		xmlSerializer.text("getDir5");
		xmlSerializer.endTag("", TAG_FUNC);

		xmlSerializer.startTag("", TAG_VERSIONCODE);
		xmlSerializer.text(OtherHelper.getVersionCode(context) + "");
		xmlSerializer.endTag("", TAG_VERSIONCODE);

		xmlSerializer.startTag("", TAG_TKNA);

		xmlSerializer.startTag("", TAG_APPK);
		xmlSerializer.text(Constant.APP_KEY);
		xmlSerializer.endTag("", TAG_APPK);

		xmlSerializer.startTag("", TAG_SALT);
		xmlSerializer.text(Base64.encode(salt));
		xmlSerializer.endTag("", TAG_SALT);

		xmlSerializer.startTag("", TAG_DEVID);
		xmlSerializer.text(devid);
		xmlSerializer.endTag("", TAG_DEVID);

		xmlSerializer.startTag("", TAG_DATE);
		xmlSerializer.text(getDateA());
		xmlSerializer.endTag("", TAG_DATE);

		xmlSerializer.startTag("", TAG_SIGN);
		xmlSerializer.text(getSignA(Constant.APP_KEY, devid, salt));
		xmlSerializer.endTag("", TAG_SIGN);

		xmlSerializer.endTag("", TAG_TKNA);

		xmlSerializer.endTag("", TAG_REQ);
		xmlSerializer.endTag("", TAG_LYSS);

		xmlSerializer.flush();
		return xmlWriter.toString();
	}

	/**
	 * 使用tokenB生成目录服务器请求数据
	 */
	private String createGetInfoRequestDataWhithTokenB(Context context)
			throws Exception {
		StringWriter xmlWriter = new StringWriter();
		XmlSerializer xmlSerializer = Xml.newSerializer();
		xmlSerializer.setOutput(xmlWriter);
		xmlSerializer.startDocument("UTF-8", true);
		xmlSerializer.startTag("", TAG_LYSS);
		xmlSerializer.startTag("", TAG_REQ);

		xmlSerializer.startTag("", TAG_FUNC);
		xmlSerializer.text("getDir6");
		xmlSerializer.endTag("", TAG_FUNC);

		xmlSerializer.startTag("", TAG_VERSIONCODE);
		xmlSerializer.text(OtherHelper.getVersionCode(context) + "");
		xmlSerializer.endTag("", TAG_VERSIONCODE);

		// 插入tokenB
		insertTokenB(xmlSerializer, xmlWriter);

		xmlSerializer.endTag("", TAG_REQ);
		xmlSerializer.endTag("", TAG_LYSS);

		xmlSerializer.flush();
		return xmlWriter.toString();
	}

	public String createAppKeyPropertyRequestData() throws Exception {
		StringWriter xmlWriter = new StringWriter();
		XmlSerializer xmlSerializer = Xml.newSerializer();
		xmlSerializer.setOutput(xmlWriter);
		xmlSerializer.startDocument("UTF-8", true);
		xmlSerializer.startTag("", TAG_LYSS);
		xmlSerializer.startTag("", TAG_REQ);

		xmlSerializer.startTag("", TAG_FUNC);
		xmlSerializer.text("getAppkeyProperty");
		xmlSerializer.endTag("", TAG_FUNC);

		xmlSerializer.startTag("", "TYPE");
		xmlSerializer.text("1");
		xmlSerializer.endTag("", "TYPE");

		insertTokenB(xmlSerializer, xmlWriter);

		xmlSerializer.endTag("", TAG_REQ);
		xmlSerializer.endTag("", TAG_LYSS);

		xmlSerializer.flush();
		return xmlWriter.toString();
	}
    @SuppressLint("SimpleDateFormat")
    private String getDateA() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String dateStr = dateFormat.format(date);
        return dateStr;
    }
    /**
     * 生成sign
     *
     * @param appkey
     * @param devid
     * @param salt
     * @return
     */
    private String getSignA(String appkey, String devid, byte[] salt) {
        UUID appkeyU = UUID.fromString(appkey);
        UUID devidU = UUID.fromString(devid);
        byte[] appkeyBytes = getBytesByUUID(appkeyU);
        byte[] devidBytes = getBytesByUUID(devidU);
        ByteBuffer bf = ByteBuffer.allocate(40);
        bf.put(appkeyBytes);
        bf.put(devidBytes);
        bf.put(salt);
        byte[] data = bf.array();
        try {
            byte[] md5 = MD5.Md5(data);
            byte[] password = ActivityUtils.get16byte(appkey);
            data = AES.encrypt(md5, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String base64Data = Base64.encode(data);
        return base64Data;
    }
    /**
     * 使用uuid生成16byte
     */
    private byte[] getBytesByUUID(UUID uuid) {
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        return bb.array();
    }
    /**
     * 用mac地址生成uuid
     *
     * @param context
     * @return
     */
    private String getDEVID(Context context) {
        WifiManager wifi = (WifiManager) context
                .getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        String macAdd = info.getMacAddress();
        if (macAdd == null || macAdd.equals("")) {
            macAdd = "defaultMacAdd";
        }
        UUID uuid = UUID.nameUUIDFromBytes(macAdd.getBytes());
        return uuid.toString();
    }
	/**
	 * 取出tokenB，包含TKNB标签
	 *
	 * @return 如果不存在就返回null
	 */
	public String getTokenB() {
		File cacheDir = context.getCacheDir();
		File fileDir = new File(cacheDir.getPath() + "/contentServer");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		FileInputStream in = null;
		try {
			in = new FileInputStream(fileDir.getPath() + "/tokenB");
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			return "";
		}
		BufferedInputStream bufferin = new BufferedInputStream(in);
		byte c[] = new byte[1024];
		int n = 0;
		StringBuffer tokenb = new StringBuffer();
		try {
			while ((n = bufferin.read(c)) != -1) {
				String temp = new String(c, 0, n);
				tokenb.append(temp);
			}
			bufferin.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String tokenB = tokenb.toString();
		return tokenB;
	}

	/**
	 * <b>[private]</b>获取Element中某个节点的内容，不包含子节点
	 */
	private String getNodeValue(Element e, String tag) {
		if (((Element) (e.getElementsByTagName(tag).item(0))).getFirstChild() != null) {
			return ((Element) (e.getElementsByTagName(tag).item(0)))
					.getFirstChild().getNodeValue();
		}
		return "";
	}

	/**
	 * <b>[private]</b>获取tokenb中的某个标签里的内容
	 */
	public String getTagContentFromTokenBFile(String tagName) {
		try {
			File cacheDir = context.getCacheDir();
			File fileDir = new File(cacheDir.getPath() + "/contentServer");
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			File xmlFile = new File(fileDir.getPath() + "/tokenB");
			// 从本地文件读
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = null;
			document = builder.parse(xmlFile);
			// 获取根节点
			Element root = document.getDocumentElement();
			String content = getNodeValue(root, tagName);
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 从tokenb缓存文件中取得APPK
	 */
	public String getAppk() {
		return getTagContentFromTokenBFile("APPK");
	}

	/**
	 * 从tokenb缓存文件中取得DEVID
	 */
	public String getDevid() {
		return getTagContentFromTokenBFile("DEVID");
	}

	/**
	 * 从tokenb缓存文件中取得SALT
	 */
	public String getSalt() {
		return getTagContentFromTokenBFile("SALT");
	}
    /**
     * 8byte随机数
     *
     * @return
     */
    private byte[] getSaltA() {
        byte[] b = new byte[8];
        Random random = new Random();
        random.nextBytes(b);
        return b;
    }
	/**
	 * 从tokenb缓存文件中取得DATE
	 */
	public String getDate() {
		return getTagContentFromTokenBFile("DATE");
	}

	/**
	 * 从tokenb缓存文件中取得SIGN
	 */
	public String getSign() {
		return getTagContentFromTokenBFile("SIGN");
	}

	/**
	 * 将tokenb保存到缓存文件
	 */
	public void saveTokenB(Element tokenBRootElement) {

		if(tokenBRootElement == null){
			return;
		}
		// 保存到本地
		File cacheDir = context.getCacheDir();
		File fileDir = new File(cacheDir.getPath() + "/contentServer");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		try {
			TransformerFactory xformFactory = TransformerFactory.newInstance();

			Transformer idTransform = xformFactory.newTransformer();
			idTransform.setOutputProperty(OutputKeys.ENCODING, ENCODE);
			idTransform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,
					"yes");

			Source input = new DOMSource(tokenBRootElement);

			Result output = new StreamResult(new FileOutputStream(
					fileDir.getPath() + "/tokenB"));
			idTransform.transform(input, output);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 删除tokenB
	 */
	public void deleteTokenB(){
		File cacheDir = context.getCacheDir();
		File fileDir = new File(cacheDir.getPath() + "/contentServer");
		if (!fileDir.exists()) {
			fileDir.mkdirs();
		}
		File tokenB = new File(fileDir.getPath() + "/tokenB");
		tokenB.delete();
	}

}
