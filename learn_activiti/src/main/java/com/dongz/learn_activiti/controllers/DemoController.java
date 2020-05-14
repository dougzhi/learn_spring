package com.dongz.learn_activiti.controllers;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author dong
 * @date 2020/5/15 00:02
 * @desc
 */
@RestController
@RequestMapping("api")
public class DemoController {
    @Resource
    RepositoryService repositoryService;

    @Resource
    RuntimeService runtimeService;

    @Resource
    TaskService taskService;

    @RequestMapping("start")
    public void test() {
        System.out.println("Number of process definitions : "
                + repositoryService.createProcessDefinitionQuery().count());
        System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
        runtimeService.startProcessInstanceByKey("oneTaskProcess");
        System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
    }

}
