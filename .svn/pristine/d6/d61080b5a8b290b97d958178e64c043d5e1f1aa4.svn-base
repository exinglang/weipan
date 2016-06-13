package com.puxtech.weipan.parser;

import android.content.Context;
import android.text.TextUtils;


import com.puxtech.weipan.data.entitydata.ContentsServerCmmdEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerEnvEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerJysEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerLlEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerServiceEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerYwEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerYwsysEntity;
import com.puxtech.weipan.data.entitydata.ContentsServerZuEntity;
import com.puxtech.weipan.data.entitydata.VersionEntity;

import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <b>Description:</b>
 * <p>
 * 目录服务器解析类
 * </p>
 * 
 * @Company 北京乐赢索思软件科技有限责任公司
 * @author fanshuo
 * @date 2014-4-20 下午10:04:35
 * @version V1.0
 */
public class ContentsServerParser extends BaseParser{
	protected final String TAG_RESP = "RESP";
	protected final String TAG_RESP_CODE = "CODE";
	protected final String TAG_RESP_MSG = "MSG";
	final String TAG_RESP_TKNB = "TKNB";
	final String TAG_RESP_TKNB_APPK = "APPK";
	final String TAG_RESP_TKNB_DEVID = "DEVID";
	final String TAG_RESP_TKNB_SALT = "SALT";
	final String TAG_RESP_TKNB_DATE = "DATE";
	final String TAG_RESP_TKNB_SIGN = "SIGN";
	final String TAG_RESP_YWSYS = "YWSYS";
	final String TAG_RESP_YWSYS_CODE = "CODE";
	final String TAG_RESP_YWSYS_YWSYSN = "YWSYSN";
	final String TAG_RESP_YWSYS_YWSYSJ = "YWSYSJ";
	// ***********************新增SERVICES
	final String TAG_RESP_YWSYS_SERVICES = "SERVICES";
	final String TAG_RESP_YWSYS_SERVICES_SERVICE = "SERVICE";
	final String TAG_RESP_YWSYS_SERVICES_SERVICE_TYPE = "TYPE";
	final String TAG_RESP_YWSYS_SERVICES_SERVICE_SERVICENA = "SERVICENA";
	final String TAG_RESP_YWSYS_SERVICES_SERVICE_ZU = "ZU";
	final String TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_ZUID = "ZUID";
	final String TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_ZUNA = "ZUNA";
	final String TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_LL = "LL";
	final String TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_LL_CODE = "CODE";
	final String TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_LL_LLNA = "LLNA";
	final String TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_LL_URL = "URL";
	// **********************************

	final String TAG_RESP_YWSYS_JYS = "JYS";
	final String TAG_RESP_YWSYS_JYS_CODE = "CODE";
	final String TAG_RESP_YWSYS_JYS_JYSN = "JYSN";
	final String TAG_RESP_YWSYS_JYS_JYSJ = "JYSJ";
	final String TAG_RESP_YWSYS_JYS_ENV = "ENV";
	final String TAG_RESP_YWSYS_JYS_ENV_ENVN = "ENVN";
	final String TAG_RESP_YWSYS_JYS_ENV_ENVJC = "ENVJC";
	final String TAG_RESP_YWSYS_JYS_ENV_ENVTAG = "ENVTAG";
	final String TAG_RESP_YWSYS_JYS_ENV_ENVCODE = "ENVCODE";
	final String TAG_RESP_YWSYS_JYS_ENV_ENVCAT = "ENVCAT";
	final String TAG_RESP_YWSYS_JYS_ENV_YW = "YW";
	final String TAG_RESP_YWSYS_JYS_ENV_YW_TYPE = "TYPE";
	final String TAG_RESP_YWSYS_JYS_ENV_YW_YWNA = "YWNA";
	final String TAG_RESP_YWSYS_JYS_ENV_YW_LL = "LL";
	final String TAG_RESP_YWSYS_JYS_ENV_YW_LL_CODE = "CODE";
	final String TAG_RESP_YWSYS_JYS_ENV_YW_LL_LLNA = "LLNA";
	final String TAG_RESP_YWSYS_JYS_ENV_YW_LL_URL = "URL";
	final String TAG_RESP_YWSYS_CMMDS = "CMMDS";
	final String TAG_RESP_YWSYS_CMMDS_CMMD = "CMMD";
	final String TAG_RESP_YWSYS_CMMDS_CMMD_YWSYSCODE = "YWSYSCODE";
	final String TAG_RESP_YWSYS_CMMDS_CMMD_JYSCODE = "JYSCODE";
	final String TAG_RESP_YWSYS_CMMDS_CMMD_ENVCODE = "ENVCODE";
	final String TAG_RESP_YWSYS_CMMDS_CMMD_CMMDCODE = "CMMDCODE";
	final String TAG_RESP_YWSYS_VERSION = "VERSION";
	final String TAG_RESP_YWSYS_VERSION_VERSIONCODE = "VERSIONCODE";
	final String TAG_RESP_YWSYS_VERSION_VERSIONNAME = "VERSIONNAME";
	final String TAG_RESP_YWSYS_VERSION_NEEDUPDATE = "NEEDUPDATE";
	final String TAG_RESP_YWSYS_VERSION_UPDATREURL = "UPDATREURL";
	final String TAG_RESP_YWSYS_VERSION_PRPT = "PRPT";// 更新内容

