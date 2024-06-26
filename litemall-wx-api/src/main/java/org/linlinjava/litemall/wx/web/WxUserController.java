package org.linlinjava.litemall.wx.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallUser;
import org.linlinjava.litemall.db.domain.UserVo;
import org.linlinjava.litemall.db.service.LitemallOrderService;
import org.linlinjava.litemall.db.service.LitemallUserService;
import org.linlinjava.litemall.wx.annotation.LoginUser;
import org.linlinjava.litemall.wx.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户服务
 */
@RestController
@RequestMapping("/wx/user")
@Validated
public class WxUserController {
    private final Log logger = LogFactory.getLog(WxUserController.class);

    @Autowired
    private LitemallOrderService orderService;

    @Autowired
    private LitemallUserService userService;

    @GetMapping("")
    public Object info(@LoginUser Integer userId){
        UserVo user = userService.findUserVoById(userId);
        return ResponseUtil.ok(user);
    }

    /**
     * 用户个人页面数据
     * <p>
     * 目前是用户订单统计信息
     *
     * @param userId 用户ID
     * @return 用户个人页面数据
     */
    @GetMapping("index")
    public Object list(@LoginUser Integer userId) {
        if (userId == null) {
            return ResponseUtil.unlogin();
        }

        Map<Object, Object> data = new HashMap<Object, Object>();
        data.put("order", orderService.orderInfo(userId));
        return ResponseUtil.ok(data);
    }

    @PostMapping("")
    public Object update(@LoginUser Integer userId, @RequestBody  UserDto userDto){
        if (userId == null) {
            return ResponseUtil.unlogin();
        }
        LitemallUser litemallUser = new LitemallUser();
        litemallUser.setId(userId);
        litemallUser.setNickname(userDto.getNickname());
        litemallUser.setAvatar(userDto.getAvatar());
        litemallUser.setBirthday(userDto.getBirthday());
        litemallUser.setGender(userDto.getGender());
        litemallUser.setMobile(userDto.getMobile());
        if (userService.updateById(litemallUser) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok(litemallUser);
    }

}