package org.linlinjava.litemall.wx.web;

import org.linlinjava.litemall.core.util.ResponseUtil;
import org.linlinjava.litemall.db.domain.LitemallStore;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.linlinjava.litemall.db.service.LitemallStoreService;
import org.linlinjava.litemall.db.vo.CategoryGoodsVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/wx/store")
public class WxStoreController {

    @Resource
    private LitemallGoodsService goodsService;

    @Resource
    private LitemallStoreService storeService;

    @GetMapping("/goods")
    public Object goods(){
        List<CategoryGoodsVO> list = goodsService.getCategoryWithGoodsList();
        return ResponseUtil.ok(list);
    }

    @GetMapping("/{id}")
    public Object detail(@PathVariable Integer id){
        LitemallStore store = storeService.findById(id);
        return ResponseUtil.ok(store);
    }
}
