package com.yuangcc.web;

import com.yuangcc.po.Type;
import com.yuangcc.service.BlogService;
import com.yuangcc.service.TypesService;
import com.yuangcc.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {

    @Autowired
    private TypesService typesService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(@PageableDefault(size=8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable,
                        @PathVariable Long id,
                        Model model){

        List<Type> types = typesService.listType(10000);
        if(id == -1){
            id = types.get(0).getId();
        }
        BlogQuery blog = new BlogQuery();
        blog.setTypeId(id);
        model.addAttribute("types", types);
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        model.addAttribute("activeTypeId", id);
        return "/types";
    }
}
