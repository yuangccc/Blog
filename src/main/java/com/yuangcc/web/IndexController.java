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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TagsService tagsService;
    @Autowired
    private TypesService typesService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort = ("updateTime"), direction = Sort.Direction.DESC)
                                    Pageable pageable,
                        Model model) {

        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typesService.listType(6));
        model.addAttribute("tags", tagsService.listTag(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(8));
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model) {
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "/blog";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 10, sort = ("updateTime"), direction = Sort.Direction.DESC)
                                     Pageable pageable,
                         @RequestParam String query,
                         Model model){

        model.addAttribute("page", blogService.listBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "/search";
    }
}
