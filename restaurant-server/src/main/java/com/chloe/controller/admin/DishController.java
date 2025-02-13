package com.chloe.controller.admin;

import com.chloe.dto.DishDTO;
import com.chloe.dto.DishPageQueryDTO;
import com.chloe.result.PageResult;
import com.chloe.result.Result;
import com.chloe.service.DishService;
import com.chloe.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin/dish")
@Api("Dish related api")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping
    @ApiOperation("Add dish")
    public Result add(@RequestBody DishDTO dishDTO) {
        log.info("Add new dish:{}", dishDTO);
        dishService.addWithFlavor(dishDTO);
        //清理redis中的缓存数据
        String key = "dish_" + dishDTO.getCategoryId();
        cleanCache(key);
        return Result.success();
    }


    @GetMapping("/page")
    @ApiOperation("Dish paging query function")
    public Result<PageResult> pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        log.info("Dish page query:{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }


    @DeleteMapping
    @ApiOperation("Dishes batch delete")
    public Result delete(@RequestParam List<Long> ids) {
        log.info("Dishes batch delete:{}", ids);
        dishService.deleteBatch(ids);
        //将所有菜品缓存数据清理，所有以dish_开头的key
        cleanCache("dish_*");

      return Result.success();
    }



    @PostMapping("/status/{status}")
    @ApiOperation("Update status")
    public Result updateStatus(@PathVariable Integer status, Long id) {
        log.info("Update status by id");
        dishService.updateStatus(status, id);

        cleanCache("dish_*");

        return Result.success();
    }

    @PutMapping
    @ApiOperation("Update dish")
    public Result update(@RequestBody DishDTO dishDTO) {
    log.info("Update dish:{}",dishDTO);
    dishService.update(dishDTO);
        cleanCache("dish_*");

        return Result.success();
    }
    @GetMapping("/{id}")
    @ApiOperation("Get dish by id")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("get dish by id:{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }
@GetMapping("/list")
    public Result<List<DishVO>> getByCategoryId(Long categoryId){
     List<DishVO> list=  dishService.getByCategoryId(categoryId);
        return Result.success(list);
}

//清理缓存数据的方法
private  void cleanCache(String pattern){
    //将所有菜品缓存数据清理，所有以dish_开头的key

    Set keys=  redisTemplate.keys(pattern);
    redisTemplate.delete(keys);

}
}
