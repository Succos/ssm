package com.imooc.controller;

import com.imooc.pojo.Items;
import com.imooc.pojo.ItemsImg;
import com.imooc.pojo.ItemsParam;
import com.imooc.pojo.ItemsSpec;
import com.imooc.pojo.vo.CommentLevelCountVO;
import com.imooc.pojo.vo.ItemVO;
import com.imooc.pojo.vo.ShopcartVO;
import com.imooc.service.ItemService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "首页",tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemController extends BaseController {
    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IMOOCJSONResult info(
            @ApiParam(name = "itemId",value = "以及分类的id",required = true)
            @PathVariable String itemId)
    {
        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg("id不能为空");
        }

        Items items = itemService.queryItem(itemId);
        List<ItemsImg> itemsImg = itemService.queryItemImage(itemId);
        ItemsParam itemsParams = itemService.queryItemParam(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpec(itemId);

        ItemVO itemVO = new ItemVO();
        itemVO.setItem(items);
        itemVO.setItemImgList(itemsImg);
        itemVO.setItemParams(itemsParams);
        itemVO.setItemSpecList(itemsSpecs);
        return IMOOCJSONResult.ok(itemVO);
    }


    @ApiOperation(value = "查询商品评价等级",notes = "查询商品评价等级",httpMethod = "GET")
    @GetMapping("/commentLevel")
    public IMOOCJSONResult commentLevel(
            @ApiParam(name = "itemId",value = "以及分类的id",required = true)
            @RequestParam String itemId)
    {
        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg("id不能为空");
        }

        CommentLevelCountVO items = itemService.queryCommentCounts(itemId);

        return IMOOCJSONResult.ok(items);
    }


    @ApiOperation(value = "查询商品评价",notes = "查询商品评价",httpMethod = "GET")
    @GetMapping("/comments")
    public IMOOCJSONResult comments(
            @ApiParam(name = "itemId",value = "以及分类的id",required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level",value = "评价等级",required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page",value = "第几页",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "该页显示条目数",required = false)
            @RequestParam Integer pageSize

    )
    {


        if (StringUtils.isBlank(itemId)){
            return IMOOCJSONResult.errorMsg("id不能为空");
        }

        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult grid = itemService.queryPagedComments(itemId, level, page, pageSize);

        return IMOOCJSONResult.ok(grid);
    }


    @ApiOperation(value = "搜索商品",notes = "搜索商品",httpMethod = "GET")
    @GetMapping("/search")
    public IMOOCJSONResult comments(
            @ApiParam(name = "keywords",value = "搜索关键词",required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort",value = "排序",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "第几页",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "该页显示条目数",required = false)
            @RequestParam Integer pageSize

    )
    {


        if (StringUtils.isBlank(keywords)){
            return IMOOCJSONResult.errorMsg("keywords不能为空");
        }

        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, page, pageSize);

        return IMOOCJSONResult.ok(pagedGridResult);
    }


    @ApiOperation(value = "搜索商品",notes = "搜索商品",httpMethod = "GET")
    @GetMapping("/catItems")
    public IMOOCJSONResult catItems(
            @ApiParam(name = "catId",value = "分类id",required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort",value = "排序",required = false)
            @RequestParam String sort,
            @ApiParam(name = "page",value = "第几页",required = false)
            @RequestParam Integer page,
            @ApiParam(name = "pageSize",value = "该页显示条目数",required = false)
            @RequestParam Integer pageSize

    )
    {


        if (catId == null){
            return IMOOCJSONResult.errorMsg("keywords不能为空");
        }

        if (page == null){
            page = 1;
        }
        if (pageSize == null){
            pageSize = PAGE_SIZE;
        }

        PagedGridResult pagedGridResult = itemService.searchItems(catId, sort, page, pageSize);

        return IMOOCJSONResult.ok(pagedGridResult);
    }


    @ApiOperation(value = "根据商品规格id查询商品数据",notes = "根据商品规格id查询商品数据",httpMethod = "GET")
    @GetMapping("/refresh")
    public IMOOCJSONResult refresh(
            @ApiParam(name = "itemSpecIds",value = "拼接的规格id字符串",required = true)
            @RequestParam String itemSpecIds
    )
    {


       if (StringUtils.isBlank(itemSpecIds)){
           return IMOOCJSONResult.ok();
       }
        System.out.println(itemSpecIds);
        List<ShopcartVO> shopcartVOS = itemService.queryItemsBySpecIds(itemSpecIds);

        return IMOOCJSONResult.ok(shopcartVOS);
    }

}
