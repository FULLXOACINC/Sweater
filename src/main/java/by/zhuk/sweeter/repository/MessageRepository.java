package by.zhuk.sweeter.repository;

import by.zhuk.sweeter.model.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findByTag(String tag);
}