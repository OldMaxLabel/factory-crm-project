package lazybro.company.factorycrm.repository;

import lazybro.company.factorycrm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}
