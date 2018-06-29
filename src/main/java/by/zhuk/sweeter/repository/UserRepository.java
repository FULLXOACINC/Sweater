package by.zhuk.sweeter.repository;

import by.zhuk.sweeter.model.Message;
import by.zhuk.sweeter.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String userName);
}