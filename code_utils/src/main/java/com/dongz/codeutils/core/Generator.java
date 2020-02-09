package com.dongz.codeutils.core;

import com.dongz.codeutils.utils.FileUtils;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * @author dong
 * @date 2020/2/9 13:43
 * @desc 代码生成器核心处理类
 *       使用 freemakerw完成文件生成
 *       数据模型 + 模板
 */
public class Generator {
    /**
     * 模板路劲
     */
    private String templatePath;
    /**
     * 代码生成器路径
     */
    private String outPath;
    private Configuration cfg;

    public Generator(String templatePath, String outPath) throws IOException {
        this.templatePath = templatePath;
        this.outPath = outPath;
        //实例化对象
        cfg = new Configuration();
        //指定模板加载器
        FileTemplateLoader ftl = new FileTemplateLoader(new File(templatePath));
        cfg.setTemplateLoader(ftl);
    }

    /**
     * 代码生成
     *  1， 扫描所有模板
     *  2， 对每个模板进行文件生成
     */
    public void scanAndGenerator(Map<String,Object> dataModel) throws IOException {
        // 找到模板路径下的所有文件
        List<File> files = FileUtils.searchAllFile(new File(templatePath));
        // 对每个文件进行代码生成
        files.forEach(file -> {
            try {
                executeGenerator(dataModel, file);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * 对模板进行文件生成
     * @param dataModel 模板数据
     * @param file 模板文件
     */
    private void executeGenerator(Map<String, Object> dataModel, File file) throws IOException, TemplateException {
        // 文件路径处理
        String templateFileName = file.getAbsolutePath().replace(templatePath, "");
        String outFileName = processTemplateString(templateFileName, dataModel);
        // 读取模板文件
        Template template = cfg.getTemplate(templateFileName);
        // 指定生成文件字符集
        template.setEncoding("utf-8");
        // 创建文件
        File mkdir = FileUtils.mkdir(outPath, outFileName);
        // 模板处理（文件生成）
        FileWriter fw = new FileWriter(mkdir);

        template.process(dataModel, fw);
        fw.close();
    }

    private String processTemplateString(String templatePath, Map<String,Object> dataModel) throws IOException, TemplateException {
        StringWriter out = new StringWriter();
        Template template = new Template("ts", new StringReader(templatePath), cfg);
        template.process(dataModel, out);
        return out.toString();
    }
}
