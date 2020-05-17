package com.dongz.learn_activiti.controllers;

import com.dongz.learn_activiti.util.SecurityUtil;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.model.payloads.StartProcessPayload;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author dong
 * @date 2020/5/15 00:02
 * @desc
 */
@RestController
@RequestMapping("api")
public class DemoController {
    Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 查看流程定义
     */
    @RequestMapping("contextLoads")
    public void contextLoads() {
        securityUtil.logInAs("system");
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        System.out.println(" 可 用 的 流 程 定 义 数 量 : " +
                processDefinitionPage.getTotalItems());
        for (ProcessDefinition pd :
                processDefinitionPage.getContent()) {
            System.out.println("流程定义:" + pd);
        }
    }

    /**
     * 启动流程实例
     *
     * @param myProcess
     */
    @RequestMapping("testStartProcess")
    public void testStartProcess(String myProcess) {
        securityUtil.logInAs("system");
        StartProcessPayload build = ProcessPayloadBuilder.start().withProcessDefinitionKey(myProcess).build();
        ProcessInstance pi =
                processRuntime.start(build);
        System.out.println("流程实例ID:" + pi.getId());
    }

    /**
     * 查询任务，并完成自己的任务
     */
    @RequestMapping("testTask")
    public void testTask() {
        securityUtil.logInAs("ryandawsonuk");
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10));
        if (taskPage.getTotalItems() > 0) {
            for (Task task : taskPage.getContent()) {
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
                System.out.println("任 务: " + task);
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
            }
        }
        Page<Task> taskPage2 = taskRuntime.tasks(Pageable.of(0, 10));
        if (taskPage2.getTotalItems() > 0) {
            System.out.println("任 务: " + taskPage2.getContent());
        }
    }

    @RequestMapping(value = "/hello")
    public void hello() {
        //首先，取出项目中的最多 10 个流程定义
        Page<ProcessDefinition> processDefinitionPage =
                processRuntime.processDefinitions(Pageable.of(0, 10));

        if (processDefinitionPage.getTotalItems() > 0) {
            //然后，对取出的流程进行启动
            for (ProcessDefinition definition : processDefinitionPage.getContent()) {
                logger.info("流程定义信息:" + definition);
                processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionId(definition.getId()).build());
            }
        }
        //完成 流程启动后， 由于当前项目中 只有 other.bpmn 一个流程，且该流程在 设计时，已分 配给 activitiTeam 组
        //因此我们登录一个 activitiTea m 组成员,该账号信息会被设置到 security 上下文中，activit i 会对其 信息进行读取 //获取当前用户任务，最多 10 个
        Page<Task> taskPage = taskRuntime.tasks(Pageable.of(0, 10));
        //由于目前只有一个流程，两个任务，我们尝试一下完成一个，看看会发生什么变化
        if (taskPage.getTotalItems() > 0) {
            for (Task task : taskPage.getContent()) {
                logger.info("任务信息:" + task);
                //注意，完成任务前必须先声明
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build());
                //完成任务
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build());
            }
        }

        //上一轮任务完成，再看一下，现在流程是否走到了 second?
        Page<Task> taskPage2 = taskRuntime.tasks(Pageable.of(0, 10));
        if (taskPage2.getTotalItems() > 0) {
            logger.info("任务信息:" + taskPage2.getContent());
        }
    }
}
