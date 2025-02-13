package com.chloe.controller.user;

import com.chloe.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "User Shop related API")
@Slf4j
public class ShopController {
    public static final String KEY="SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/status")
    @ApiOperation("Send shop's status")
    public Result<Integer> sendStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);
    log.info("Send the shop's status as:{}",status==1?"Open":"Close");
        return Result.success(status);
    }
}
