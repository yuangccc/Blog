package com.yuangcc.service;

import com.yuangcc.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypesService {

    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    List<Type> listType();

    List<Type> listType(Integer size);

    Type updateType(Long id, Type type);

    void deleteType(Long id);

    Type getTypeByName(String name);
}
