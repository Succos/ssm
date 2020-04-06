package com.imooc.service;

import com.imooc.pojo.*;
import com.imooc.pojo.vo.CommentLevelCountVO;
import com.imooc.pojo.vo.ItemCommentVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.utils.PagedGridResult;

import java.util.List;

public interface ItemService {
    public Items queryItem(String itemId);
    public List<ItemsSpec> queryItemSpec(String itemId);
    public ItemsParam queryItemParam(String itemId);
    public List<ItemsImg> queryItemImage(String itemId);

    /**
     * 返回vo 自定义
     * @param itemId
     */
    public CommentLevelCountVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品的pingjia
     * @param itemId
     * @param level
     * @return
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer page,
                                              Integer pageSize);


    public PagedGridResult searchItems(String keywords, String sort,Integer page,Integer pageSize);

    public PagedGridResult searchItems(Integer keywords, String sort,Integer page,Integer pageSize);


    public List<ShopcartVO> queryItemsBySpecIds(String specIds);
}
