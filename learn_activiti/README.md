# activiti7数据库表命名规范 （25张表）
-   ACT_EVT_LOG 日志信息
-   ACT_PROCDEF_INFO    流程信息
-   ACT_RE_*（3张）:   repository
```
    这个前缀的表包含了流程定义和流程静态资源（图片，规则。。。）
```
-   ACT_RU_*（10张）:   runtime
```
    这些是运行时的表，包含流程实例，任务，变量，异步任务等运行中的数据。
    activiti只在流程实例执行过程中保存这些数据，在流程结束时就会删除这些记录。
    这样运行时表可以一直很小，速度很快
```
-   ACT_HI_*（8张）:   history
```
    这些表包含历史数据，比如历史流程实例，变量，任务等
```
-   ACT_GE_*（2张）:   general
```
    通用数据，用于不同场景下
```

# service总览
-   RepositoryService
    activiti的资源管理类
    
-   RuntimeService
    activiti的流程运行管理类

-   TaskService
    activiti的任务管理类

-   HistoryService
    activiti的历史管理类

-   ManagerService
    activiti的引擎管理类
    
    
## 启动流程实例
-   前提是先可以完成流程定义的部署工作
-   背后影响的表
    -   act_hi_actinst          已完成的活动信息
    -   act_hi_identitylicnk    参与者信息
    -   act_hi_procinst         流程实例
    -   act_hi_taskinst         任务实例
    -   act_ru_excution         执行表
    -   act_ru_identitylink     参与者信息
    -   act_ru_task             任务
