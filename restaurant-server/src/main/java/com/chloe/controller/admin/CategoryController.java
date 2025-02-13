package com.chloe.controller.admin;

import com.chloe.dto.CategoryDTO;
import com.chloe.dto.CategoryPageQueryDTO;
import com.chloe.entity.Category;
import com.chloe.result.PageResult;
import com.chloe.result.Result;
import com.chloe.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequestMapping("/admin/category")
@Api(tags = "Category related api")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    //    paging query
    @GetMapping("/page")
    @ApiOperation("Paging query function")
    public Result<PageResult> pageQuery(CategoryPageQueryDTO categoryPageQueryDTO) {
        log.info("Category page query:{}", categoryPageQueryDTO);
        PageResult pageResult = categoryService.pageQuery(categoryPageQueryDTO);
        return Result.success(pageResult);

    }

    //update category info
    @PutMapping
    @ApiOperation("Update category")
    public Result updateInfo(@RequestBody CategoryDTO categoryDTO) {
        log.info("Update category:{}", categoryDTO);
        categoryService.updateInfo(categoryDTO);
        return Result.success();

    }

    //    change status
    @ApiOperation("Change status")
    @PostMapping("/status/{status}")
    public Result changeStatus(@PathVariable Integer status, Long id) {
        categoryService.updateStatus(status, id);
        return Result.success();
    }

    //add new category
    @PostMapping
    @ApiOperation("Add new category")
    public Result add(@RequestBody CategoryDTO categoryDTO) {
        categoryService.add(categoryDTO);
        return Result.success();
    }

    //delete category
    @DeleteMapping
    @ApiOperation("Delete category")
    public Result delete(Long id) {
        categoryService.delete(id);
        return Result.success();
    }

    //search according to type
    //其实这个不用做啊，上面分页已经可以实现效果了。
    @GetMapping("/list")
    @ApiOperation("Get by type")
    public Result<List<Category>> getByType(Integer type) {
        List<Category> list =  categoryService.getByType(type);
        return Result.success(list);
    }
}
