package com.yuangcc.service;

import com.yuangcc.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagsService {

    Tag saveTag(Tag tag);

    void deleteTag(Long id);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    List<Tag> listTag();

    List<Tag> listTag(Integer size);

    Tag updateTag(Long id, Tag tag);

    List<Tag> listTag(String Ids);

    Tag getTagByName(String name);
}
