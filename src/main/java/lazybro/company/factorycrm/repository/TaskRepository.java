package lazybro.company.factorycrm.repository;

import lazybro.company.factorycrm.entity.Task;
import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Long> {
}
