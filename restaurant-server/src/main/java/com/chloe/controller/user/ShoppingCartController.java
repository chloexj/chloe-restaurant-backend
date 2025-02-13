package com.chloe.controller.user;

import com.chloe.dto.ShoppingCartDTO;
import com.chloe.entity.ShoppingCart;
import com.chloe.result.Result;
import com.chloe.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/shoppingCart")
@Slf4j
@Api(tags = "Shopping Cart related API")
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping("/add")
    @ApiOperation("Add into shopping cart")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("add into shopping cart:{}",shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);

        return Result.success();

    }
    @PostMapping("/sub")
    @ApiOperation("sub from shopping cart")
    public Result sub(@RequestBody ShoppingCartDTO shoppingCartDTO){
        shoppingCartService.subShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("Check shoppingcart")
    public Result<List<ShoppingCart>> list(){
       List<ShoppingCart> list= shoppingCartService.showShoppingCart();
        return Result.success(list);
    }
@DeleteMapping("/clean")
@ApiOperation("Clean shopping cart")
    public Result clean(){
        shoppingCartService.cleanShoppingCart();
        return Result.success();
}

}
