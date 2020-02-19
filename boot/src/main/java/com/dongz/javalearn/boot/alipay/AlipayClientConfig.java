package com.dongz.javalearn.boot.alipay;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author dong
 * @date 2020/2/19 13:15
 * @desc
 */
@Data
@AllArgsConstructor
public class AlipayClientConfig {

    /**
     * 网关.
     */
    private String gatewayUrl;
    /**
     * appid.
     */
    private String appId;
    /**
     * 私钥.
     */
    private String appPrivateKey;
    /**
     * 格式 默认json.
     */
    private String format;
    /**
     * 编码 默认utf-8.
     */
    private String charset;
    /**
     * 支付宝公钥.
     */
    private String alipayPublicKey;
    /**
     * 签名方式.
     */
    private String signType;
}
