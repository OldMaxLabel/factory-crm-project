package lazybro.company.factorycrm.controller;

import lazybro.company.factorycrm.entity.Form;
import lazybro.company.factorycrm.entity.Material;
import lazybro.company.factorycrm.entity.Stuff;
import lazybro.company.factorycrm.service.StuffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StuffController {

    @Autowired
    private StuffService stuffService;

    /**
     * Весь материал + фильтр
     *
     * @param filter_form
     * @param filter_material
     * @param model
     * @return
     */
    @GetMapping("/stuff")
    public String getStuff(@RequestParam(required = false) String filter_form,
                           @RequestParam(required = false) String filter_material,
                           Model model) {

        List<String> forms = Arrays.stream(Form.values()).map(Form::getTitle).collect(Collectors.toList());
        List<String> materials = Arrays.stream(Material.values()).map(Material::getTitle).collect(Collectors.toList());

        model.addAttribute("forms", forms);
        model.addAttribute("materials", materials);

        Iterable<Stuff> stuff = stuffService.findStuffByFilter(filter_form, filter_material);

        model.addAttribute("stuffs", stuff);

        model.addAttribute("stuff", new Stuff());

        return "stuff";
    }

    /**
     * Добавление материала
     *
     * @param stuff
     * @param form
     * @param material
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/stuff")
    public String addNewStuff(@ModelAttribute("stuff") @Valid Stuff stuff,
                              @RequestParam(required = false) String form,
                              @RequestParam(required = false) String material,
                              BindingResult bindingResult,
                              Model model) {

        if (bindingResult.hasErrors()) {
            return "error";
        }

        boolean addStuff = stuffService.addStuff(stuff, form, material);

        if (!addStuff) {
            String message = "Проверьте правильность введенных параметров!";
            model.addAttribute("message", message);
            return "error";
        }

        return "redirect:/stuff";
    }

}
