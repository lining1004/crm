package com.briup.crmuitl.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;

import java.util.Map;

/*
 * 封装jwt工具类
 */
public class JwtUtil {
    /*
     * jwt密钥 （可自定义指定）
     */
    private static final String SECRET = "jwt_secret";

    /*
     * 该方法生成jwt字符串
     * @param userId
     * @param info, map的value只能存放的值的类型为：Map,List,Boolean,Integer,Long,Double,String
     * and Date
     */
    public static String sign(String userId, Map<String, Object> info) {
        try {
            // 通过字符串产生真正的密钥
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            return JWT.create()
                    // 将userId保存到token里面
                    .withAudience(userId)
                    // 存放自定义数据
                    .withClaim("info", info)
                    // token 的密钥
                    .sign(algorithm);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 该方法解析token
     * 根据token获取userId
     * @Param token
     */

    public static String getUserId(String token) {
        try {
            return JWT
                    .decode(token)
                    .getAudience()
                    .get(0);
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }
    /*
     * 该方法解析token
     * 根据token获取自定义数据info
     */

    public static Map<String, Object> getInfo(String token) {
        try {
            return JWT
                    .decode(token)
                    .getClaim("info")
                    .asMap();
        } catch (JWTDecodeException e) {
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 该方法校验token
     * @param token
     */
    public static boolean checkSign(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .build();
            verifier.verify(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
