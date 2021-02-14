package lazybro.company.factorycrm.repository;

import lazybro.company.factorycrm.entity.Machine;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MachineRepository extends CrudRepository<Machine, Long> {

    List<Machine> findByProcessing(String process);

    Machine findByTitle(String title);

}
