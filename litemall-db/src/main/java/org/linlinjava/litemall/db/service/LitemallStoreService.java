package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallStoreMapper;
import org.linlinjava.litemall.db.domain.LitemallStorage;

import org.linlinjava.litemall.db.domain.LitemallStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class LitemallStoreService {

    @Resource
    private LitemallStoreMapper litemallStoreMapper;


    public LitemallStore findById(Integer id) {
        return litemallStoreMapper.selectByPrimaryKey(id);
    }

}
