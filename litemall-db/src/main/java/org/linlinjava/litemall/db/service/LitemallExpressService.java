package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallExpressMapper;
import org.linlinjava.litemall.db.domain.LitemallExpress;
import org.linlinjava.litemall.db.domain.LitemallExpressExample;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

@Service
public class LitemallExpressService {
    @Resource
    private LitemallExpressMapper expressMapper;

    /**
     * 根据公司编码获取公司名称
     * @param code
     * @return
     */
    public String getNameByCode(String code){
        if(!StringUtils.hasText(code)){
            return null;
        }
        LitemallExpressExample litemallExpressExample = new LitemallExpressExample();
        litemallExpressExample.or().andCodeEqualTo(code);
        LitemallExpress litemallExpress = expressMapper.selectOneByExample(litemallExpressExample);
        return litemallExpress != null ? litemallExpress.getName() : null;
    }
}
