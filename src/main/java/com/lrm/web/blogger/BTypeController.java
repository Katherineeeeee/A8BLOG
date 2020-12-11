package com.lrm.web.blogger;

import com.lrm.po.Type;
import com.lrm.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by limi on 2017/10/16.
 */

@Controller
@RequestMapping("/blogger")
public class BTypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page",typeService.listType(pageable));
        return "blogger/types";
    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "blogger/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "blogger/types-input";
    }


    @PostMapping("/types")
    public String post(@Valid Type type,BindingResult result, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","Cannot add duplicate categories");
        }
        if (result.hasErrors()) {
            return "blogger/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "Add failed");
        } else {
            attributes.addFlashAttribute("message", "added successfully");
        }
        return "redirect:/blogger/types";
    }


    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name","nameError","Cannot add duplicate categories");
        }
        if (result.hasErrors()) {
            return "blogger/types-input";
        }
        Type t = typeService.updateType(id,type);
        if (t == null ) {
            attributes.addFlashAttribute("message", "Update failed");
        } else {
            attributes.addFlashAttribute("message", "Update Completed");
        }
        return "redirect:/blogger/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message", "successfully deleted");
        return "redirect:/blogger/types";
    }


}
