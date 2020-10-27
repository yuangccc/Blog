package com.yuangcc.web.admin;

import com.yuangcc.po.Type;
import com.yuangcc.service.TypesService;
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
public class TypeController {

    @Autowired
    private TypesService typesService;

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC)
                                        Pageable pageable,
                                        Model model){
        model.addAttribute("page", typesService.listType(pageable));
        return "/admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "/admin/types-input";
    }

    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type type1 = typesService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typesService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typesService.getType(id));
        return "/admin/types-input";
    }

    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type,
                       BindingResult result,
                       @PathVariable Long id,
                       RedirectAttributes redirectAttributes){

        Type type1 = typesService.getTypeByName(type.getName());
        if(type1 != null){
            result.rejectValue("name", "nameError", "已有相同分类");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typesService.updateType(id, type);
        if (t == null){
            redirectAttributes.addFlashAttribute("message", "抱歉, 更新失败。");
        }else{
            redirectAttributes.addFlashAttribute("message", "恭喜, 更新成功!");
        }

        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes){
        typesService.deleteType(id);
        redirectAttributes.addFlashAttribute("message", "恭喜, 删除成功!");
        return "redirect:/admin/types";
    }
}