	ContentsServerEntity entity;
	Context context;

	public ContentsServerParser(Context context) {
		super();
		this.context = context;
		this.entity = new ContentsServerEntity();
	}

	public ContentsServerEntity parse(Element rootElement) {
		Element resp = (Element) rootElement.getElementsByTagName(TAG_RESP)
				.item(0);
		Element respCode = (Element) resp.getElementsByTagName(TAG_RESP_CODE)
				.item(0);
		entity.setCode(Integer.parseInt(respCode.getTextContent()));
		Element respMsg = (Element) resp.getElementsByTagName(TAG_RESP_MSG)
				.item(0);
		entity.setMessage(respMsg.getTextContent());
		// 如果服务器验证失败就直接返回
		if (entity.getCode() != 0) {
			return entity;
		}
		// YWSYS
		Element respYwsys = (Element) resp.getElementsByTagName(TAG_RESP_YWSYS)
				.item(0);
		Element respYwsysCode = (Element) respYwsys.getElementsByTagName(
				TAG_RESP_YWSYS_CODE).item(0);
		Element respYwsysYwsysn = (Element) respYwsys.getElementsByTagName(
				TAG_RESP_YWSYS_YWSYSN).item(0);
		Element respYwsysYwsysj = (Element) respYwsys.getElementsByTagName(
				TAG_RESP_YWSYS_YWSYSJ).item(0);
		ContentsServerYwsysEntity ywSystemEntity = new ContentsServerYwsysEntity();
		ywSystemEntity
				.setCode(Integer.parseInt(respYwsysCode.getTextContent()));
		ywSystemEntity.setSystemName(respYwsysYwsysn.getTextContent());
		ywSystemEntity.setSystemShortName(respYwsysYwsysj.getTextContent());
		// YWSYS-SERVICES
		List<ContentsServerServiceEntity> serviceList = new ArrayList<ContentsServerServiceEntity>();
		Element respYwsysServices = (Element) respYwsys.getElementsByTagName(
				TAG_RESP_YWSYS_SERVICES).item(0);
		NodeList respYwsysServiceNodeList = respYwsysServices
				.getElementsByTagName(TAG_RESP_YWSYS_SERVICES_SERVICE);
		for (int i = 0; i < respYwsysServiceNodeList.getLength(); i++) {
			Element respYwsysService = (Element) respYwsysServiceNodeList
					.item(i);
			ContentsServerServiceEntity serviceEntity = new ContentsServerServiceEntity();
			Element respYwsysServiceType = (Element) respYwsysService
					.getElementsByTagName(TAG_RESP_YWSYS_SERVICES_SERVICE_TYPE)
					.item(0);
			Element respYwsysServiceName = (Element) respYwsysService
					.getElementsByTagName(
							TAG_RESP_YWSYS_SERVICES_SERVICE_SERVICENA).item(0);
			serviceEntity.setType(Integer.parseInt(respYwsysServiceType
					.getTextContent()));
			serviceEntity.setName(respYwsysServiceName.getTextContent());
			List<ContentsServerZuEntity> zuList = new ArrayList<>();
			NodeList respYwsysServiceZuNodeList = respYwsysService
					.getElementsByTagName(TAG_RESP_YWSYS_SERVICES_SERVICE_ZU);
			for (int j = 0; j < respYwsysServiceZuNodeList.getLength(); j++) {
				Element respYwsysServiceZu = (Element) respYwsysServiceZuNodeList
						.item(j);
				Element respYwsysServiceZuZuid = (Element) respYwsysServiceZu
						.getElementsByTagName(
								TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_ZUID)
						.item(0);
				Element respYwsysServiceZuZuna = (Element) respYwsysServiceZu
						.getElementsByTagName(
								TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_ZUNA)
						.item(0);
				ContentsServerZuEntity zuEntity = new ContentsServerZuEntity();
				zuEntity.setId(Integer.parseInt(respYwsysServiceZuZuid
						.getTextContent()));
				zuEntity.setName(respYwsysServiceZuZuna.getTextContent());
				List<ContentsServerLlEntity> llList = new ArrayList<ContentsServerLlEntity>();
				NodeList respYwsysHqfwZuLlNodeList = respYwsysServiceZu
						.getElementsByTagName(TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_LL);
				// YWSYS-SERVICE-ZU-LL
				for (int k = 0; k < respYwsysHqfwZuLlNodeList.getLength(); k++) {
					Element respYwsysServiceZuLl = (Element) respYwsysHqfwZuLlNodeList
							.item(k);
					Element respYwsysServiceZuLlCode = (Element) respYwsysServiceZuLl
							.getElementsByTagName(
									TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_LL_CODE)
							.item(0);
					Element respYwsysServiceZuLlLlna = (Element) respYwsysServiceZuLl
							.getElementsByTagName(
									TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_LL_LLNA)
							.item(0);
					Element respYwsysServiceZuLlUrl = (Element) respYwsysServiceZuLl
							.getElementsByTagName(
									TAG_RESP_YWSYS_SERVICES_SERVICE_ZU_LL_URL)
							.item(0);
					ContentsServerLlEntity llEntity = new ContentsServerLlEntity();
					llEntity.setCode(Integer.parseInt(respYwsysServiceZuLlCode
							.getTextContent()));
					llEntity.setName(respYwsysServiceZuLlLlna.getTextContent());
					llEntity.setUrl(respYwsysServiceZuLlUrl.getTextContent());
					llList.add(llEntity);
				}
				zuEntity.setLlList(llList);
				zuList.add(zuEntity);
			}
			serviceEntity.setZuList(zuList);
			serviceList.add(serviceEntity);
		}
		ywSystemEntity.setServiceList(serviceList);
		// YWSYS-JYS
		List<ContentsServerJysEntity> jysList = new ArrayList<ContentsServerJysEntity>();
		NodeList respYwsysJysNodeList = respYwsys
				.getElementsByTagName(TAG_RESP_YWSYS_JYS);
		for (int i = 0; i < respYwsysJysNodeList.getLength(); i++) {
			Element respYwsysJys = (Element) respYwsysJysNodeList.item(i);
			Element respYwsysJysCode = (Element) respYwsysJys
					.getElementsByTagName(TAG_RESP_YWSYS_JYS_CODE).item(0);
			Element respYwsysJysJysN = (Element) respYwsysJys
					.getElementsByTagName(TAG_RESP_YWSYS_JYS_JYSN).item(0);
			Element respYwsysJysJysJ = (Element) respYwsysJys
					.getElementsByTagName(TAG_RESP_YWSYS_JYS_JYSJ).item(0);
			ContentsServerJysEntity jysEntity = new ContentsServerJysEntity();
			jysEntity.setType(Integer.parseInt(respYwsysJys
					.getAttribute("type")));
			jysEntity.setCode(Integer.parseInt(respYwsysJysCode
					.getTextContent()));
			jysEntity.setName(respYwsysJysJysN.getTextContent());
			jysEntity.setShortName(respYwsysJysJysJ.getTextContent());
			List<ContentsServerEnvEntity> envList = new ArrayList<ContentsServerEnvEntity>();
			NodeList respYwsysJysEnvNodeList = respYwsysJys
					.getElementsByTagName(TAG_RESP_YWSYS_JYS_ENV);
			// YWSYS-JYS-ENV
			for (int j = 0; j < respYwsysJysEnvNodeList.getLength(); j++) {
				Element respYwsysJysEnv = (Element) respYwsysJysEnvNodeList
						.item(j);
				Element respYwsysJysEnvEnvn = (Element) respYwsysJysEnv
						.getElementsByTagName(TAG_RESP_YWSYS_JYS_ENV_ENVN)
						.item(0);
				Element respYwsysJysEnvEnvjc = (Element) respYwsysJysEnv
						.getElementsByTagName(TAG_RESP_YWSYS_JYS_ENV_ENVJC)
						.item(0);
				Element respYwsysJysEnvEnvtag = (Element) respYwsysJysEnv
						.getElementsByTagName(TAG_RESP_YWSYS_JYS_ENV_ENVTAG)
						.item(0);
				Element respYwsysJysEnvEnvcode = (Element) respYwsysJysEnv
						.getElementsByTagName(TAG_RESP_YWSYS_JYS_ENV_ENVCODE)
						.item(0);
				Element respYwsysJysEnvEnvcat = (Element) respYwsysJysEnv
						.getElementsByTagName(TAG_RESP_YWSYS_JYS_ENV_ENVCAT)
						.item(0);
				ContentsServerEnvEntity envEntity = new ContentsServerEnvEntity();
				envEntity.setType(Integer.parseInt(respYwsysJysEnv
						.getAttribute("type")));
				envEntity.setName(respYwsysJysEnvEnvn.getTextContent());
				envEntity.setShortName(respYwsysJysEnvEnvjc.getTextContent());
				envEntity.setEnvTag(respYwsysJysEnvEnvtag.getTextContent());
				envEntity.setCode(Integer.parseInt(respYwsysJysEnvEnvcode
						.getTextContent()));
				envEntity.setCategory(Integer.parseInt(respYwsysJysEnvEnvcat
						.getTextContent()));
				List<ContentsServerYwEntity> ywList = new ArrayList<ContentsServerYwEntity>();
				NodeList respYwsysJysEnvYwNodeList = respYwsysJysEnv
						.getElementsByTagName(TAG_RESP_YWSYS_JYS_ENV_YW);
				// YWSYS-JYS-ENV-YW
				for (int k = 0; k < respYwsysJysEnvYwNodeList.getLength(); k++) {
					Element respYwsysJysEnvYw = (Element) respYwsysJysEnvYwNodeList
							.item(k);
					Element respYwsysJysEnvYwType = (Element) respYwsysJysEnvYw
							.getElementsByTagName(
									TAG_RESP_YWSYS_JYS_ENV_YW_TYPE).item(0);
					Element respYwsysJysEnvYwYwna = (Element) respYwsysJysEnvYw
							.getElementsByTagName(
									TAG_RESP_YWSYS_JYS_ENV_YW_YWNA).item(0);
					ContentsServerYwEntity ywEntity = new ContentsServerYwEntity();
					ywEntity.setType(Integer.parseInt(respYwsysJysEnvYwType
							.getTextContent()));
					ywEntity.setName(respYwsysJysEnvYwYwna.getTextContent());
					List<ContentsServerLlEntity> llList = new ArrayList<ContentsServerLlEntity>();
					NodeList respYwsysJysEnvYwLlNodeList = respYwsysJysEnvYw
							.getElementsByTagName(TAG_RESP_YWSYS_JYS_ENV_YW_LL);
					// YWSYS-JYS-ENV-YW-LL
					for (int l = 0; l < respYwsysJysEnvYwLlNodeList.getLength(); l++) {
						Element respYwsysJysEnvYwLl = (Element) respYwsysJysEnvYwLlNodeList
								.item(l);
						Element respYwsysJysEnvYwLlCode = (Element) respYwsysJysEnvYwLl
								.getElementsByTagName(
										TAG_RESP_YWSYS_JYS_ENV_YW_LL_CODE)
								.item(0);
						Element respYwsysJysEnvYwLlLlna = (Element) respYwsysJysEnvYwLl
								.getElementsByTagName(
										TAG_RESP_YWSYS_JYS_ENV_YW_LL_LLNA)
								.item(0);
						Element respYwsysJysEnvYwLlUrl = (Element) respYwsysJysEnvYwLl
								.getElementsByTagName(
										TAG_RESP_YWSYS_JYS_ENV_YW_LL_URL).item(
										0);
						ContentsServerLlEntity llEntity = new ContentsServerLlEntity();
						llEntity.setCode(Integer
								.parseInt(respYwsysJysEnvYwLlCode
										.getTextContent()));
						llEntity.setName(respYwsysJysEnvYwLlLlna
								.getTextContent());
						llEntity.setUrl(respYwsysJysEnvYwLlUrl.getTextContent());
						llList.add(llEntity);
					}
					ywEntity.setLlList(llList);
					ywList.add(ywEntity);
				}
				envEntity.setYwList(ywList);
				envList.add(envEntity);
			}
			jysEntity.setEnvList(envList);
			jysList.add(jysEntity);
		}
		ywSystemEntity.setJysList(jysList);
		
		Element respYwsysCmmds = (Element) respYwsys.getElementsByTagName(
				TAG_RESP_YWSYS_CMMDS).item(0);
		List<ContentsServerCmmdEntity> cmmdList = new ArrayList<ContentsServerCmmdEntity>();
		NodeList respYwsysCmmdsCmmdNodeList = respYwsysCmmds
				.getElementsByTagName(TAG_RESP_YWSYS_CMMDS_CMMD);
		
		for (int i = 0; i < respYwsysCmmdsCmmdNodeList.getLength(); i++) {
			Element respYwsysCmmd = (Element) respYwsysCmmdsCmmdNodeList
					.item(i);
			Element respYwsysCmmdYwsyscode = (Element) respYwsysCmmd
					.getElementsByTagName(TAG_RESP_YWSYS_CMMDS_CMMD_YWSYSCODE)
					.item(0);
			Element respYwsysCmmdJyscode = (Element) respYwsysCmmd
					.getElementsByTagName(TAG_RESP_YWSYS_CMMDS_CMMD_JYSCODE)
					.item(0);
			Element respYwsysCmmdEnvcode = (Element) respYwsysCmmd
					.getElementsByTagName(TAG_RESP_YWSYS_CMMDS_CMMD_ENVCODE)
					.item(0);
			Element respYwsysCmmdCmmdcode = (Element) respYwsysCmmd
					.getElementsByTagName(TAG_RESP_YWSYS_CMMDS_CMMD_CMMDCODE)
					.item(0);
			ContentsServerCmmdEntity cmmdEntity = new ContentsServerCmmdEntity();
			cmmdEntity.setYwsysCode(Integer.parseInt(respYwsysCmmdYwsyscode
					.getTextContent()));
			cmmdEntity.setJysCode(Integer.parseInt(respYwsysCmmdJyscode
					.getTextContent()));
			cmmdEntity.setEnvCode(Integer.parseInt(respYwsysCmmdEnvcode
					.getTextContent()));
			cmmdEntity.setCmmdCode(respYwsysCmmdCmmdcode.getTextContent());
			cmmdList.add(cmmdEntity);
		}
		ywSystemEntity.setCmmdsList(cmmdList);
		// YWSYS-VERSION
		Element respYwsysVersion = (Element) respYwsys.getElementsByTagName(
				TAG_RESP_YWSYS_VERSION).item(0);
		Element respYwsysVersionVersionCode = (Element) respYwsysVersion
				.getElementsByTagName(TAG_RESP_YWSYS_VERSION_VERSIONCODE).item(
						0);
		Element respYwsysVersionVersionName = (Element) respYwsysVersion
				.getElementsByTagName(TAG_RESP_YWSYS_VERSION_VERSIONNAME).item(
						0);
		Element respYwsysVersionNeedUpdate = (Element) respYwsysVersion
				.getElementsByTagName(TAG_RESP_YWSYS_VERSION_NEEDUPDATE)
				.item(0);
		Element respYwsysVersionUpdateUrl = (Element) respYwsysVersion
				.getElementsByTagName(TAG_RESP_YWSYS_VERSION_UPDATREURL)
				.item(0);
		Element respYwsysVersionUpdateInfo = (Element) respYwsysVersion
				.getElementsByTagName(TAG_RESP_YWSYS_VERSION_PRPT)
				.item(0);
		VersionEntity versionEntity = new VersionEntity();
		if (respYwsysVersionNeedUpdate == null||respYwsysVersionNeedUpdate.getTextContent().equals("0")) {
			versionEntity.setNeedUpdate(0);
			ywSystemEntity.setVersion(versionEntity);
			entity.setYwSystemEntity(ywSystemEntity);
			return entity;
		}
		versionEntity.setVersionCode(Integer
				.parseInt(respYwsysVersionVersionCode.getTextContent()));
		versionEntity.setVersionName(respYwsysVersionVersionName
				.getTextContent());
		versionEntity.setNeedUpdate(Integer.parseInt(respYwsysVersionNeedUpdate
				.getTextContent()));
		versionEntity.setUpdateUrl(respYwsysVersionUpdateUrl.getTextContent());
		if(respYwsysVersionUpdateInfo != null){
			//getTextContent会自动把\n转成\\n，所以还要手动转回来
			String info = respYwsysVersionUpdateInfo.getTextContent();
			info = info.replace("\\n", "\n");
			versionEntity.setUpdateInfo(info);
		}
		ywSystemEntity.setVersion(versionEntity);
		entity.setYwSystemEntity(ywSystemEntity);
		return entity;
	}

