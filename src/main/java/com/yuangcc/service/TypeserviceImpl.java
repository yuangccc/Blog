package com.yuangcc.service;

import com.yuangcc.NotFoundException;
import com.yuangcc.dao.TypeRepository;
import com.yuangcc.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TypeserviceImpl implements TypesService{

    @Autowired
    private TypeRepository typerepository;

    @Override
    @Transactional
    public Type saveType(Type type) {
        return typerepository.save(type);
    }

    @Override
    @Transactional
    public Type getType(Long id) {
        return typerepository.findOne(id);
    }

    @Override
    @Transactional
    public Page<Type> listType(Pageable pageable) {
        return typerepository.findAll(pageable);
    }

    @Override
    public List<Type> listType() {
        return typerepository.findAll();
    }

    @Override
    public List<Type> listType(Integer size) {
        Sort sort = new Sort(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = new PageRequest(0, size, sort);
        return typerepository.findTop(pageable);
    }

    @Override
    @Transactional
    public Type updateType(Long id, Type type) {
        Type t = typerepository.findOne(id);
        if(t == null){
            throw new NotFoundException("不存在该分类");
        }
        BeanUtils.copyProperties(type, t);
        return typerepository.save(t);
    }

    @Override
    @Transactional
    public void deleteType(Long id) {
        typerepository.delete(id);
    }

    @Override
    @Transactional
    public Type getTypeByName(String name) {
        return typerepository.findByName(name);
    }
}
