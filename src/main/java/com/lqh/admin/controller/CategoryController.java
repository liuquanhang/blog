package com.lqh.admin.controller;

import com.lqh.admin.dto.QueryPage;
import com.lqh.admin.dto.ResponseCode;
import com.lqh.admin.entity.Article;
import com.lqh.admin.entity.Category;
import com.lqh.admin.exception.GlobalException;
import com.lqh.admin.service.ArticleService;
import com.lqh.admin.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *@Author: null
 *@Date: 21:16 2019/3/28
 *显示分类页面
 */
@RestController
@RequestMapping("/category")
public class CategoryController extends BaseController{


    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ArticleService articleService;

    @GetMapping(value = "/findAll")
    public ResponseCode findAll(){
        return ResponseCode.success(categoryService.findAll());
    }

    /**
     *@Author: null
     *@Date: 21:38 2019/3/28
     * 查找每种分类下文章的数量
     */
    @GetMapping(value = "/findArticleCountForCategory")
    public ResponseCode findArticleCountForCategory(){
        Map<String, Integer> map = new HashMap<>(16);
        List<Category> categoryList = categoryService.findAll();
        for (Category category:categoryList){
            List<Article> articleList = articleService.findByCategory(category.getName());
            map.put(category.getName(),articleList.size());
        }
        return ResponseCode.success(map);
    }

    @PostMapping(value = "/findByPage")
    public ResponseCode findByPage(QueryPage queryPage,Category category){
        return ResponseCode.success(super.selectByPageNumSize(queryPage,() -> categoryService.findByPage(category)));
    }

    @GetMapping(value = "/findById")
    public ResponseCode findById(@RequestParam(value = "id")Long id){
        return ResponseCode.success(categoryService.findById(id));
    }

    @PostMapping(value = "/save")
    public ResponseCode save(@RequestBody Category category){
        try {
            categoryService.save(category);
            return ResponseCode.success();
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PutMapping(value = "/update")
    public ResponseCode update(@RequestBody Category category){
        try {
            categoryService.update(category);
            return ResponseCode.success();
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }

    @PostMapping(value = "/delete")
    public ResponseCode delete(@RequestBody Long...ids){
        try {
            categoryService.delete(ids);
            return ResponseCode.success();
        }catch (Exception e){
            e.printStackTrace();
            throw new GlobalException(e.getMessage());
        }
    }
}
