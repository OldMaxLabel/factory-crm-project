package lazybro.company.factorycrm.repository;

import lazybro.company.factorycrm.entity.Message;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {

    List<Message> findByTag(String tag);

}
