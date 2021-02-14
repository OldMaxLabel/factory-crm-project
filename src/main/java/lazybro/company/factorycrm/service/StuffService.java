package lazybro.company.factorycrm.service;

import lazybro.company.factorycrm.entity.Stuff;
import lazybro.company.factorycrm.repository.StuffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuffService {

    @Autowired
    private StuffRepository stuffRepository;

    public Iterable<Stuff> findStuffByFilter(String filter_form, String filter_material) {

        Iterable<Stuff> stuff;

        if (filter_form != null && !filter_form.isEmpty() && filter_material != null && filter_material.isEmpty()) {
            stuff = stuffRepository.findByForm(filter_form);
        } else if (filter_form != null && filter_form.isEmpty() && filter_material != null && !filter_material.isEmpty()) {
            stuff = stuffRepository.findByMaterial(filter_material);
        } else if (filter_form != null && !filter_form.isEmpty() && filter_material != null && !filter_material.isEmpty()) {
            stuff = stuffRepository.findByFormAndMaterial(filter_form, filter_material);
        } else {
            stuff = stuffRepository.findAll();
        }

        return stuff;
    }

    public boolean addStuff(Stuff stuff, String form, String material) {

        boolean isAdd = true;

        Stuff stuffFromDb;

        if (stuff.getDiameter() != 0.0 && stuff.getLength() != 0.0 && stuff.getHeight() == 0.0 && stuff.getWidth() == 0.0) {

            stuffFromDb = stuffRepository.findByFormAndMaterialAndDiameter(form, material, stuff.getDiameter());

            if (null != stuffFromDb) {
                stuffFromDb.setLength(stuffFromDb.getLength() + stuff.getLength());
                stuffRepository.save(stuffFromDb);
            } else {
                stuff.setForm(form);
                stuff.setMaterial(material);
                stuffRepository.save(stuff);
            }
        } else if (stuff.getDiameter() == 0.0 && stuff.getHeight() != 0.0 && stuff.getWidth() != 0.0 && stuff.getLength() != 0.0) {
            stuffFromDb = stuffRepository.findByFormAndMaterialAndHeightAndWidth(form, material, stuff.getHeight(), stuff.getWidth());

            if (null != stuffFromDb) {
                stuffFromDb.setLength(stuffFromDb.getLength() + stuff.getLength());
                stuffRepository.save(stuffFromDb);
            } else {
                stuff.setForm(form);
                stuff.setMaterial(material);
                stuffRepository.save(stuff);
            }
        } else {
            isAdd = false;
        }

        return isAdd;
    }
}
