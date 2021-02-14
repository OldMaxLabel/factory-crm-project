package lazybro.company.factorycrm.repository;

import lazybro.company.factorycrm.entity.Stock;
import org.springframework.data.repository.CrudRepository;

public interface StockRepository extends CrudRepository<Stock, Long> {
}
