package com.imooc.controller;

import com.imooc.pojo.Carousel;
import com.imooc.pojo.Category;
import com.imooc.pojo.vo.CategoryVO;
import com.imooc.pojo.vo.NewItemsVO;
import com.imooc.service.CarouselService;
import com.imooc.service.CategoryService;
import com.imooc.utils.IMOOCJSONResult;
import com.imooc.utils.enums.Sex;
import com.imooc.utils.enums.YesOrNo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api(value = "首页",tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {
    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;
    final static Logger logger = LoggerFactory.getLogger(IndexController.class);

    @GetMapping("/carousel")
    public IMOOCJSONResult carousel(){
        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.type);

        return IMOOCJSONResult.ok(carousels);
    }

    /**
     * 第一次刷新主页查询大分类，渲染展示到首页
     * 如果移到大分类，展示子分类的内容，如果有子分类，则不加载了
     */
    @ApiOperation(value = "获取商品一级分类",notes = "获取商品一级分类",httpMethod = "GET")
    @GetMapping("/cats")
    public IMOOCJSONResult cats(){
        List<Category> categories = categoryService.queryAllRootLevelCat();
        return IMOOCJSONResult.ok(categories);
    }

    @ApiOperation(value = "获取商品子分类",notes = "获取商品子分类",httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IMOOCJSONResult subCat(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId)
    {
        if (rootCatId == null){
            return  IMOOCJSONResult.errorMsg("参数错误");
        }
        List<CategoryVO> list = categoryService.getSubCatList(rootCatId);
        return IMOOCJSONResult.ok(list);
    }

    @ApiOperation(value = "查询每个一级分类下四个最新六条商品数据",notes = "查询每个一级分类下四个最新六条商品数据",httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IMOOCJSONResult sixNewItems(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable Integer rootCatId)
    {
        if (rootCatId == null){
            return  IMOOCJSONResult.errorMsg("参数错误");
        }
        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return IMOOCJSONResult.ok(list);
    }


}
