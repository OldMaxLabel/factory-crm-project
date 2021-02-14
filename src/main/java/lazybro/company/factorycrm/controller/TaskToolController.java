package lazybro.company.factorycrm.controller;

import lazybro.company.factorycrm.entity.*;
import lazybro.company.factorycrm.repository.TaskRepository;
import lazybro.company.factorycrm.repository.ToolRepository;
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
public class TaskToolController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ToolRepository toolRepository;

    /**
     * Получение инструмента для задания
     *
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @GetMapping("/{id}/tools")
    public String getTaskTools(@PathVariable(value = "id") long id,
                                  Model model) {

        Task task = taskRepository.findById(id).orElseThrow();

        model.addAttribute("task", task);

        Set<Tool> tools = task.getTools();

        model.addAttribute("tools", tools);

        return "task-tools";
    }

    /**
     * Получение инструмента для добавления к заданию
     *
     * @param filter_specialization
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @GetMapping("/{id}/tools/add")
    public String getTaskToolsForAdd(@RequestParam(required = false) String filter_specialization,
                                        @PathVariable(value = "id") long id,
                                        Model model) {

        Task task = taskRepository.findById(id).orElseThrow();

        model.addAttribute("task", task);

        List<String> specializations = Arrays.stream(Specialization.values())
                .map(Specialization::getTitle).collect(Collectors.toList());

        model.addAttribute("specializations", specializations);

        Iterable<Tool> tools;

        if (filter_specialization != null && !filter_specialization.isEmpty()) {
            tools = toolRepository.findBySpecialization(filter_specialization);
        } else {
            tools = toolRepository.findAll();
        }

        model.addAttribute("tools", tools);

        return "task-tools-add";
    }

    /**
     * Добавление инструмента к заданию
     *
     * @param task_id
     * @param tool_id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @PostMapping("/{task.id}/tools/add/{tool.id}/add")
    public String addToolFromTask(@PathVariable(value = "task.id") long task_id,
                                     @PathVariable(value = "tool.id") long tool_id,
                                     Model model) {

        Task task = taskRepository.findById(task_id).orElseThrow();

        Tool tool = toolRepository.findById(tool_id).orElseThrow();

        task.getTools().add(tool);

        taskRepository.save(task);

        return "redirect:/task/{task.id}/tools";
    }

    /**
     * Удаление инструмента из задания
     *
     * @param task_id
     * @param tool_id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @PostMapping("/{task.id}/tools/{tool.id}/remove")
    public String deleteToolFromTask(@PathVariable(value = "task.id") long task_id,
                                        @PathVariable(value = "tool.id") long tool_id,
                                        Model model) {

        Task task = taskRepository.findById(task_id).orElseThrow();

        Tool tool = toolRepository.findById(tool_id).orElseThrow();

        task.getTools().remove(tool);

        taskRepository.save(task);

        return "redirect:/task/{task.id}/tools";
    }

}
