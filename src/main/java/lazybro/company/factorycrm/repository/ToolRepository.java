package lazybro.company.factorycrm.repository;

import lazybro.company.factorycrm.entity.Tool;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ToolRepository extends CrudRepository<Tool, Long> {

    List<Tool> findBySpecialization(String specialization);

    List<Tool> findByDiameter(double diameter);

    Tool findByTitle(String title);

}
