package lazybro.company.factorycrm.controller;

import lazybro.company.factorycrm.entity.Form;
import lazybro.company.factorycrm.entity.Material;
import lazybro.company.factorycrm.entity.Stock;
import lazybro.company.factorycrm.entity.Task;
import lazybro.company.factorycrm.service.TaskService;
import lazybro.company.factorycrm.service.TaskStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/task")
public class TaskStockController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskStockService taskStockService;

    /**
     * Информация для добавление заготовки
     *
     * @param id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @GetMapping("/{id}/stock")
    public String getStock(@PathVariable(value = "id") long id,
                           Model model) {
        Task task = taskService.findTaskById(id);
        List<String> forms = Arrays.stream(Form.values()).map(Form::getTitle).collect(Collectors.toList());
        List<String> materials = Arrays.stream(Material.values()).map(Material::getTitle).collect(Collectors.toList());
        model.addAttribute("forms", forms);
        model.addAttribute("materials", materials);
        model.addAttribute("stock", new Stock());
        model.addAttribute("task", task);

        return "task-stock";
    }

    /**
     * Добавление заготовки
     *
     * @param id
     * @param stock
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @PostMapping("/{id}/stock")
    public String addStock(@PathVariable(value = "id") long id,
                           @ModelAttribute("stock") @Valid Stock stock,
                           BindingResult bindingResult,
                           Model model) {

        if (bindingResult.hasErrors()) {
            return "error";
        }

        boolean isAdd = taskStockService.addNewStock(id, stock);

        Task task = taskService.findTaskById(id);

        if (!isAdd) {
            String message = "Проверьте правильность введенных параметров!";
            model.addAttribute("message", message);
            return "error";
        }

        model.addAttribute("task", task);

        return "task-details";
    }

    /**
     * Списание заготовки
     *
     * @param task_id
     * @param stock_id
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/{task.id}/stock/{stock.id}/push")
    public String pushStuff(@PathVariable(value = "task.id") long task_id,
                            @PathVariable(value = "stock.id") long stock_id,
                            Model model) {

        boolean isPush = taskStockService.pushStuff(task_id, stock_id);

        if (!isPush) {
            String message = "Материала в наличии нет, необходимо заказать!";
            model.addAttribute("message", message);
            return "error";
        }

        return "redirect:/stuff";
    }

    /**
     * Получение информации для изменение заготовки
     *
     * @param stock_id
     * @param model
     * @return
     * @throws IOException
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @GetMapping("/{task_id}/stock/{stock_id}/edit")
    public String stockUpdate(@PathVariable(value = "task_id") long task_id,
                              @PathVariable(value = "stock_id") long stock_id,
                              Model model) throws IOException {

        Task task = taskService.findTaskById(task_id);

        model.addAttribute("task", task);

        Stock stockFromDb = taskStockService.findTaskStockById(stock_id);

        model.addAttribute("stock", stockFromDb);

        List<String> forms = Arrays.stream(Form.values()).map(Form::getTitle).collect(Collectors.toList());
        List<String> materials = Arrays.stream(Material.values()).map(Material::getTitle).collect(Collectors.toList());
        model.addAttribute("forms", forms);
        model.addAttribute("materials", materials);

        return "task-stock-edit";
    }

    /**
     * Изменение заготовки
     *
     * @param id
     * @param stock_id
     * @param stock
     * @param model
     * @return
     * @throws IOException
     */
    @PreAuthorize("hasAuthority('ENGINEER')")
    @PostMapping("/{id}/stock/{stock_id}/edit")
    public String stockUpdatePost(@PathVariable(value = "id") long id,
                                  @PathVariable(value = "stock_id") long stock_id,
                                  @ModelAttribute("stock") @Valid Stock stock,
                                  BindingResult bindingResult,
                                  Model model) throws IOException {

        if (bindingResult.hasErrors()) {
            return "error";
        }

        taskStockService.updateTaskStock(stock_id, stock);

        Task task = taskService.findTaskById(id);

        model.addAttribute("task", task);

        return "task-details";
    }

}
