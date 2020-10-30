package com.yuangcc.web;

import com.yuangcc.po.Tag;
import com.yuangcc.service.BlogService;
import com.yuangcc.service.TagsService;
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
public class TagShowController {

    @Autowired
    private TagsService tagsService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String types(@PageableDefault(size=8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
                                    Pageable pageable,
                        @PathVariable Long id,
                        Model model){

        List<Tag> tags = tagsService.listTag(10000);
        if(id == -1){
            id = tags.get(0).getId();
        }
        model.addAttribute("tags", tags);
        model.addAttribute("page", blogService.listBlog(id, pageable));
        model.addAttribute("activeTagId", id);
        return "/tags";
    }
}
