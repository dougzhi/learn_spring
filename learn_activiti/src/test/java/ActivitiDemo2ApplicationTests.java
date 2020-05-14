import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author dong
 * @date 2020/5/14 23:43
 * @desc
 */
@SpringBootTest
public class ActivitiDemo2ApplicationTests {

    @Resource
    RepositoryService repositoryService;

    @Resource
    RuntimeService runtimeService;

    @Resource
    TaskService taskService;

    @Test
    public void contextLoads() {
        System.out.println("Number of process definitions : "
                + repositoryService.createProcessDefinitionQuery().count());
        System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
        runtimeService.startProcessInstanceByKey("holiday");
        System.out.println("Number of tasks after process start: " + taskService.createTaskQuery().count());
    }

}