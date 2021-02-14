package lazybro.company.factorycrm.controller;

import lazybro.company.factorycrm.entity.Task;
import lazybro.company.factorycrm.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;

@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;

    /**
     * Получение списка всех заданий
     *
     * @param model
     * @return
     */
    @GetMapping
    public String task(Model model) {

        Iterable<Task> tasks = taskService.findAllTasks();

        model.addAttribute("tasks", tasks);

        return "task-main";
    }

    /**
     * Добавление нового задания
     *
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/add")
    public String taskAdd() {
        return "task-add";
    }

    /**
     * Публикация нового задания
     *
     * @param title
     * @param initialize_date
     * @param finalize_date
     * @param count
     * @param file
     * @return
     * @throws IOException
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/add")
    public String taskAddNew(@RequestParam String title,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate initialize_date,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate finalize_date,
                             @RequestParam int count,
                             @RequestParam(value = "file") MultipartFile file) throws IOException {

        taskService.addNewTask(title, initialize_date, finalize_date, count, file);

        return "redirect:/task";
    }

    /**
     * Детализации по заданию
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/{id}")
    public String taskDetails(@PathVariable(value = "id") long id, Model model) {

        Task task = taskService.findTaskById(id);

        model.addAttribute("task", task);

        return "task-details";

    }

    /**
     * Редактирование задания
     *
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/{id}/edit")
    public String taskEdit(@PathVariable(value = "id") long id, Model model) {

        Task task = taskService.findTaskById(id);

        model.addAttribute("task", task);

        return "task-edit";
    }

    /**
     * Обновление задания
     *
     * @param id
     * @param title
     * @param initialize_date
     * @param finalize_date
     * @param count
     * @param comments
     * @param file
     * @param model
     * @return
     * @throws IOException
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/{id}/edit")
    public String taskUpdate(@PathVariable(value = "id") long id,
                             @RequestParam String title,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate initialize_date,
                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate finalize_date,
                             @RequestParam int count,
                             @RequestParam String comments,
                             @RequestParam(value = "file") MultipartFile file,
                             Model model) throws IOException {

        Task task = taskService.findTaskById(id);

        taskService.updateTask(task, title, initialize_date, finalize_date, count, comments, file);

        model.addAttribute("task", task);

        return "task-details";
    }

    /**
     * Удаление задания
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/{id}/remove")
    public String taskRemove(@PathVariable(value = "id") long id) {

        taskService.removeTask(id);

        return "redirect:/task";
    }

}
