package cloud.tianai.captcha.service.service;

import cloud.tianai.captcha.service.model.UserLoginModel;
import cloud.tianai.captcha.service.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserLoginService {

    @Autowired
    private UserRepository userRepository;

    public UserLoginModel findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}
