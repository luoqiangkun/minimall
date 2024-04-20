package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.system.SystemConfig;
import org.linlinjava.litemall.core.util.ResponseUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/wx/mall")
@RestController
public class WxMallController {

    @GetMapping("")
    public Object detail(){
        Map<String, Object> detail = new HashMap<>();
        detail.put("name", SystemConfig.getMallName());
        detail.put("address", SystemConfig.getMallAddress());
        detail.put("phone", SystemConfig.getMallPhone());
        detail.put("qq", SystemConfig.getMallQQ());
        detail.put("longitude", SystemConfig.getMallLongitude());
        detail.put("latitude", SystemConfig.getMallLatitude());
        detail.put("banner",SystemConfig.getMallBanner());
        detail.put("logo",SystemConfig.getMallLogo());
        detail.put("desc",SystemConfig.getMallDesc());
        detail.put("notice",SystemConfig.getMallNotice());
        detail.put("businessStartTime",SystemConfig.getBusinessStartTime());
        detail.put("businessEndTime",SystemConfig.getBusinessEndTime());
        return ResponseUtil.ok(detail);
    }
}
