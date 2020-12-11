package com.lrm.web.blogger;

import com.lrm.po.Tag;
import com.lrm.service.TagService;
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
public class BTagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                               Pageable pageable, Model model) {
        model.addAttribute("page",tagService.listTag(pageable));
        return "blogger/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "blogger/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "blogger/tags-input";
    }


    @PostMapping("/tags")
    public String post(@Valid Tag tag,BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","Cannot add duplicate categories");
        }
        if (result.hasErrors()) {
            return "blogger/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "Add failed");
        } else {
            attributes.addFlashAttribute("message", "Added successfully");
        }
        return "redirect:/blogger/tags";
    }


    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result,@PathVariable Long id, RedirectAttributes attributes) {
        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","Cannot add duplicate categories");
        }
        if (result.hasErrors()) {
            return "blogger/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "Update failed");
        } else {
            attributes.addFlashAttribute("message", "Update Completed");
        }
        return "redirect:/blogger/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message", "successfully deleted");
        return "redirect:/blogger/tags";
    }


}
