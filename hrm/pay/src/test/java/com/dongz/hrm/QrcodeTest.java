package com.dongz.hrm;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

/**
 * @author dong
 * @date 2020/2/17 00:19
 * @desc
 */
public class QrcodeTest {

    @Test
    public void test() throws WriterException, IOException {
        // 1， 二维码中的一些信息
        String content = "https://dongz.ngrok2.xiaomiqiu.cn?id=915301110863755200";
        // 2， 通过zxing生成二维码（保存到本地， 通过data url的形式体现）

        // 3， 创建QrCodeWriter对象
        QRCodeWriter writer = new QRCodeWriter();
        // 4， 基本配置
        /**
         * 1, 二维码信息
         * 2，图片类型
         * 3，宽度
         * 4，长度
         */
        BitMatrix encode = writer.encode(content, BarcodeFormat.QR_CODE, 200, 200);
        // 5， 保存二维码到本地
        Path path = new File("/Users/dong/Desktop/test/test.png").toPath();
        MatrixToImageWriter.writeToPath(encode, "png", path);
    }
}
