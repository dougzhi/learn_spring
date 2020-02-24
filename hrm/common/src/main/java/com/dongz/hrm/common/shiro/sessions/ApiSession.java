package com.dongz.hrm.common.shiro.sessions;

import lombok.Data;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dong
 * @date 2020/2/24 14:57
 * @desc 获取所有api，权限信息
 */
@Component
public class ApiSession {

    public static List<String> topApiList;
    public static Map<String, List<Auth>> childrenApis = new HashMap<>();

    static {
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

    }

    private static Auth getAuth(String basePath,Method item) {
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
        return auth;
    }

    @Data
    public static class Auth implements Serializable {
        private static final long serialVersionUID = 8719853788317890588L;
        private String methodType;
        private String authUniqueMark;
        private String authUrl;
        private String authName;
    }
}
