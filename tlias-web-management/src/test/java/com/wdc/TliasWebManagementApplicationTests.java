package com.wdc;

import com.wdc.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//@SpringBootTest
class TliasWebManagementApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void genJwt(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","Tom");
        String jwt = Jwts.builder()
                .setClaims(claims) //自定义内容(载荷)
                .signWith(SignatureAlgorithm.HS256, "itheima") //签名算法
                .setExpiration(new Date(System.currentTimeMillis() +
                        24*3600*1000)) //有效期
                .compact();
        System.out.println(jwt);
    }
//    @Test
//    public void parseJwt(){
//        // 1. 现场生成一个有效期为 1 小时的 Token
//        String freshToken = genJwt(); // 调用你自己写的生成方法
//
//        // 2. 解析这个刚生成的 Token
//        Claims claims = JwtUtils.parseJWT(freshToken);
//
//        // 3. 断言解析出的内容
//        System.out.println(claims);
//    }

}
