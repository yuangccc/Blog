package com.yuangcc.web.admin;

import com.yuangcc.po.Tag;
import com.yuangcc.service.TagsService;
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

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagsService tagsService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                       Pageable pageable, Model model){

        model.addAttribute("page", tagsService.listTag(pageable));
        return "/admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "/admin/tags-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagsService.getTag(id));
        return "/admin/tags-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagsService.deleteTag(id);
        attributes.addFlashAttribute("message", "恭喜，删除成功！");
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags")
    public String post(@Valid Tag tag,
                        BindingResult result,
                        RedirectAttributes attributes){
        Tag tag1 = tagsService.getTagByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name", "nameError", "已有该标签");
        }
        if(result.hasErrors()){
            return "/admin/tags-input";
        }
        Tag t = tagsService.saveTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message", "抱歉，新增失败。");
        }else{
            attributes.addFlashAttribute("message", "恭喜，新增成功！");
        }

        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag,
                       BindingResult result,
                       RedirectAttributes attributes,
                       @PathVariable Long id){
        Tag tag1 = tagsService.getTagByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name", "nameError", "已有该标签");
        }
        if(result.hasErrors()){
            return "/admin/tags-input";
        }
        Tag t = tagsService.updateTag(id, tag);
        if(t == null){
            attributes.addFlashAttribute("message", "抱歉，更新失败。");
        }else{
            attributes.addFlashAttribute("message", "恭喜，更新成功！");
        }

        return "redirect:/admin/tags";
    }
}
