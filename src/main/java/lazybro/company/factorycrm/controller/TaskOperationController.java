package lazybro.company.factorycrm.controller;

import lazybro.company.factorycrm.entity.Operation;
import lazybro.company.factorycrm.entity.Operations;
import lazybro.company.factorycrm.entity.Task;
import lazybro.company.factorycrm.service.TaskOperationService;
import lazybro.company.factorycrm.service.TaskService;
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
@RequestMapping("/task")
public class TaskOperationController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskOperationService taskOperationService;

    /**
     * Получение операций для задания
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}/operations")
    public String getTaskOperations(@PathVariable(value = "id") long id,
                                    Model model) {

        Task task = taskService.findTaskById(id);

        model.addAttribute("task", task);

        List<Operation> operations = task.getOperations();

        model.addAttribute("operations", operations);

        return "task-operations";
    }

    /**
     * Получение информации на добавление новой операции для задания
     *
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @GetMapping("/{id}/operations/add")
    public String getOperationsForAdd(@PathVariable(value = "id") long id,
                                      Model model) {

        Task task = taskService.findTaskById(id);

        model.addAttribute("task", task);

        List<String> actions = Arrays.stream(Operations.values())
                .map(Operations::getTitle).collect(Collectors.toList());

        model.addAttribute("actions", actions);

        return "task-operations-add";
    }

    /**
     * Добавление операции к заданию
     *
     * @param id
     * @param action
     * @param time
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @PostMapping("/{id}/operations/add")
    public String addOperationForTask(@PathVariable(value = "id") long id,
                                      @RequestParam(name = "action") String action,
                                      @RequestParam int time) {

        taskOperationService.addOperation(id, action, time);

        return "redirect:/task/{id}/operations";
    }

    /**
     * Удаление операции из задания
     *
     * @param task_id
     * @param operation_id
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @PostMapping("/{task.id}/operations/{operation.id}/remove")
    public String deleteOperationFromTask(@PathVariable(value = "task.id") long task_id,
                                          @PathVariable(value = "operation.id") long operation_id) {

        taskOperationService.deleteOperation(task_id, operation_id);

        return "redirect:/task/{task.id}/operations";
    }

    /**
     * Получение информации для изменение операции в задании
     *
     * @param task_id
     * @param operation_id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @GetMapping("/{task_id}/operations/{operation_id}/edit")
    public String getOperationsForEdit(@PathVariable(value = "task_id") long task_id,
                                       @PathVariable(value = "operation_id") long operation_id,
                                       Model model) {

        Task task = taskService.findTaskById(task_id);

        model.addAttribute("task", task);

        List<String> actions = Arrays.stream(Operations.values())
                .map(Operations::getTitle).collect(Collectors.toList());

        model.addAttribute("actions", actions);

        Operation operation = taskOperationService.findOperationById(operation_id);

        model.addAttribute("operation", operation);

        return "task-operations-edit";
    }

    /**
     * Обновление операции
     *
     * @param task_id
     * @param operation_id
     * @param action
     * @param operation
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @PostMapping("/{task.id}/operations/{operation.id}/edit")
    public String editOperationFromTask(@PathVariable(value = "task.id") long task_id,
                                        @PathVariable(value = "operation.id") long operation_id,
                                        @RequestParam(name = "action") String action,
                                        @ModelAttribute @Valid Operation operation,
                                        BindingResult bindingResult,
                                        Model model) {

        if (bindingResult.hasErrors()) {
            String message = bindingResult.getObjectName();
            model.addAttribute("message", message);
            return "error";
        }

        taskOperationService.editOperation(task_id, operation_id, action, operation);

        return "redirect:/task/{task.id}/operations";
    }

    /**
     * Расчет стоимости работ
     *
     * @param task
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/{task}/operations/cash")
    public String getTaskOperationCash(@PathVariable Task task, Model model) {

        int operationsDuration = task.getOperations().stream().mapToInt(Operation::getDuration).sum();

        long oneMinuteCash = 2_500 / 60;

        long finaOperationCash = operationsDuration * oneMinuteCash * task.getCount();

        model.addAttribute("task", task);
        model.addAttribute("duration", operationsDuration);
        model.addAttribute("cash", finaOperationCash);

        return "task-operations-cash";
    }

}
