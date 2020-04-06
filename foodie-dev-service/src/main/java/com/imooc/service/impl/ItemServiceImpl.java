package com.imooc.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mapper.*;
import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.SearchItemsVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.service.ItemService;
import com.imooc.utils.PagedGridResult;
import com.imooc.utils.enums.CommentLevel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemsMapper itemsMapper;

    @Autowired
    private ItemsSpecMapper itemsSpecMapper;

    @Autowired
    private ItemsImgMapper itemsImgMapper;

    @Autowired
    private ItemsParamMapper itemsParamMapper;

    @Autowired
    private ItemsCommentsMapper itemsCommentsMapper;

    @Autowired
    private  ItemsMapperCustom itemsMapperCustom;

    @Override
    public Items queryItem(String itemId) {
        return itemsMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public List<ItemsSpec> queryItemSpec(String itemId) {
        Example exampleSpec = new Example(ItemsSpec.class);
        Example.Criteria criteria = exampleSpec.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return  itemsSpecMapper.selectByExample(exampleSpec);

    }

    @Override
    public ItemsParam queryItemParam(String itemId) {
        Example exampleParam = new Example(ItemsParam.class);
        Example.Criteria criteria = exampleParam.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return  itemsParamMapper.selectOneByExample(exampleParam);
    }

    @Override
    public List<ItemsImg> queryItemImage(String itemId) {
        Example exampleImg = new Example(ItemsImg.class);
        Example.Criteria criteria = exampleImg.createCriteria();
        criteria.andEqualTo("itemId",itemId);
        return  itemsImgMapper.selectByExample(exampleImg);
    }


    @Override
    public CommentLevelCountVO queryCommentCounts(String itemId) {
//        三次查询u
        Integer goodCounts = getCommentCounts(itemId, CommentLevel.GOOD.type);
        Integer normalCounts = getCommentCounts(itemId, CommentLevel.NORMAL.type);
        Integer badCounts = getCommentCounts(itemId, CommentLevel.BAD.type);
        Integer totalCounts = goodCounts + normalCounts +badCounts;
        CommentLevelCountVO countsVO = new CommentLevelCountVO();
        countsVO.setTotalCounts(totalCounts);
        countsVO.setGoodCounts(goodCounts);
        countsVO.setNormalCounts(normalCounts);
        countsVO.setBadCounts(badCounts);
        return countsVO;
    }

    Integer getCommentCounts(String itemId,Integer level){
        ItemsComments condition = new ItemsComments();
        condition.setItemId(itemId);
        if (level != null){
            condition.setCommentLevel(level);
        }
       return itemsCommentsMapper.selectCount(condition);
    }

    @Override
    public PagedGridResult queryPagedComments(String itemId,
                                                  Integer level,
                                                  Integer page,
                                                  Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("itemId",itemId);
        map.put("level",level);
        // mybatis-pagehelper
        PageHelper.startPage(page,pageSize);
        // list已经是分页过后的数据，需要封装到pagedGridResult.java给前端
        List<ItemCommentVO> list = itemsMapperCustom.queryItemComments(map);
        return setterPagedGrid(list,page);
    }
    private PagedGridResult setterPagedGrid(List<?> list,Integer page){
        PageInfo<?> pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }

    @Override
    public PagedGridResult searchItems(String keywords, String sort,Integer page,Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("keywords",keywords);
        map.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItems(map);
        return setterPagedGrid(list,page);
    }

    @Override
    public PagedGridResult searchItems(Integer catId, String sort, Integer page, Integer pageSize) {
        Map<String,Object> map = new HashMap<>();
        map.put("catId",catId);
        map.put("sort",sort);
        PageHelper.startPage(page,pageSize);
        List<SearchItemsVO> list = itemsMapperCustom.searchItemsThirdCat(map);
        return setterPagedGrid(list,page);
    }

    @Override
    public List<ShopcartVO> queryItemsBySpecIds(String specIds) {

//        '"1","2"'
        String ids[] = specIds.split(",");


        for (int i = 0 ; i <ids.length ; i++ ) {

            System.out.println("--"+ids[i]);

        }

        List<String> specIdsList =  new ArrayList<>();

        Collections.addAll(specIdsList,ids);

        System.out.println(specIdsList);

        return   itemsMapperCustom.queryItemBySpecIds(specIdsList);

    }
}
