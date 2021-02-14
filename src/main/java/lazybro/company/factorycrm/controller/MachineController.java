package lazybro.company.factorycrm.controller;

import lazybro.company.factorycrm.entity.Machine;
import lazybro.company.factorycrm.entity.Processing;
import lazybro.company.factorycrm.entity.State;
import lazybro.company.factorycrm.repository.MachineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class MachineController {

    @Autowired
    private MachineRepository machineRepository;

    /**
     * Получение всех станков + фильтр
     *
     * @param filter_process
     * @param model
     * @return
     */
    @GetMapping("/machine")
    public String getAllMachine(@RequestParam(required = false) String filter_process, Model model) {

        List<String> processing = Arrays.stream(Processing.values()).map(Processing::getTitle).collect(Collectors.toList());
        List<String> states = Arrays.stream(State.values()).map(State::getTitle).collect(Collectors.toList());

        model.addAttribute("processing", processing);
        model.addAttribute("states", states);

        Iterable<Machine> machines;

        if (filter_process != null && !filter_process.isEmpty()) {
            machines = machineRepository.findByProcessing(filter_process);
        } else {
            machines = machineRepository.findAll();
        }

        model.addAttribute("machines", machines);

        model.addAttribute("machine", new Machine());

        return "machine";
    }

    /**
     * Добавление нового станка
     *
     * @param machine
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/machine")
    public String addMachine(@ModelAttribute("machine") @Valid Machine machine,
                             BindingResult bindingResult,
                             Model model) {

        if (bindingResult.hasErrors()) {
            return "error";
        }

        Machine machineFromDb = machineRepository.findByTitle(machine.getTitle());

        if (null != machineFromDb) {
            String message = "Оборудование с таким наименованием уже зарегистрировано";
            model.addAttribute("message", message);
            return "error";
        } else {
            machineRepository.save(machine);
        }

        return "redirect:/machine";
    }

    /**
     * Получение детализации по станку
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/machine/{id}")
    public String getMachine(@PathVariable(value = "id") long id, Model model) {

        Machine machine = machineRepository.findById(id).orElseThrow();

        model.addAttribute("mech", machine);

        return "machine-details";
    }

    /**
     * Редактирование станка
     *
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/machine/{id}/edit")
    public String getMachineEdit(@PathVariable(value = "id") long id, Model model) {

        Machine machine = machineRepository.findById(id).orElseThrow();

        List<String> processing = Arrays.stream(Processing.values()).map(Processing::getTitle).collect(Collectors.toList());
        List<String> states = Arrays.stream(State.values()).map(State::getTitle).collect(Collectors.toList());

        model.addAttribute("processing", processing);
        model.addAttribute("states", states);

        model.addAttribute("machine", machine);

        return "machine-edit";
    }

    /**
     * Обновление станка
     *
     * @param id
     * @param machine
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/machine/{id}/edit")
    public String updateMachine(@PathVariable(value = "id") long id,
                                @ModelAttribute @Valid Machine machine,
                                BindingResult bindingResult,
                                Model model) {

        if (bindingResult.hasErrors()) {
            return "error";
        }

        Machine machineFromDb = machineRepository.findById(id).orElseThrow();

        machineFromDb.setProcessing(machine.getProcessing());
        machineFromDb.setState(machine.getState());
        machineFromDb.setTitle(machine.getTitle());
        machineFromDb.setxAxis(machine.getxAxis());
        machineFromDb.setyAxis(machine.getyAxis());
        machineFromDb.setzAxis(machine.getzAxis());
        machineFromDb.setDescription(machine.getDescription());

        Machine testMachineFromDb = machineRepository.findByTitle(machine.getTitle());

        if (null != testMachineFromDb && !testMachineFromDb.getId().equals(machineFromDb.getId())) {
            String message = "Оборудование с таким наименованием уже зарегистрировано";
            model.addAttribute("message", message);
            return "error";
        } else {
            machineRepository.save(machineFromDb);
        }

        model.addAttribute("mech", machineFromDb);

        return "machine-details";
    }


}
