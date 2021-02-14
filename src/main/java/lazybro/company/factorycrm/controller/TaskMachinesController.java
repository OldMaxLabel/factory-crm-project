package lazybro.company.factorycrm.controller;

import lazybro.company.factorycrm.entity.Machine;
import lazybro.company.factorycrm.entity.Processing;
import lazybro.company.factorycrm.entity.State;
import lazybro.company.factorycrm.entity.Task;
import lazybro.company.factorycrm.service.TaskMachineService;
import lazybro.company.factorycrm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/task")
public class TaskMachinesController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskMachineService taskMachineService;

    /**
     * Получение оборудования для задания
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}/machines")
    public String getTaskMachines(@PathVariable(value = "id") long id,
                                  Model model) {

        Task task = taskService.findTaskById(id);

        model.addAttribute("task", task);

        Set<Machine> machines = task.getMachines();

        model.addAttribute("machines", machines);

        return "task-machines";
    }

    /**
     * Список для добавления оборудования к заданию
     *
     * @param filter_process
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @GetMapping("/{id}/machines/add")
    public String getTaskMachinesForAdd(@RequestParam(required = false) String filter_process,
                                        @PathVariable(value = "id") long id,
                                        Model model) {

        Task task = taskService.findTaskById(id);

        model.addAttribute("task", task);

        List<String> processing = Arrays.stream(Processing.values()).map(Processing::getTitle).collect(Collectors.toList());
        List<String> states = Arrays.stream(State.values()).map(State::getTitle).collect(Collectors.toList());

        model.addAttribute("processing", processing);
        model.addAttribute("states", states);

        Iterable<Machine> machines = taskMachineService.findMachinesByFilter(filter_process);

        model.addAttribute("machines", machines);

        return "task-machines-add";
    }

    /**
     * Добавление оборудования к заданию
     *
     * @param task_id
     * @param mech_id
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @PostMapping("/{task.id}/machines/add/{mech.id}/add")
    public String addMachineFromTask(@PathVariable(value = "task.id") long task_id,
                                    @PathVariable(value = "mech.id") long mech_id) {

        taskMachineService.addMachine(task_id, mech_id);

        return "redirect:/task/{task.id}/machines";
    }

    /**
     * Удаление станка из задания
     *
     * @param task_id
     * @param mech_id
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @PostMapping("/{task.id}/machines/{mech.id}/remove")
    public String deleteMachineFromTask(@PathVariable(value = "task.id") long task_id,
                                        @PathVariable(value = "mech.id") long mech_id) {

        taskMachineService.removeMachine(task_id, mech_id);

        return "redirect:/task/{task.id}/machines";
    }

}
