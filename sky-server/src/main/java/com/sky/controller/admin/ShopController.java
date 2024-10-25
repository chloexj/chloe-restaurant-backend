package com.sky.controller.admin;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/shop")
@Api(tags = "Shop related API")
@Slf4j
public class ShopController {
    public static final String KEY="SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;

    @PutMapping("/{status}")
    @ApiOperation("Set shop's status")
    public Result setStatus(@PathVariable Integer status){
        log.info("set shop's status as :{}",status==1?"Open":"Close");
        redisTemplate.opsForValue().set(KEY,status);
        return  Result.success();
    }
    @GetMapping("/status")
    @ApiOperation("Send shop's status")
    public Result<Integer> sendStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
    log.info("Send the shop's status as:{}",status==1?"Open":"Close");
        return Result.success(status);
    }
}
