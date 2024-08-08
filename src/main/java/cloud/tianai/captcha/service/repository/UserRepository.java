package cloud.tianai.captcha.service.repository;

import cloud.tianai.captcha.service.model.UserLoginModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserLoginModel, Long> {
    UserLoginModel findByUserName(String userName);
}