	/**
	 * 解析检查版本更新
	 */
	public VersionEntity parseCheckVersion(Element rootElement) {
	
		Element respYwsysVersion = (Element) rootElement.getElementsByTagName(
				TAG_RESP_YWSYS_VERSION).item(0);
		Element respYwsysVersionVersionCode = (Element) respYwsysVersion
				.getElementsByTagName(TAG_RESP_YWSYS_VERSION_VERSIONCODE).item(
						0);
		Element respYwsysVersionVersionName = (Element) respYwsysVersion
				.getElementsByTagName(TAG_RESP_YWSYS_VERSION_VERSIONNAME).item(
						0);
		Element respYwsysVersionNeedUpdate = (Element) respYwsysVersion
				.getElementsByTagName(TAG_RESP_YWSYS_VERSION_NEEDUPDATE)
				.item(0);
		Element respYwsysVersionUpdateUrl = (Element) respYwsysVersion
				.getElementsByTagName(TAG_RESP_YWSYS_VERSION_UPDATREURL)
				.item(0);
		Element respYwsysVersionUpdateInfo = (Element) respYwsysVersion
				.getElementsByTagName(TAG_RESP_YWSYS_VERSION_PRPT)
				.item(0);
		VersionEntity versionEntity = new VersionEntity();
		versionEntity.setVersionCode(Integer
				.parseInt(respYwsysVersionVersionCode.getTextContent()));
		versionEntity.setVersionName(respYwsysVersionVersionName
				.getTextContent());
		versionEntity.setNeedUpdate(Integer.parseInt(respYwsysVersionNeedUpdate
				.getTextContent()));
		if(versionEntity.getNeedUpdate()==1){
			if (respYwsysVersionUpdateUrl != null) {
				versionEntity.setUpdateUrl(respYwsysVersionUpdateUrl
						.getTextContent());
			}
			if(respYwsysVersionUpdateInfo != null){
				//getTextContent会自动把\n转成\\n，所以还要手动转回来
				String info = respYwsysVersionUpdateInfo.getTextContent();
				info = info.replace("\\n", "\n");
				versionEntity.setUpdateInfo(info);
			}
		}
		return versionEntity;
	}
	
//	/**
//	 * 解析获取用户管理服务器地址的返回数据
//	 */
//	public HashMap<String, Object> parseYHGLUrl(Element rootElement) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		List<OtherServerUrl> retList = new ArrayList<OtherServerUrl>();
//		try {
//			String code = getString(rootElement, "CODE");
//			String msg = getString(rootElement, "MSG");
//			hashMap.put("code", code);
//			hashMap.put("msg", msg);
//			if(!"0".equals(code)){
//				return hashMap;
//			}
//			Element servicesElement = (Element) rootElement.getElementsByTagName("SERVICES").item(0);
//			NodeList serviceNodeList = servicesElement.getElementsByTagName("SERVICE");
//			for (int i = 0; i < serviceNodeList.getLength(); i++) {
//				Element serviceNode = (Element) serviceNodeList.item(i);
//				OtherServerUrl yhglUrl = new OtherServerUrl();
//				yhglUrl.setYwSystemCode(getString(serviceNode, "YWSYSCODE"));
//				yhglUrl.setCode(getString(serviceNode, "CODE"));
//				yhglUrl.setType(getString(serviceNode, "TYPE"));
//				yhglUrl.setUrl(getString(serviceNode, "URL"));
//				retList.add(yhglUrl);
//			}
//			hashMap.put("list", retList);
//			return hashMap;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 * 解析获取业务数据采集服务器地址的返回数据
//	 */
//	public HashMap<String, Object> parseDataCollectionUrl(Element rootElement) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		List<OtherServerUrl> retList = new ArrayList<OtherServerUrl>();
//		try {
//			String code = getString(rootElement, "CODE");
//			String msg = getString(rootElement, "MSG");
//			hashMap.put("code", code);
//			hashMap.put("msg", msg);
//			Element dataGathersElement = (Element) rootElement.getElementsByTagName("DATAGATHERS").item(0);
//			NodeList dataGatherNodeList = dataGathersElement.getElementsByTagName("DATAGATHER");
//			for (int i = 0; i < dataGatherNodeList.getLength(); i++) {
//				Element serviceNode = (Element) dataGatherNodeList.item(i);
//				OtherServerUrl url = new OtherServerUrl();
//				url.setYwSystemCode(getString(serviceNode, "YWSYSCODE"));
//				url.setCode(getString(serviceNode, "CODE"));
//				url.setType(getString(serviceNode, "TYPE"));
//				url.setUrl(getString(serviceNode, "URL"));
//				retList.add(url);
//			}
//			hashMap.put("list", retList);
//			return hashMap;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	/**
//	 * 解析获取竞赛AppKey属性的返回数据
//	 */
//	public HashMap<String, Object> parseJSAppKeyProperty(Element rootElement) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		try {
//			String code = getString(rootElement, "CODE");
//			String msg = getString(rootElement, "MSG");
//			hashMap.put("code", code);
//			hashMap.put("msg", msg);
//			Element propertyElement = (Element) rootElement.getElementsByTagName("PROPERTY").item(0);
//			JSAppKeyPropertyEntity propertyEntity = new JSAppKeyPropertyEntity();
//			propertyEntity.setStartDate(getString(propertyElement, "STARTDATE"));
//			propertyEntity.setEndDate(getString(propertyElement, "ENDDATE"));
//			propertyEntity.setBody(getString(propertyElement, "BODY"));
//			propertyEntity.setTime(getString(propertyElement, "TIME"));
//			propertyEntity.setQueryServiceUrl(getString(propertyElement, "QUERYSERVICE"));
//			propertyEntity.setScoreService(getString(propertyElement, "SCORESERVICE"));
//			propertyEntity.setRuleUrl(getString(propertyElement, "RULEURL"));
//			propertyEntity.setMqUrl(getString(propertyElement, "MQURL"));
//			propertyEntity.setMqTopic(getString(propertyElement, "MQTOPIC"));
//			propertyEntity.setMqUsername(getString(propertyElement, "MQUSERNAME"));
//			propertyEntity.setMqPwd(getString(propertyElement, "MQPWD"));
//			propertyEntity.setLastTradeAccount(getString(propertyElement, "LASTTRADEACCOUNT"));
//			propertyEntity.setServicePhone(getString(propertyElement, "CONTESTSERVICEPHONE"));
//			hashMap.put("property", propertyEntity);
//			return hashMap;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//	/**
//	 * 解析Normal AppKey属性的返回数据
//	 */
//	public HashMap<String, Object> parseNormalAppKeyProperty(Element rootElement) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		try {
//			String code = getString(rootElement, "CODE");
//			String msg = getString(rootElement, "MSG");
//			hashMap.put("code", code);
//			hashMap.put("msg", msg);
//			Element propertyElement = (Element) rootElement.getElementsByTagName("PROPERTY").item(0);
//
//
//			NormalAppKeyPropertyEntity propertyEntity = new NormalAppKeyPropertyEntity();
////			hashMap.put("url",getString(propertyElement, "COMMUNITYURL") );
//			propertyEntity.setCommunityUrl(getString(propertyElement, "COMMUNITYURL"));
//			MyApplication myapp = (MyApplication)context.getApplicationContext();
//			myapp.setCommunityUrl(getString(propertyElement, "COMMUNITYURL"));
//
//			myapp.setImageUrl(getString(propertyElement, "IMAGEURL"));
//
//			propertyEntity.setTag(getString(propertyElement, "TAG"));
//
//
//			String showHotNewsStr=getString(propertyElement,"SHOWHOTNEWS");
//			if(!TextUtils.isEmpty(showHotNewsStr))
//			propertyEntity.setShowHotNews(showHotNewsStr.equals("1"));
//
//			String queryHotNewsDuration=getString(propertyElement,"QUERYHOTNEWSDURATION");
//			if(!TextUtils.isEmpty(queryHotNewsDuration))
//			propertyEntity.setShowHotNewsInterval(Integer.valueOf(queryHotNewsDuration)*1000*60);
//
//			propertyEntity.setRongChatRoomId(getString(propertyElement, "RONGCHATROOMID"));
//			propertyEntity.setRongChatRoomName(getString(propertyElement, "RONGCHATROOMNAME"));
//
//			//是否强制清除行情缓存
//			String forceClearHqCacheStr = getString(propertyElement,"FORCECLEARHQCACHE");
//			if(!TextUtils.isEmpty(forceClearHqCacheStr)){
//				try {
//					int forceClearHqCache = Integer.parseInt(forceClearHqCacheStr);
//					propertyEntity.setForceClearHqCache(forceClearHqCache);
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//				}
//			}
//
//			hashMap.put("property", propertyEntity);
//			return hashMap;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}
//
//	public HashMap<String, Object> parseNyAppKeyProperty(Element rootElement) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		try {
//			String code = getString(rootElement, "CODE");
//			String msg = getString(rootElement, "MSG");
//			hashMap.put("code", code);
//			hashMap.put("msg", msg);
//			Element propertyElement = (Element) rootElement.getElementsByTagName("PROPERTY").item(0);
//
//
//			NyAppKeyPropertyEntity propertyEntity = new NyAppKeyPropertyEntity();
////			hashMap.put("url",getString(propertyElement, "COMMUNITYURL") );
//			propertyEntity.setCommunityUrl(getString(propertyElement, "COMMUNITYURL"));
//			try{
//			propertyEntity.setTradeMode(new JSONObject(getString(propertyElement, "TRADEMODE")));
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//
//			MyApplication myapp = (MyApplication)context.getApplicationContext();
//			myapp.setCommunityUrl(getString(propertyElement, "COMMUNITYURL"));
//			myapp.setImageUrl(getString(propertyElement, "IMAGEURL"));
//
//			propertyEntity.setTag(getString(propertyElement, "TAG"));
//
//
//			String showHotNewsStr=getString(propertyElement,"SHOWHOTNEWS");
//			if(!TextUtils.isEmpty(showHotNewsStr))
//			propertyEntity.setShowHotNews(showHotNewsStr.equals("1"));
//
//			String queryHotNewsDuration=getString(propertyElement,"QUERYHOTNEWSDURATION");
//			if(!TextUtils.isEmpty(queryHotNewsDuration))
//			propertyEntity.setShowHotNewsInterval(Integer.valueOf(queryHotNewsDuration)*1000*60);
//
//			propertyEntity.setRongChatRoomId(getString(propertyElement, "RONGCHATROOMID"));
//			propertyEntity.setRongChatRoomName(getString(propertyElement, "RONGCHATROOMNAME"));
//
//			//是否强制清除行情缓存
//			String forceClearHqCacheStr = getString(propertyElement,"FORCECLEARHQCACHE");
//			if(!TextUtils.isEmpty(forceClearHqCacheStr)){
//				try {
//					int forceClearHqCache = Integer.parseInt(forceClearHqCacheStr);
//					propertyEntity.setForceClearHqCache(forceClearHqCache);
//				} catch (NumberFormatException e) {
//					e.printStackTrace();
//				}
//			}
//			//撮合商品的商品代码
//			String cuoHeCodes = getString(propertyElement,"CUOHECODES");
//			propertyEntity.setCuoHeCodes(cuoHeCodes);
//
//			hashMap.put("property", propertyEntity);
//			return hashMap;
//		} catch (Exception e) {
//			e.printStackTrace();
//			return null;
//		}
//	}

//	/**
//	 * 解析热点消息的返回数据
//	 */
//	public HashMap<String, Object> parseHotNewsMessage(Element rootElement) {
//		HashMap<String, Object> hashMap = new HashMap<String, Object>();
//		String code = getString(rootElement, "CODE");
//		String msg = getString(rootElement, "MSG");
//		hashMap.put("code", code);
//		hashMap.put("msg", msg);
//		ArrayList<HotMessageEntity> messageList = new ArrayList<HotMessageEntity>();
//		NodeList messages = rootElement.getElementsByTagName("MESSAGE");
//		if (messages == null)return hashMap;
//		Node node = null;
//		HotMessageEntity hotMessageEntity;
//		for (int i = 0, len = messages.getLength(); i < len; i++) {
//			node = messages.item(i);
//			hotMessageEntity = new HotMessageEntity();
//			hotMessageEntity.setBody(getString((Element)node, "BODY"));
//			hotMessageEntity.setTime(getString((Element)node, "TIME"));
//			messageList.add(hotMessageEntity);
//		}
//		hashMap.put("messages", messageList);
//
//		return hashMap;
//	}
}
