package cloud.tianai.captcha.service.controller;

import cloud.tianai.captcha.service.model.UserLoginModel;
import cloud.tianai.captcha.service.service.UserLoginService;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.util.DigestUtils;

@RestController
@RequestMapping("/api")
public class UserLoginController {

    private static final Logger logger = LoggerFactory.getLogger(UserLoginController.class);

    @Autowired
    private UserLoginService userLoginService;

    @CrossOrigin(origins = "*", methods = { RequestMethod.POST })
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserLoginModel user = userLoginService.findByUserName(loginRequest.getUserName());
        logger.info("User: {}", user);

        if (user != null && user.getUserPassword().equals(loginRequest.getUserPassword())) {
            return ResponseEntity.ok("Login successful");
        } else {
            return ResponseEntity.status(401).body("Invalid username or password");
        }
    }
}

@Setter
@Getter
class LoginRequest {
    private String userName;
    private String userPassword;
}
