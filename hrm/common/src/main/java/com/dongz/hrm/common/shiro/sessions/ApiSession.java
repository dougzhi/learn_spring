package com.dongz.hrm.common.shiro.sessions;

import com.dongz.hrm.common.controllers.BaseController;
import lombok.Data;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dong
 * @date 2020/2/24 14:57
 * @desc 获取所有api，权限信息
 */
@Component
public class ApiSession implements ApplicationContextAware {

    public static List<String> topApiList;
    public static Map<String, List<Map<String, Object>>> childrenApis;
    public static Map<String, Map<String, Object>> apiMaps;

    /**
     * 打成jar后无法启动
     * @return
     */
    /*static {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forPackage("com.dongz.hrm")).setScanners(new MethodAnnotationsScanner()));

        //扫描包内带有@RequiresPermissions注解的所有方法集合
        Set<Method> methods = reflections.getMethodsAnnotatedWith(RequiresPermissions.class);

        //同类下的方法集中处理
        Map<String, List<Method>> collect = methods.stream().collect(Collectors.groupingBy(item -> item.getDeclaringClass().getAnnotation(RequestMapping.class).value()[0]));

        topApiList = collect.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());

        collect.forEach((key, value) -> {
            List<Auth> list = value.stream().map(item -> getAuth(key, item)).collect(Collectors.toList());
            childrenApis.put(key, list);
        });

    }*/

    private static Map<String, Object> getAuth(String basePath, Method item) {
        //用于保存方法的请求类型
        String methodType = "";

        //获取方法上的@PutMapping,@GetMapping,@PostMapping,@DeleteMapping注解的值，作为请求路径,并区分请求方式
        String authUrl = "";
        String authName = "";
        if (item.getAnnotation(PutMapping.class) != null) {
            methodType = "put";
            PutMapping annotation = item.getAnnotation(PutMapping.class);
            authName = annotation.name();
            if (annotation.value().length > 0) {
                authUrl = annotation.value()[0];
            }
        } else if (item.getAnnotation(GetMapping.class) != null) {
            methodType = "get";
            GetMapping annotation = item.getAnnotation(GetMapping.class);
            authName = annotation.name();
            if (annotation.value().length > 0) {
                authUrl = annotation.value()[0];
            }
        } else if (item.getAnnotation(PostMapping.class) != null) {
            methodType = "post";
            PostMapping annotation = item.getAnnotation(PostMapping.class);
            authName = annotation.name();
            if (item.getAnnotation(PostMapping.class).value().length > 0) {
                authUrl = annotation.value()[0];
            }
        } else if (item.getAnnotation(DeleteMapping.class) != null) {
            methodType = "delete";
            DeleteMapping annotation = item.getAnnotation(DeleteMapping.class);
            authName = annotation.name();
            if (item.getAnnotation(DeleteMapping.class).value().length > 0) {
                authUrl = annotation.value()[0];
            }
        }else if (item.getAnnotation(RequestMapping.class) != null) {
            RequestMapping annotation = item.getAnnotation(RequestMapping.class);
            authName = annotation.name();
            methodType = Arrays.toString(annotation.method());
            if (item.getAnnotation(RequestMapping.class).value().length > 0) {
                authUrl = annotation.value()[0];
            }
        }

        //使用Auth对象来保存值
        Auth auth = new Auth();
        auth.setMethodType(methodType);
        auth.setAuthUniqueMark(item.getAnnotation(RequiresPermissions.class).value()[0]);
        auth.setAuthName(authName);
        String fullName = ("/" + basePath + "/" + authUrl).replace("//", "/");
        auth.setAuthUrl(fullName);
        return auth.getDataBaseMap();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beansWithAnnotation = applicationContext.getBeansWithAnnotation(RestController.class);
        // 获取所有controller
        List<? extends Class<?>> controllerList = beansWithAnnotation.values().stream()
                .map(Object::getClass)
                .filter(BaseController.class::isAssignableFrom)
                //排除CGLIB代理类
                .map(item -> item.getSuperclass().equals(BaseController.class)?item: item.getSuperclass())
                .collect(Collectors.toList());
        //避免类 requestMapping 相同
        Map<String, ? extends List<? extends Class<?>>> classMap = controllerList.stream().collect(Collectors.groupingBy(item -> item.getDeclaredAnnotation(RequestMapping.class).value()[0]));
        // 分组合并
        Map<String, List<Method>> collect = classMap.entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                item -> item.getValue().stream().map(e -> new ArrayList<>(Arrays.asList(e.getDeclaredMethods())))
                        .reduce((a, b) -> {
                            a.addAll(b);
                            return a;
                        }).get().stream().filter(e->e.isAnnotationPresent(RequiresPermissions.class))
                .collect(Collectors.toList())
        ));
        // 剔除size（）==0 ,无权限注解方法的类
        childrenApis = collect.entrySet().stream().filter(item -> item.getValue().size() > 0)
        // 封装类
        .collect(Collectors.toMap(Map.Entry::getKey, item -> item.getValue().stream().map(e -> getAuth(item.getKey(), e)).collect(Collectors.toList())));
        topApiList = childrenApis.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        // map
        apiMaps = childrenApis.entrySet().stream().reduce((a, b) -> {
            a.getValue().addAll(b.getValue());
            return a;
        }).get().getValue().stream().collect(Collectors.toMap(item -> (String)item.get("authUrl"), item -> item));
    }

    @Data
    public static class Auth implements Serializable {
        private static final long serialVersionUID = 8719853788317890588L;
        private String methodType;
        private String authUniqueMark;
        private String authUrl;
        private String authName;

        public Map<String, Object> getDataBaseMap(){
            Map<String, Object> map = new HashMap<>();
            Field[] declaredFields = Auth.class.getDeclaredFields();
            for (Field field : declaredFields) {
                field.setAccessible(true);
                try{
                    map.put(field.getName(), field.get(this));
                }catch (Exception ignored){}
            }
            return map;
        }
    }
}
