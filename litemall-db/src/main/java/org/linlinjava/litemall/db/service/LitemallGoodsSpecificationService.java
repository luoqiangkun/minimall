package org.linlinjava.litemall.db.service;

import org.linlinjava.litemall.db.dao.LitemallGoodsSpecificationMapper;
import org.linlinjava.litemall.db.domain.LitemallGoodsSpecification;
import org.linlinjava.litemall.db.domain.LitemallGoodsSpecificationExample;
import org.linlinjava.litemall.db.vo.GoodsSpecificationVO;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LitemallGoodsSpecificationService {
    @Resource
    private LitemallGoodsSpecificationMapper goodsSpecificationMapper;

    public List<LitemallGoodsSpecification> queryByGid(Integer id) {
        LitemallGoodsSpecificationExample example = new LitemallGoodsSpecificationExample();
        example.or().andGoodsIdEqualTo(id).andDeletedEqualTo(false);
        return goodsSpecificationMapper.selectByExample(example);
    }

    public LitemallGoodsSpecification findById(Integer id) {
        return goodsSpecificationMapper.selectByPrimaryKey(id);
    }

    public void deleteByGid(Integer gid) {
        LitemallGoodsSpecificationExample example = new LitemallGoodsSpecificationExample();
        example.or().andGoodsIdEqualTo(gid);
        goodsSpecificationMapper.logicalDeleteByExample(example);
    }

    public void add(LitemallGoodsSpecification goodsSpecification) {
        goodsSpecification.setAddTime(LocalDateTime.now());
        goodsSpecification.setUpdateTime(LocalDateTime.now());
        goodsSpecificationMapper.insertSelective(goodsSpecification);
    }

    /**
     * [
     * {
     * name: '',
     * valueList: [ {}, {}]
     * },
     * {
     * name: '',
     * valueList: [ {}, {}]
     * }
     * ]
     *
     * @param id
     * @return
     */
    public Object getSpecificationVoList(Integer id) {
        List<LitemallGoodsSpecification> goodsSpecificationList = queryByGid(id);

        Map<String, VO> map = new HashMap<>();
        List<VO> specificationVoList = new ArrayList<>();

        for (LitemallGoodsSpecification goodsSpecification : goodsSpecificationList) {
            String specification = goodsSpecification.getSpecification();
            VO goodsSpecificationVo = map.get(specification);
            if (goodsSpecificationVo == null) {
                goodsSpecificationVo = new VO();
                goodsSpecificationVo.setName(specification);
                List<LitemallGoodsSpecification> valueList = new ArrayList<>();
                valueList.add(goodsSpecification);
                goodsSpecificationVo.setValueList(valueList);
                map.put(specification, goodsSpecificationVo);
                specificationVoList.add(goodsSpecificationVo);
            } else {
                List<LitemallGoodsSpecification> valueList = goodsSpecificationVo.getValueList();
                valueList.add(goodsSpecification);
            }
        }

        return specificationVoList;
    }

    /**
     * return vo list
     * @param goodsSpecificationList
     * @return
     */
    public List<GoodsSpecificationVO> getVoListBySpecificationList(List<LitemallGoodsSpecification> goodsSpecificationList){
        Map<String, GoodsSpecificationVO> map = new HashMap<>();
        List<GoodsSpecificationVO> specificationVoList = new ArrayList<>();
        if( !ObjectUtils.isEmpty(goodsSpecificationList) ) {
            for (LitemallGoodsSpecification goodsSpecification : goodsSpecificationList) {
                String specification = goodsSpecification.getSpecification();
                GoodsSpecificationVO goodsSpecificationVo = map.get(specification);
                if (goodsSpecificationVo == null) {
                    goodsSpecificationVo = new GoodsSpecificationVO();
                    goodsSpecificationVo.setName(specification);
                    List<LitemallGoodsSpecification> valueList = new ArrayList<>();
                    goodsSpecification.setChecked(false);
                    valueList.add(goodsSpecification);
                    goodsSpecificationVo.setValueList(valueList);
                    map.put(specification, goodsSpecificationVo);
                    specificationVoList.add(goodsSpecificationVo);
                } else {
                    List<LitemallGoodsSpecification> valueList = goodsSpecificationVo.getValueList();
                    goodsSpecification.setChecked(false);
                    valueList.add(goodsSpecification);
                }
            }
        }
        return specificationVoList;
    }

    public void updateById(LitemallGoodsSpecification specification) {
        specification.setUpdateTime(LocalDateTime.now());
        goodsSpecificationMapper.updateByPrimaryKeySelective(specification);
    }

    private class VO {
        private String name;
        private List<LitemallGoodsSpecification> valueList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<LitemallGoodsSpecification> getValueList() {
            return valueList;
        }

        public void setValueList(List<LitemallGoodsSpecification> valueList) {
            this.valueList = valueList;
        }
    }

}
