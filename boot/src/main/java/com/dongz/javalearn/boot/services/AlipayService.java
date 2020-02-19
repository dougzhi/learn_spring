package com.dongz.javalearn.boot.services;

import com.dongz.javalearn.boot.alipay.AlipayClientConfig;
import org.springframework.stereotype.Service;

/**
 * @author dong
 * @date 2020/2/19 13:20
 * @desc
 */
@Service
public class AlipayService {

    public AlipayClientConfig getConfig() {
        String gateway = "https://openapi.alipay.com/gateway.do";
        String appid = "2021001131636874";
        String appPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCoWgWH3UTLj0VU6T3oACSezRwkdtaKuO0svrMCb0CFQugM2ncbqScyrDfL1fhpQacRPZlujA4NsCNZXeX3fFympcdX3yZy3GSQRouXPohdYBjSTV73T2wkuduoGSpfPc7MaHS/LR0/LsLwJuXb5NsFN88PFdXQM3YjvrVnQsoy0qCqyrWpgXIzsQv4xfUelCOIbMrpYNKviCmbfzlgAbMvyr5JC44lgjeJJdBniXbUBmHz35FstcirjsntGlo9kww9CYry6ZM0YC19j0D4CN9o6amJk9aTIRx1IvWrVS8GghUrjufFdG+3C8duMYrhAXpFxzUsvQyB7XXozCfR19WZAgMBAAECggEAKS40EiLm9CAspnOTaA+45Xtoueaz3iUUJxDSuvK5xp8yIgrCfeqnovaLg3nolN6xXj9CK89olKKUUwTr/tjV1RHSLEVyo0AT/VK0TJ/IPYcHK1Ivu7Ea0/T+rSTSv49f6ZJXlHgVomHu3020hlB53tHi/V6q+Y3NBTteJk8pJLsHqNMgIMPKEaz5mUdQ3xqGzCSoLLLGBnpahZBq6KQC9SInrU2/qATUofSV7iV+lFntaLDMzhy845m6+tPdnsOL7gkZQsr3HQFGZjDLTigXkyTSKHhXC5oQ6rIBHdJl4fsB6VdySAS0nm8WHYIarGlGfYqQO957qoATyyNuXmu5zQKBgQD6PQbpyc+pj8stGCiV6Y5l1dofwxRSnl+naTbY4oubyNBBFpeaKPnac2KG6aBulumkO/prWDE1WI6/7A9pNX61enjSwOba8SXWnWf0tpQLCHiqsuQNPqLsLj8FURkR6kV1WkSpolCQH2cDXZs9XNl4dfIskd4QakQlohdPVedNdwKBgQCsOlT4y+j9z00XGbSav8FY27O0FivAi8tosfTRucsuVN4loaOCcz0np+yVu38EmaktRLDj25yHcBX8vafg2EgJIAghpArFCdAbUc1eQOxMZXs1+aKOSxnxSGSKbLwYL4+mLwG3n1kCHYBAeGHr34g0zdG3NCmdlnnNCf0MtJB5bwKBgAIHCo5MX01dZ+UGpBWK3ed/UxQczYZZ2CaQGKyrMTxi5SUR3MqX+GZHtw2myPD8cgotnjrObR+khwuRCxLsJwmrY105yOUPPSI/Bj+aVxq7x9aCOozM7B8DxOjzHkPh5QwtXg8NzJ9OUdRaJs7ZeL2cHwkzaVwysDip1oyDAqvDAoGBAI5PBLcvhi1yhTh5PQpBN/m8UBiV2UEYQdMYeDpzUrfN2GEv1nOL1RRXZN20N5gNgASvpZohdI1UyFY2P+lBzQP9O7rQWTXGjfaE+TTQYHAfylcnFnKNC7qbHrhicGlroz6lyL15JYrh5X9f+Ul1Qo6fIpJGv7w/AhWyEy3UZSxxAoGBAM1Wp/zMdUrtxbxxErwopjP+1vOC1FpWK5CF+Ddc2l2aoJ/zAR2rcz9TnsSpZQ78TC6SdABEkrhVhNnNuMAPx71Mat57dYSLaBo6D+KJ/mae8/cqq1CuRywvUZ77s+KNbrqb/CcHRE5SxDOI/3forLvQJjHLutkIBEPJJ+hfJNve";
        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqFoFh91Ey49FVOk96AAkns0cJHbWirjtLL6zAm9AhULoDNp3G6knMqw3y9X4aUGnET2ZbowODbAjWV3l93xcpqXHV98mctxkkEaLlz6IXWAY0k1e909sJLnbqBkqXz3OzGh0vy0dPy7C8Cbl2+TbBTfPDxXV0DN2I761Z0LKMtKgqsq1qYFyM7EL+MX1HpQjiGzK6WDSr4gpm385YAGzL8q+SQuOJYI3iSXQZ4l21AZh89+RbLXIq47J7RpaPZMMPQmK8umTNGAtfY9A+AjfaOmpiZPWkyEcdSL1q1UvBoIVK47nxXRvtwvHbjGK4QF6Rcc1LL0Mge116Mwn0dfVmQIDAQAB";
        String format = "json";
        String charset = "utf-8";
        String signType = "RSA2";
        return new AlipayClientConfig(gateway, appid, appPrivateKey, format, charset, alipayPublicKey, signType);
    }
}
