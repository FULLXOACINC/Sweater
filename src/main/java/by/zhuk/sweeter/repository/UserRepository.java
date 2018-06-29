package by.zhuk.sweeter.repository;

import by.zhuk.sweeter.model.User;
import org.springframework.data.repository.CrudRepository;


public interface UserRepository extends CrudRepository<User, Integer> {

    User findByUsername(String userName);
}