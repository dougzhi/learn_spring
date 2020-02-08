欢迎你：${username}

<#if type == 1>
    type = 1
    <#elseif type == 2>
    type = 2
    <#else>
    type = 3
</#if>

<#list [1,2,3] as item>
    ${item}
</#list>

<#list list as item>
    ${item_index} ${item}
    <#if item_has_next>
        <#else>
        over
        <#break>
    </#if>
</#list>

<#-- assign 指定一个值到根节点 -->
<#assign username = "dong">
<#include "${include}">

首字母大写： ${username?cap_first}
大写： ${username?upper_case}
长度： ${username?length}
截取： ${username?substring(2)}
数量： ${list?size}

