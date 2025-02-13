package com.chloe.controller.user;

import com.chloe.constant.JwtClaimsConstant;
import com.chloe.dto.UserLoginDTO;
import com.chloe.entity.User;
import com.chloe.properties.JwtProperties;
import com.chloe.result.Result;
import com.chloe.service.UserService;
import com.chloe.utils.JwtUtil;
import com.chloe.vo.UserLoginVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/user")
@Api(tags = "Wechat User related API")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProperties jwtProperties;

    @ApiOperation("wechat login")
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO userLoginDTO){
log.info("wechat user log in:{}",userLoginDTO);
        User user = userService.wxLogin(userLoginDTO);
        Map<String,Object> claims=new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID,user.getId());
        String token = JwtUtil.createJWT(jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(), claims);
       UserLoginVO userLoginVO= UserLoginVO
               .builder()
               .id(user.getId())
               .openid(user.getOpenid())
               .token(token).build();
        return Result.success(userLoginVO);
    }
}
