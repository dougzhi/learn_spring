package com.dongz.codeutils.entitys.enums;

import com.dongz.codeutils.entitys.Settings;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author dong
 * @date 2020/2/14 23:12
 * @desc
 */
@Getter
@AllArgsConstructor
public enum TemplateEnum {
    Table("DemoTable.java","/%s/src/main/java/%s/entities/"),
    TableVO("DemoTableVO.java","/%s/src/main/java/%s/entities/vos/"),
    Service("DemoService.java","/%s/src/main/java/%s/services/"),
    Controller("DemoController.java","/%s/src/main/java/%s/controllers/"),
    MainApplication("Application.java","/%s/src/main/java/%s/"),
    Pom("pom.xml","/%s/"),
    Application("application.yml","/%s/src/main/resources/");

    private String name;
    private String path;

    public String getDemoPath() {
        return "/templates" + this.path.replaceAll("%s", "").replaceAll("//", "/") + name;
    }

    public String getOutPath(Settings settings) {
        String path;
        if (this == TemplateEnum.Pom || this == TemplateEnum.Application) {
            path = String.format(this.path, settings.getProject());
        } else {
            path = String.format(this.path, settings.getProject(), settings.getPathAll());
        }
        return settings.getOutPath() + path ;
    }
}
