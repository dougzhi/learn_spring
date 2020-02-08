package com.dongz.codeutils.test;

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @author dong
 * @date 2020/2/7 19:15
 * @desc
 */
public class Freemarker01 {

    /**
     * 文件模板
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void test01() throws IOException, TemplateException {
        //1, 创建freemarker的配置类
        Configuration cfg = new Configuration();

        //2, 指定模板加载器：将模板存入缓存中
        FileTemplateLoader ftl = new FileTemplateLoader(new File("templates"));
        cfg.setTemplateLoader(ftl);

        //3, 获取模板
        Template template = cfg.getTemplate("template01.ftl");

        //4, 构造数据模型
        Map<String, Object> dateModel = new HashMap<>();
        dateModel.put("username", "dongzhi");
        dateModel.put("type", 2);

        String[] arr = {"1", "2", "3", "4", "5"};
        dateModel.put("list", Arrays.asList(arr));

        dateModel.put("include", "template02.ftl");

        //5, 文件输出
        // 参数一： 数据模型
        // 参数二： writer（FileWriter:文件输出， PrintWriter:控制台输出）
//        template.process(dateModel, new FileWriter(new File("/Users/dong/IdeaProjects/javaLearn/code_utils/files/test01.txt")));
        template.process(dateModel, new PrintWriter(System.out));
    }

    /**
     * 字符串模板
     */
    @Test
    public void test02() throws IOException, TemplateException {
        //1, 创建freemarker的配置类
        Configuration cfg = new Configuration();

        //2, 指定模板加载器
        cfg.setTemplateLoader(new StringTemplateLoader());

        //3, 创建字符串模板
        String stringTemplate = "你好，${username}";
        Template template = new Template("template01", stringTemplate, cfg);


        //4, 构造数据模型
        Map<String, Object> dateModel = new HashMap<>();
        dateModel.put("username", "dongzhi");

        //5, 文件输出
        // 参数一： 数据模型
        // 参数二： writer（FileWriter:文件输出， PrintWriter:控制台输出）
//        template.process(dateModel, new FileWriter(new File("/Users/dong/IdeaProjects/javaLearn/code_utils/files/test01.txt")));
        template.process(dateModel, new PrintWriter(System.out));
    }

}
