package com.dongz.javalearn.boot.controllers;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.dongz.javalearn.boot.alipay.AlipayClientConfig;
import com.dongz.javalearn.boot.services.AlipayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author dong
 * @date 2020/2/19 13:33
 * @desc
 */
@Controller
@RequestMapping("/")
public class AlipayController {

    @Autowired
    private AlipayService service;

    @RequestMapping("/")
    public String test(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");
        String agent = header.toLowerCase();
        if (agent.indexOf("micromessenger")>0) {
            return "wechat";
        }else if(agent.indexOf("alipayclient")>0){
            return "alipay";
        }
        return null;
    }

    @PostMapping("pay")
    public void pay(HttpServletResponse response) throws IOException {
        AlipayClientConfig config = service.getConfig();
        //获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(config.getGatewayUrl(), config.getAppId(), config.getAppPrivateKey(), config.getFormat(), config.getCharset(), config.getAlipayPublicKey(), config.getSignType());
        //创建API对应的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model = new AlipayTradeWapPayModel();

        //设置支付宝支付的订单号
        model.setOutTradeNo(System.currentTimeMillis()+"");
        //商品名称
        model.setSubject("商品名称");
        //支付金额
        model.setTotalAmount("0.01");
        //商品描述
        model.setBody("这个是一个商品支付");
        //超时时间
        model.setTimeoutExpress("30m");
        //商品Code
        model.setProductCode("000");
        //设置参数
        alipayRequest.setBizModel(model);
        alipayRequest.setNotifyUrl("https://dongz.ngrok2.xiaomiqiu.cn/notifyUrl");
        alipayRequest.setReturnUrl("https://dongz.ngrok2.xiaomiqiu.cn/returnUrl");
        String form="";
        try {
            //调用SDK生成表单
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        System.out.println(form);
        response.setContentType("text/html;charset=" + config.getCharset());
        //直接将完整的表单html输出到页面
        response.getWriter().write(form);
        response.getWriter().flush();
        response.getWriter().close();
    }

    @GetMapping("/returnUrl")
    public String returnUrl() {
        return "returnUrl";
    }

    @GetMapping("/notifyUrl")
    public String notifyUrl() {
        return "notifyUrl";
    }
}
