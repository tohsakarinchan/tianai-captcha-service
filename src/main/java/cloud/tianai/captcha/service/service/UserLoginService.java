package cloud.tianai.captcha.service.service;

import cloud.tianai.captcha.service.model.UserLoginModel;
import cloud.tianai.captcha.service.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class UserLoginService {

    @Autowired
    private UserRepository userRepository;

    private final String SECRET_KEY = "A2D5F8G1J3K6M9P2R5T8W0Y3C5F8H2J6"; // 用于生成JWT的密钥

    public UserLoginModel findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    public String generateToken(UserLoginModel user) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(user.getUserName())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 设置过期时间
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public boolean validateToken(String token, UserLoginModel user) {
        String username = extractUsername(token);
        return (username.equals(user.getUserName()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private boolean isTokenExpired(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody()
                .getExpiration().before(new Date());
    }
}
