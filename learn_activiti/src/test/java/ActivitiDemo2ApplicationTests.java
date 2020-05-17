import com.dongz.learn_activiti.config.ApplicationConfiguration;
import com.dongz.learn_activiti.util.SecurityUtil;
import org.activiti.api.process.model.ProcessDefinition;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.model.Task;
import org.activiti.api.task.model.builders.TaskPayloadBuilder;
import org.activiti.api.task.runtime.TaskRuntime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author dong
 * @date 2020/5/14 23:43
 * @desc
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfiguration.class)
public class ActivitiDemo2ApplicationTests {

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskRuntime taskRuntime;

    @Autowired
    private SecurityUtil securityUtil;

    /**
     * 查看流程定义 */
    @Test
    public void contextLoads() {
        securityUtil.logInAs("system");
        Page<ProcessDefinition> processDefinitionPage = processRuntime.processDefinitions(Pageable.of(0, 10));
        System.out.println(" 可 用 的 流 程 定 义 数 量 : " +
                processDefinitionPage.getTotalItems());
        for (org.activiti.api.process.model.ProcessDefinition pd :
                processDefinitionPage.getContent()) {
            System.out.println("流程定义:" + pd); }
    }

    /**
     * 启动流程实例
     */ @Test
    public void testStartProcess() {
        securityUtil.logInAs("system");
        ProcessInstance pi =
                processRuntime.start(ProcessPayloadBuilder.start().withProcessDefinitionKey("myProcess").build());
                        System.out.println("流程实例ID:" + pi.getId());
     }

    /**
     * 查询任务，并完成自己的任务
     */
    @Test
    public void testTask() {
        securityUtil.logInAs("ryandawsonuk");
        Page<Task> taskPage=taskRuntime.tasks(Pageable.of(0,10));
        if (taskPage.getTotalItems()>0){
            for (Task task:taskPage.getContent()){
                taskRuntime.claim(TaskPayloadBuilder.claim().withTaskId(task.getId()).build()); System.out.println("任 务: "+task);
                taskRuntime.complete(TaskPayloadBuilder.complete().withTaskId(task.getId()).build() );
            } }
        Page<Task> taskPage2=taskRuntime.tasks(Pageable.of(0,10));
        if (taskPage2.getTotalItems()>0){ System.out.println("任 务: "+taskPage2.getContent());
        }
    }

}