package com.dongz.hrm.common.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.crazycake.shiro.AuthCachePrincipal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dong
 * @date 2020/2/23 22:35
 * @desc 用户安全数据
 */
@Data
@AllArgsConstructor
public class Profile implements AuthCachePrincipal,Serializable {
    private static final long serialVersionUID = 8913745173701454838L;
    private Long id;
    private String username;
    private String mobile;
    private Long companyId;
    private String company;
    private Roles roles;

    @Override
    public String getAuthCacheKey() {
        return this.mobile;
    }

    @Data
    public static class Roles implements Serializable{
        private static final long serialVersionUID = -7447543349720068403L;
        private List<String> menus = new ArrayList<>();
        private List<String> points = new ArrayList<>();
        private List<String> apis = new ArrayList<>();
    }
}
