package com.app.Utils;

import com.app.bean.UserToken;
import com.app.config.CommonConstants;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.jose4j.keys.HmacKey;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.text.SimpleDateFormat;

@Service
public class TokenUtil {
    // token时效：24小时
    public static final long EXPIRE = 60*24*10000;
    // 签名哈希的密钥，对于不同的加密算法来说含义不同
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHOsdadasdasfdssfeweee";


    /**
     * 生成token
     * @param userToken
     * @param expire
     * @return
     * @throws Exception
     */
    public static String generateToken(UserToken userToken, int expire) throws Exception {
        JwtClaims claims = new JwtClaims();
        claims.setSubject(userToken.getUserName());
        claims.setClaim(CommonConstants.userId,userToken.getUserId());
        claims.setClaim(CommonConstants.privilegeId, userToken.getPrivilegeId());
        claims.setClaim(CommonConstants.uuidToken, userToken.getUuidToken());
        claims.setClaim(CommonConstants.temp2, userToken.getTemp2());
        claims.setExpirationTimeMinutesInTheFuture(expire == 0 ? EXPIRE : expire);

        Key key = new HmacKey(CommonConstants.JWT_PRIVATE_KEY.getBytes("UTF-8"));

        JsonWebSignature jws = new JsonWebSignature();
        jws.setPayload(claims.toJson());
        jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.HMAC_SHA256);
        jws.setKey(key);
        jws.setDoKeyValidation(false); // relaxes the key length requirement

        //签名
        String token = jws.getCompactSerialization();
        return token;
    }

    /**
     * 解析token
     * @param
     * @return
     * @throws Exception
     */
    public static UserToken getInfoFromToken(String token) throws UnsupportedEncodingException, InvalidJwtException, MalformedClaimException {

        if (token == null) {
            return null;
        }

        Key key = new HmacKey(CommonConstants.JWT_PRIVATE_KEY.getBytes("UTF-8"));

        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setVerificationKey(key)
                .setRelaxVerificationKeyValidation() // relaxes key length requirement
                .build();


        JwtClaims processedClaims = jwtConsumer.processToClaims(token);
        return new UserToken(
                processedClaims.getSubject(),
                processedClaims.getClaimValue(CommonConstants.userId).toString(),
                processedClaims.getClaimValue(CommonConstants.privilegeId).toString(),
                processedClaims.getClaimValue(CommonConstants.uuidToken).toString(),
                processedClaims.getClaimValue(CommonConstants.temp2).toString());
    }


    /**
     *
     * 解析token，查看token是否过期
     * false 表示未过期，true表示过期
     *
     */
    public static boolean checkToken(String token) throws UnsupportedEncodingException {

        //如果token为空，返回true，表示token过期，请充重写登录
        if (token == null) {
            return true;
        }

        //创建时间类对象
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //创建key对象
        Key key = new HmacKey(CommonConstants.JWT_PRIVATE_KEY.getBytes("UTF-8"));
        //解析api
        JwtConsumer jwtConsumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setAllowedClockSkewInSeconds(30)
                .setRequireSubject()
                .setVerificationKey(key)
                .setRelaxVerificationKeyValidation() // relaxes key length requirement
                .build();

        try{
            //解析api
            JwtClaims processedClaims = jwtConsumer.processToClaims(token);
            //如果过期日期小于等于当前日期，则以及过期
            System.out.println(sdf.format(processedClaims.getExpirationTime().getValueInMillis()));
            if(processedClaims.getExpirationTime().getValueInMillis() >= System.currentTimeMillis() ){
                return false;
            }
        }catch (Exception e){
            return true;
        }

        return false;

    }


    public static void main(String[] agars) throws Exception {
//        UserToken userToken=new UserToken("贾靖渝","lbXAxIjoiMSIsInRlbXAyIjoiMTExMTE","12","1","1");
//        String token = generateToken(userToken, 0);
//        System.out.println(token);
//        UserToken infoFromToken = getInfoFromToken(token);
//        System.out.println(infoFromToken);
//        System.out.println(checkToken(token));

//        System.out.println(checkToken("eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiLotL7pnZbmuJ0iLCJ1c2VySWQiOiJsYlhBeElqb2lNU0lzSW5SbGJYQXlJam9pTVRFeE1URSIsInByaXZpbGVnZUlkIjoiMTIiLCJ1dWlkVG9rZW4iOiIxIiwidGVtcDIiOiIxIiwiZXhwIjoxNjY5MTE4MTQ3fQ.enYqvSU3HWXFhR1XbBbrvDAJFrk8Tezg4uO7-ptQTBI"));


    }


}
