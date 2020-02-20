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
        String gateway = "https://openapi.alipaydev.com/gateway.do";
        String appid = "2016101900722714";
        String appPrivateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC6OtwXfbT9qahPytjceC2SWAvqpnX5j6MoIaM2gYIawhsPRYCgiqj+HssNrp8BaCsPm0FJGlx2VBgrAy81gkHEha2+xoe4NJu+jIIchhRgpwQhr9jzhsmgVZHrPg4IgRc4sZUrqySMOQZU48ae0QlQ1tpEfxU63FT86ZRRCj4iC/RQ6IgMtR6shVtq8YGhAr9xXX85DCNgJ3S2mRYQ/Pe8VmGTiaQN+EeoYeQoPSd28wvrdGdQcZqRj3p9jzaJu6VAxPvQwFDbsA/AL2IgTbMHicp/bWB53hmcTYGpmyEeL7KzrSSw+DJWQgyCZxp340HeSBlA5+8xN96TNqp/UlP9AgMBAAECggEBAKcewvvht1YbtgtFAa93uY2mrar5js1R9RzCcvEq71KL9jSSLofjZxwFDjtNBSwCCwKKVOABZBBcDwQqa2cdaV+uhui4yHqp20n4VZq7R/YkazS/1XVU9AOKRtRQb8956RjbY/44Kyga1G/BqDBFbpK4/E6TrL2RlEMSaiL/ihqKDVWcvrVKl1LHpzPGZZPpqgkV/sO63eUhp8k/idqdh0t/FF4h7eFRnhrQPldrHIVmGlY5qiREtYSqDrlize5XLEvPL8kmoprzhQckS/GTu0jcSMCHqTgJxV/vo3T3h8VxdVsGeFkvzYctOWsG1aJxAh5EapHyAllF7jYPP1kmJhECgYEA9M6YuP1LlFHXq2gwYWvde6LisC/GSRwfDUID4ug7UYa4d7VsU3sRXpCO35UvsRP+qeuFJaeG/elXyvu4ien/X0ZN6swpO7aHImkjEaPKVsEyBhDwyDIo8eRBEA/nRC8S8N/rwq9v9FW9xjKcXzkRUEzYXRIZodPLVxbxEMNvRA8CgYEAwr6iHHZlJmyU9GfAREqZh6Ej6LXmYPXf/P1IYnTmNHTK40flcQO66hVpSm40Jp9lR5FaziBu8sSZl7VM0E/4DXuXI9sJNyj/oJiqe9Cy31hmfYddpau5It8CzOuuD9LHUxamm1ff7oGF9gJYSIiQNo4m7zAgacQdgEbzqAJy6zMCgYEAgjGVm1xqY5kbH/erM0SQZ/56nMTrqVohUYW4V2a2lWcWl+OOlt8un6I1uca3tG8z0t6E0z/DVSpSHIu+2untfJgBPEwT6cPrHmvX0LvREk5w8jT0OSQkDnEcARTUI7j9U8liswlmewVWhh/IdgP9pz0XHHhR1XS0Ab8buZSWihUCgYBpS7/FWfKpCu6jE3HuutM2dVdAHRIafPcGCEDaE1DhLgEBUL9rEtQ+eyM5/kouU+LkBmCtsPWsR8awM3P78jExwRBgb68U5xMIV2NenOIKRvbrioSE7kuk4iXWfNvZbGMfttCQj7vhbqDq+iuX1Fb9WHEOxNvRtBsrX7GgtupB5wKBgCDGzDWSiv9ru2tCJffHxfw57jEz8QPNOP4IYu4QgcFLi95+5xwyhl0znct2suNcVLFKgd4/P8jiq8nSq1AtyiusSVMoq+hqrbhb2r8oXbRPMHL5AuBMSh2PasGbbnXMh6y4a93fFUTVWBOp6wy2ATDsTE1ixpGwWgvGRC/bAAtS";
        String alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmVhgA00rm1/1erk2maXQRW9FOm1+1XY9Cb3f9pMYoYmPztrfWlj9J7UvWwMOfdaUoKVFBCRG4kNY+tkVb1W/ledAXQ3jdUFFrv7/3WsRKhGPdifmsb//fICl297ptMeZyMUe6c/ntmObvXrAOMdQLZPvh//w6QH7o7IRFikzFlWLWmP4vBCE2v3So46xPMccWqW8m9Jp05ZqtkNw0IPg9JFwPg+4ThRZWt3iPJbZUGe9CICjxppuVBq8uAxnTsjPV0L9Givi4enmtHbynOmBtDCRYL2eCIFSijFPOr0hmK33LgB+7dduOI+r9mb3yYHGX4bPCrNxNkrspKNj96XpHQIDAQAB";
        String format = "json";
        String charset = "utf-8";
        String signType = "RSA2";
        return new AlipayClientConfig(gateway, appid, appPrivateKey, format, charset, alipayPublicKey, signType);
    }
}
