package com.yuangcc.web;


import com.yuangcc.NotFoundException;
import com.yuangcc.service.BlogService;
import com.yuangcc.service.TagsService;
import com.yuangcc.service.TypesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagsService tagsService;
    @Autowired
    private TypesService typesService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 2, sort = ("updateTime"), direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typesService.listType(6));
        model.addAttribute("tags", tagsService.listTag(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {

        return "blog";
    }
}
