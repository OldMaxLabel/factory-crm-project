package lazybro.company.factorycrm.controller;

import lazybro.company.factorycrm.entity.*;
import lazybro.company.factorycrm.repository.ToolRepository;
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
public class ToolController {

    @Autowired
    private ToolRepository toolRepository;

    /**
     * Инструмент (список и добавление)
     *
     * @param filter_specialization
     * @param model
     * @return
     */
    @GetMapping("/tool")
    public String getTool(@RequestParam(required = false) String filter_specialization, Model model) {

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

        model.addAttribute("tool", new Tool());

        return "tool";
    }

    /**
     * Добавление инструмента
     *
     * @param tool
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/tool")
    public String addTool(@ModelAttribute("tool") @Valid Tool tool,
                          BindingResult bindingResult,
                          Model model) {

        if (bindingResult.hasErrors()) {
            return "error";
        }

        String message;

        Tool toolFromDb = toolRepository.findByTitle(tool.getTitle());

        if (null != toolFromDb) {
            if (toolFromDb.getDiameter() != tool.getDiameter() || toolFromDb.getLength() != tool.getLength()
                    || toolFromDb.getCuttingLength() != tool.getCuttingLength()) {
                message = "Инструмент не добавлен! Инструмент с данным наименованием имеет другие параметры";
                model.addAttribute("message", message);
                return "error";
            } else {
                toolFromDb.setCount(toolFromDb.getCount() + tool.getCount());
                toolRepository.save(toolFromDb);
            }
        } else {
            if (tool.getDiameter() < 1 || tool.getLength() < 1 || tool.getCuttingLength() > tool.getLength() || tool.getCount() < 0) {
                message = "Инструмент не добавлен! Уточните правильность введенных данных";
                model.addAttribute("message", message);
                return "error";
            } else {
                toolRepository.save(tool);
            }
        }

        return "redirect:/tool";
    }

    /**
     * Получение инструмента для списания
     *
     * @param tool
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @GetMapping("/tool/{tool}/edit")
    public String getToolForEdit(@PathVariable Tool tool,
                                 Model model) {

        model.addAttribute("tool", tool);

        return "tool-edit";
    }

    /**
     * Списание инструмента
     *
     * @param tool
     * @param count
     * @param model
     * @return
     */
    @PreAuthorize("hasAuthority('MANAGER')")
    @PostMapping("/tool/{tool}/edit")
    public String editTool(@PathVariable Tool tool,
                           @RequestParam int count,
                           Model model) {

        tool.setCount(
                Math.max((tool.getCount() - count), 0)
        );

        toolRepository.save(tool);


        model.addAttribute("tool", tool);

        return "tool-edit";
    }

}
