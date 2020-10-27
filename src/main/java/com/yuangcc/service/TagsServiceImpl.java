package com.yuangcc.service;

import com.yuangcc.NotFoundException;
import com.yuangcc.dao.TagRepository;
import com.yuangcc.po.Tag;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TagsServiceImpl implements TagsService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
        tagRepository.delete(id);
    }

    @Override
    @Transactional
    public Tag getTag(Long id) {
        return tagRepository.findOne(id);
    }

    @Override
    @Transactional
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    @Override
    @Transactional
    public Tag updateTag(Long id, Tag tag) {
        Tag t = tagRepository.findOne(id);
        if(t == null){
            throw new NotFoundException("不存在该标签。");
        }else{
            BeanUtils.copyProperties(tag, t);
            return tagRepository.save(t);
        }
    }

    @Override
    public List<Tag> listTag(String Ids) {
        return tagRepository.findAll(Str2List(Ids));
    }
    private List<Long> Str2List(String Ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(Ids) && Ids != null){
            String[] idarray = Ids.split(",");
            for(int i=0; i<idarray.length; i++){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }
}
