package com.dongz.hrm.common.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Date;
import java.util.Map;

/**
 * @author dong
 * @date 2020/2/22 22:19
 * @desc
 */
@Data
@ConfigurationProperties("jwt")
public class JwtUtils {

    /**
     * 签名私钥
     */
    private String key;
    /**
     * 签名失效时间
     */
    private Long ttl;

    /**
     * 设置认证token
     * @return
     * @param id
     * @param name
     */
    public String createJwt(String id, String name, Map<String,Object> map) {
        // 1，设置失效时间
        long currentTimeMillis = System.currentTimeMillis();
        long exp = currentTimeMillis + ttl;
        JwtBuilder jwtBuilder = Jwts.builder().setId(id).setSubject(name)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, key)
                .setClaims(map)
                .setExpiration(new Date(exp));
        return jwtBuilder.compact() ;
    }

    public Claims parseJwt(String token) {
        return Jwts.parser().setSigningKey(key).parseClaimsJws(token).getBody();
    }
}
