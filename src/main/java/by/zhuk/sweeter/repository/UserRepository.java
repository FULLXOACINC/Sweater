package by.zhuk.sweeter.repository;

import by.zhuk.sweeter.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String userName);
    User findByActivationCode(String code);
}