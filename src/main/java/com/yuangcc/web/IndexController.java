package com.yuangcc.web;


import com.yuangcc.NotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class IndexController {

    @GetMapping("/")
    public String index() {
//        int i = 1/0;
//        String blog = null;
//        if(blog == null){
//            throw new NotFoundException("博客不存在");
//        }
        return "index";
    }

    @GetMapping("/blog")
    public String blog() {
//        int i = 1/0;
//        String blog = null;
//        if(blog == null){
//            throw new NotFoundException("博客不存在");
//        }
        return "blog";
    }
}
