package com.chloe.service.impl;

import com.chloe.context.BaseContext;
import com.chloe.dto.ShoppingCartDTO;
import com.chloe.entity.Dish;
import com.chloe.entity.Setmeal;
import com.chloe.entity.ShoppingCart;
import com.chloe.mapper.DishMapper;
import com.chloe.mapper.SetMapper;
import com.chloe.mapper.ShoppingCartMapper;
import com.chloe.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private ShoppingCartMapper shoppingCartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetMapper setMapper;
    @Override
    public void addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
        ShoppingCart shoppingCart =new ShoppingCart();
        BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
        Long userId = BaseContext.getCurrentId();
        shoppingCart.setUserId(userId);

        //判断加入购物车的商品是否存在
        //如果存在则数量+1
//套餐ID和dish id   如果是dish还要加口味
       List<ShoppingCart> list= shoppingCartMapper.list(shoppingCart);

       if(list!=null&&list.size()>0){
           ShoppingCart cart = list.get(0);
           cart.setNumber(cart.getNumber()+1);
           shoppingCartMapper.updateNumberById(cart);
       }else {
//        如果不存在就插入一条购物车数据
           //要先从菜品表/套餐表中查询shoppingCartDTO中缺少的内容。然后插入到shoppingCart中
           Long dishId = shoppingCartDTO.getDishId();
//           Long setmealId = shoppingCartDTO.getSetmealId();
           if(dishId!=null){
               //本次添加的是菜品
               Dish dish = dishMapper.getById(dishId);
               shoppingCart.setName(dish.getName());
               shoppingCart.setImage(dish.getImage());
               shoppingCart.setAmount(dish.getPrice());


           }else {
               //添加进来的不是菜品就是套餐，所以直接else
               Long setmealId = shoppingCartDTO.getSetmealId();
               Setmeal setmeal = setMapper.getById(setmealId);
               shoppingCart.setName(setmeal.getName());
               shoppingCart.setImage(setmeal.getImage());
               shoppingCart.setAmount(setmeal.getPrice());

           }

           shoppingCart.setNumber(1);
           shoppingCart.setCreateTime(LocalDateTime.now());
           shoppingCartMapper.insert(shoppingCart);
       }



    }

    @Override
    public List<ShoppingCart> showShoppingCart() {
       ShoppingCart shoppingCart= ShoppingCart.builder().userId(BaseContext.getCurrentId()).build();
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        return list;
    }

    @Override
    public void cleanShoppingCart() {
        shoppingCartMapper.deleteByUserId(BaseContext.getCurrentId());
    }

    @Override
    public void subShoppingCart(ShoppingCartDTO shoppingCartDTO) {
    ShoppingCart shoppingCart=ShoppingCart.builder().userId(BaseContext.getCurrentId()).build();
    BeanUtils.copyProperties(shoppingCartDTO,shoppingCart);
    //查询数量
        List<ShoppingCart> list = shoppingCartMapper.list(shoppingCart);
        shoppingCart = list.get(0);
        Integer num=shoppingCart.getNumber();

        if(num==1) {
        //如果菜品数量是1的话，直接删掉
        shoppingCartMapper.deleteByShoppingCart(shoppingCart.getId());
    }else {
          num=num-1;
            shoppingCart.setNumber(num);
        //如果菜品数量大于1的话 更新数据
shoppingCartMapper.updateNumberById(shoppingCart);
    }
    }


}
