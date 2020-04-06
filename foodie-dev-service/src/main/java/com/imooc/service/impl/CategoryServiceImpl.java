package com.imooc.service.impl;

import com.imooc.mapper.CarouselMapper;
import com.imooc.mapper.CategoryMapper;
import com.imooc.mapper.CategoryMapperCustom;
import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private CategoryMapperCustom categoryMapperCustom;

    @Override
    public List<CategoryVO> getSubCatList(Integer rootCatID) {
        return  categoryMapperCustom.getSubCatlist(rootCatID);
    }

    @Override
    public List<Category> queryAllRootLevelCat() {
       Example example = new Example(Category.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type",1);
        List<Category> categories = categoryMapper.selectByExample(example);
        return categories;
    }

    @Override
    public List<NewItemsVO> getSixNewItemsLazy(Integer rootCatID) {
        Map<String,Object> map = new HashMap<>();
        map.put("rootCatID",rootCatID);
        return categoryMapperCustom.getSixNewItemsLazy(map);
    }
}
