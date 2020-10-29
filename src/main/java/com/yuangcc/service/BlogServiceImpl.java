package com.yuangcc.service;

import com.yuangcc.NotFoundException;
import com.yuangcc.dao.BlogRepository;
import com.yuangcc.po.Blog;
import com.yuangcc.po.Type;
import com.yuangcc.utils.MarkdownUtils;
import com.yuangcc.utils.MyBeanUtils;
import com.yuangcc.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Transient;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class BlogServiceImpl implements BlogService{

    @Autowired
    private BlogRepository blogRepository;

    @Override
    @Transactional
    public Blog getBlog(Long id) {
        return blogRepository.findOne(id);
    }

    @Override
    @Transactional
    public Blog getAndConvert(Long id) {

        Blog blog = blogRepository.findOne(id);
        if(blog == null){
            throw new NotFoundException("该博客不存在。");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog, b);
        String content = b.getContent();
        b.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
//        blogRepository.updateViews(id);
        return b;
    }

    @Override
    @Transactional
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogRepository.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if( !"".equals(blog.getTitle()) && blog.getTitle() != null){
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId() != null){
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.isRecommend()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    @Transactional
    public Page<Blog> listBlog(Pageable pageable) {
        return blogRepository.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(String query, Pageable pageable) {
        return blogRepository.findByQuery(query, pageable);
    }

    @Override
    @Transactional
    public List<Blog> listRecommendBlogTop(Integer size) {

        Sort sort = new Sort(Sort.Direction.DESC, "updateTime");
        Pageable pageable = new PageRequest(0, size, sort);
        return blogRepository.findTop(pageable);
    }

    @Override
    @Transactional
    public Blog saveBlog(Blog blog){
        if (blog.getId() == null){
            blog.setCreatTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else{
            blog.setUpdateTime(new Date());
        }
        return blogRepository.save(blog);
    }

    @Override
    @Transactional
    public Blog updateBlog(Long id, Blog blog) {
        Blog b = blogRepository.findOne(id);
        if(b == null){
            throw new NotFoundException("该博客不存在。");
        }else {
            BeanUtils.copyProperties(blog, b, MyBeanUtils.getNullPropertiyName(blog));
            b.setUpdateTime(new Date());
            return blogRepository.save(b);
        }
    }

    @Override
    @Transactional
    public void deleteBlog(Long id) {
        blogRepository.delete(id);
    }
}
