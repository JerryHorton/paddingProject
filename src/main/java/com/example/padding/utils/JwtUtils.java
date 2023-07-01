package com.example.padding.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @version 1.0
 * @Date 2023/7/1 11:40
 * @Description
 * @Author Sxy
 */

public class JwtUtils {

    private static final String secret = "yuzhouwudiheiqi";//密钥
    private static final String issuer = "Server";//发布者
    private static final String subject = "LoginToken";//主题
    private static final String audience = "Client";//签名的观众,谁接受签名
    private static final Algorithm algorithm;
    private static final JWTVerifier jwtVerifier;

    static {
        algorithm = Algorithm.HMAC256(secret);
        jwtVerifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    /**
     * 创建token值
     *
     * @param userId
     * @param username
     * @param seconds
     * @return
     */
    public static String createToken(Long userId, String username, long seconds) {
        Date nowDate = new Date();
        Date expireDate = new Date(nowDate.getTime() + seconds * 1000);// 计算失效时间

        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");
        map.put("typ", "JWT");

        Algorithm algorithm = Algorithm.HMAC256(secret);
        String token = JWT.create()
                .withHeader(map)
                // 自定义
                .withClaim("userId", userId)// 载荷 Payload
                .withClaim("username", username)// 载荷 Payload
                .withIssuer(issuer)// 签名是有谁生成 例如 服务器
                .withSubject(subject)// 签名的主题
                .withNotBefore(nowDate)//定义在什么时间之前，该jwt都是不可用的
                .withAudience(audience)// 签名的观众 也可以理解谁接受签名的
                .withIssuedAt(nowDate) // 生成签名的时间
                .withExpiresAt(expireDate)// 签名过期的时间
                .sign(algorithm);//签名 Signature
        return token;
    }

    /**
     * 校验token值
     *
     * @param token
     * @return
     */
    public static DecodedJWT verifyToken(String token) {
        return jwtVerifier.verify(token);
    }

}