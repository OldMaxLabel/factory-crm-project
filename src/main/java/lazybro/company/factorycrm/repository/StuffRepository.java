package lazybro.company.factorycrm.repository;

import lazybro.company.factorycrm.entity.Stuff;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StuffRepository extends CrudRepository<Stuff, Long> {

    List<Stuff> findByForm(String form);

    List<Stuff> findByMaterial(String material);

    List<Stuff> findByFormAndMaterial(String form, String material);

    Stuff findByFormAndMaterialAndDiameter(String form, String material, double diameter);

    Stuff findByFormAndMaterialAndHeightAndWidth(String form, String material, double height, double width);

}
