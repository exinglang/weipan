package com.puxtech.weipan.network;

import android.content.Context;

import com.puxtech.weipan.data.entitydata.EnvironmentEntity;
import com.puxtech.weipan.data.entitydata.PriceEntity;
import com.puxtech.weipan.util.Algorithm;
import com.puxtech.weipan.util.BytesConverter;
import com.puxtech.weipan.util.MD5;

import java.nio.ByteBuffer;

/**
 * Created by mac on 15/11/4.
 */
public class RequestDataCreatorPrice extends
RequestDataCreatorPriceBase{
    private Context context;


    public RequestDataCreatorPrice(Context context) {
        super(context);

    }
    public RequestDataCreatorPrice() {
        super();

    }
    /**
     * 请求服务器协议（非共用头，无需验证信息）
     */
    public ByteBuffer createProtocolRequestData() {
        ByteBuffer buffer = ByteBuffer.allocate(1);
        buffer.put((byte) 1);
        return buffer;
    }


    /**
     * 请求行情登陆
     */
    public ByteBuffer createPriceLogonRequestData() {
        String version = myapp.getCurrentTradeEntity().getPriceData().getPriceRuntimeData().getProtocolInfo().getVersion();
        byte[] versionBytes = version.getBytes();
        byte[] key = Algorithm.GenerateGUID();
        String randomString = Algorithm.randomString(6);
        byte[] random = BytesConverter.getBytes(randomString);
        byte[] random1 = MD5.encrypt(random, key);// 加密后的随机数

        String userName = "android";
        String pwd = "123456";
        String memberId = "memberAndroid";
        String domain = "KPI";
        byte[] pwdBytes = BytesConverter.getBytes(pwd);
        pwdBytes = MD5.Md5(pwdBytes);
        byte[] userNameBytes = userName.getBytes();
        byte[] domainBytes = domain.getBytes();
        byte[] memberIdBytes = memberId.getBytes();

        ByteBuffer uerInfoBuffer = ByteBuffer.allocate(16 + pwdBytes.length
                + userNameBytes.length + domainBytes.length
                + memberIdBytes.length);
        uerInfoBuffer.putInt(userNameBytes.length);
        uerInfoBuffer.put(userNameBytes);
        uerInfoBuffer.putInt(domainBytes.length);
        uerInfoBuffer.put(domainBytes);
        uerInfoBuffer.putInt(pwdBytes.length);
        uerInfoBuffer.put(pwdBytes);
        uerInfoBuffer.putInt(memberIdBytes.length);
        uerInfoBuffer.put(memberIdBytes);
        byte[] userInfo = uerInfoBuffer.array();
        userInfo = MD5.encrypt(userInfo, key);

        ByteBuffer buffer = ByteBuffer
                .allocate(10 + versionBytes.length + key.length + random.length
                        + random1.length + userInfo.length);
        buffer.put((byte) 15);
        buffer.put((byte) versionBytes.length);
        buffer.put(versionBytes);
        buffer.put((byte) 1);
        buffer.put((byte) key.length);
        buffer.put(key);
        buffer.put((byte) random.length);
        buffer.put(random);
        buffer.put((byte) random1.length);
        buffer.put(random1);

        buffer.putInt(userInfo.length);
        buffer.put(userInfo);
        return buffer;

    }


    /**
     * 请求K线周期
     */
    public ByteBuffer createKLineCycleRequestData() {
        byte[] head = getHead(11);
        ByteBuffer buffer = ByteBuffer.allocate(head.length);
        buffer.put(head);
        return buffer;

    }

    /**
     * 请求市场信息列表
     */
    public ByteBuffer createMarketInfoRequestData() {
        byte[] head = getHead(0x22);
        ByteBuffer buffer = ByteBuffer.allocate(head.length);
        buffer.put(head);
        return buffer;

    }

    /**
     * 请求商品信息根据 环境CODE
     */
    public ByteBuffer createCommodityInfoRequestData( EnvironmentEntity entity) {
        byte[] head = getHead(0x23);
        ByteBuffer buffer = ByteBuffer.allocate(head.length + 4);
        buffer.put(head);
        buffer.putShort((short) entity.getMarketId());
        buffer.putShort((short) entity.getId());
        return buffer;

    }

    /**
     * 请求所有商品盘口
     * @param
     * @param entity
     * @return
     */
    public ByteBuffer createAllCommodityPriceRequestData( EnvironmentEntity entity) {

//        byte[] head = this.sharedDataManager.getHead(context, 0x25, zuId);
//        ByteBuffer buffer = ByteBuffer.allocate(head.length + 2 + 2);
//        buffer.put(head);
//
//        buffer.putShort((short) marketNumber);
//        buffer.putShort((short) envNumber);
        byte[] head = getHead(0x25);
        ByteBuffer buffer = ByteBuffer.allocate(head.length + 4);
        buffer.put(head);
        buffer.putShort((short) entity.getMarketId());
        buffer.putShort((short) entity.getId());
        return buffer;

    }
    /**
     * 请求分时,日
     * @param
     * @param
     * @return
     */
    public ByteBuffer createTimeLineDays(int commodityNumber, int marketNumber, int evnNumber) {

        byte[] head = getHead(0x1A);
        ByteBuffer buffer = ByteBuffer.allocate(head.length + 2 + 2 + 2);
        buffer.put(head);
        buffer.putShort((short) marketNumber);
        buffer.putShort((short) evnNumber);
        buffer.putShort((short) commodityNumber);
        return buffer;

    }

    /**
     * 请求分时,日
     * @param
     * @param pe
     *@param
     * @param startTime
     * @param   @return
     */
    public ByteBuffer createTimeLineOneRequestData(PriceEntity pe, int commodityNumber, short cycle, long startTime, int direction, int count) {
        byte[] head = getHead( 0x20);
        ByteBuffer buffer = null;
        buffer = ByteBuffer.allocate(head.length + 2 + 2 + 2 + 2 + 8 + 1 + 4);
        buffer.put(head);
        buffer.putShort((short) pe.getMarketNumber());
        buffer.putShort((short) pe.getEnvNumber());
        buffer.putShort((short) commodityNumber);
        buffer.putShort(cycle);
        buffer.putLong(startTime);// 参考开始时间
        buffer.put((byte) direction);// 方向
        buffer.putInt(count);// 请求的个数
        return buffer;



    }

    public ByteBuffer createKLineOneRequestData( int commodityNumber, short cycle,
                                                long startTime, int direction, int count, int marketNumber,
                                                int envNumber) {
        byte[] head = getHead(0x1E);
        ByteBuffer buffer = null;
        buffer = ByteBuffer.allocate(head.length + 4 + 2 + 2 + 8 + 1 + 4);

        buffer.put(head);
        buffer.putShort((short) marketNumber);
        buffer.putShort((short) envNumber);
        buffer.putShort((short) commodityNumber);// 商品编号
        buffer.putShort(cycle);// 周期
        buffer.putLong(startTime);// 参考开始时间
        buffer.put((byte) direction);// 方向
        buffer.putInt(count);// 请求的个数
        return buffer;
    }
    public ByteBuffer createKLineAndZipUrlRequestData( short commodityNumber, short cycle, int martketNumber, int envNumber) {
        byte[] head = getHead( 0x21);
        ByteBuffer buffer = ByteBuffer.allocate(head.length + 4 + 2 + 2 + 1);
        buffer.put(head);
        buffer.putShort((short) martketNumber);
        buffer.putShort((short) envNumber);
        buffer.putShort(commodityNumber);
        buffer.putShort(cycle);
        buffer.put(new byte[]{(byte) 30});
        return buffer;
    }
//    public ByteBuffer createKLineFromZipUrlRequestData( String zipUrl, int zuId) {
//        byte[] head = getHead( 0x21, zuId);
//        ByteBuffer buffer = ByteBuffer.allocate(head.length + 4 + 2 + 2 + 1);
//        buffer.put(head);
//        buffer.putShort((short) martketNumber);
//        buffer.putShort((short) envNumber);
//        buffer.putShort(commodityNumber);
//        buffer.putShort(cycle);
//        buffer.put(new byte[]{(byte) 30});
//        return buffer;
//    }
    /**
     * 请求ZIP包秘钥
     * @param zuId
     * @return
     */
    public ByteBuffer createPriceSercetList() {
//        byte[] head = sharedDataManager.getHead(context, 0x18, zuId);
//        ByteBuffer buffer = ByteBuffer.allocate(head.length);
//        buffer.put(head);


        byte[] head = getHead(0x18);
        ByteBuffer buffer = ByteBuffer.allocate(head.length );
        buffer.put(head);

        return buffer;

    }

    /**
     * 请求盘的开市时间
     * @param
     * @return
     */
    public ByteBuffer createEnvTradeTimeRequestData(int marketNumber, int evnNumber) {

        byte[] head = getHead(0x24);
        ByteBuffer buffer = ByteBuffer.allocate(head.length + 4);
        buffer.put(head);
        buffer.putShort((short)marketNumber);
        buffer.putShort((short)evnNumber);
        return buffer;

    }
}
