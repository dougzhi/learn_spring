package com.dongz.codeutils.entitys.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.dongz.codeutils.controllers.BaseController.settings;

/**
 * @author dong
 * @date 2020/2/14 23:12
 * @desc
 */
@Getter
@AllArgsConstructor
public enum TemplateEnum {
    Table("DemoTable.java","/%s/src/main/java/%s/entities/",false),
    BaseTable("BaseEntity.java","/%s/src/main/java/%s/entities/",true),
    Result("Result.java","/%s/src/main/java/%s/entities/",true),
    PageResult("PageResult.java","/%s/src/main/java/%s/entities/",true),
    ResultCode("ResultCode.java","/%s/src/main/java/%s/entities/",true),
    TableVO("DemoTableVO.java","/%s/src/main/java/%s/entities/vos/",false),
    Service("DemoService.java","/%s/src/main/java/%s/services/",false),
    BaseService("DemoService.java","/%s/src/main/java/%s/services/",true),
    Controller("DemoController.java","/%s/src/main/java/%s/controllers/",false),
    BaseController("BaseController.java","/%s/src/main/java/%s/controllers/",true),
    IdWorker("IdWorker.java","/%s/src/main/java/%s/utils/",true),
    MainApplication("Application.java","/%s/src/main/java/%s/",true),
    Pom("pom.xml","/%s/",true),
    Application("application.yml","/%s/src/main/resources/",true);

    private String name;
    private String path;
    private boolean isBase;

    public String getDemoPath() {
        return "/templates" + this.path.replaceAll("%s", "").replaceAll("//", "/") + name;
    }

    public String getOutPath() {
        String path;
        if (this == TemplateEnum.Pom || this == TemplateEnum.Application) {
            path = String.format(this.path, settings.getProject());
        } else {
            path = String.format(this.path, settings.getProject(), settings.getPathAll());
        }
        return settings.getOutPath() + path ;
    }
}
