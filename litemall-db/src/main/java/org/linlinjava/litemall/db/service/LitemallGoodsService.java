package org.linlinjava.litemall.db.service;

import com.github.pagehelper.PageHelper;
import org.linlinjava.litemall.db.dao.*;
import org.linlinjava.litemall.db.domain.*;
import org.linlinjava.litemall.db.domain.LitemallGoods.Column;
import org.linlinjava.litemall.db.vo.CategoryGoodsVO;
import org.linlinjava.litemall.db.vo.GoodsDetailVO;
import org.linlinjava.litemall.db.vo.GoodsSpecificationVO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.IntStream;

@Service
public class LitemallGoodsService {
    Column[] columns = new Column[]{Column.id, Column.name, Column.brief, Column.picUrl, Column.isHot, Column.isNew, Column.counterPrice, Column.retailPrice};
    @Resource
    private LitemallGoodsMapper goodsMapper;

    @Resource
    private LitemallCategoryMapper categoryMapper;

    @Resource
    private LitemallGoodsAttributeMapper goodsAttributeMapper;

    @Resource
    private LitemallGoodsSpecificationMapper goodsSpecificationMapper;

    @Resource
    private LitemallGoodsProductMapper litemallGoodsProductMapper;

    @Resource
    private LitemallGoodsSpecificationService litemallGoodsSpecificationService;

    /**
     * 获取热卖商品
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<LitemallGoods> queryByHot(int offset, int limit) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andIsHotEqualTo(true).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取新品上市
     *
     * @param offset
     * @param limit
     * @return
     */
    public List<LitemallGoods> queryByNew(int offset, int limit) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andIsNewEqualTo(true).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取分类下的商品
     *
     * @param catList
     * @param offset
     * @param limit
     * @return
     */
    public List<LitemallGoods> queryByCategory(List<Integer> catList, int offset, int limit) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andCategoryIdIn(catList).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time  desc");
        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }


    /**
     * 获取分类下的商品
     *
     * @param catId
     * @param offset
     * @param limit
     * @return
     */
    public List<LitemallGoods> queryByCategory(Integer catId, int offset, int limit) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andCategoryIdEqualTo(catId).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        example.setOrderByClause("add_time desc");
        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }


    public List<LitemallGoods> querySelective(Integer catId, Integer brandId, String keywords, Boolean isHot, Boolean isNew, Integer offset, Integer limit, String sort, String order) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        LitemallGoodsExample.Criteria criteria1 = example.or();
        LitemallGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(catId) && catId != 0) {
            criteria1.andCategoryIdEqualTo(catId);
            criteria2.andCategoryIdEqualTo(catId);
        }
        if (!StringUtils.isEmpty(brandId)) {
            criteria1.andBrandIdEqualTo(brandId);
            criteria2.andBrandIdEqualTo(brandId);
        }
        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(offset, limit);

        return goodsMapper.selectByExampleSelective(example, columns);
    }

    public List<LitemallGoods> querySelective(Integer goodsId, String goodsSn, String name, Integer page, Integer size, String sort, String order) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        LitemallGoodsExample.Criteria criteria = example.createCriteria();

        if (goodsId != null) {
            criteria.andIdEqualTo(goodsId);
        }
        if (!StringUtils.isEmpty(goodsSn)) {
            criteria.andGoodsSnEqualTo(goodsSn);
        }
        if (!StringUtils.isEmpty(name)) {
            criteria.andNameLike("%" + name + "%");
        }
        criteria.andDeletedEqualTo(false);

        if (!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(order)) {
            example.setOrderByClause(sort + " " + order);
        }

        PageHelper.startPage(page, size);
        return goodsMapper.selectByExampleWithBLOBs(example);
    }

    /**
     * 获取某个商品信息,包含完整信息
     *
     * @param id
     * @return
     */
    public LitemallGoods findById(Integer id) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andIdEqualTo(id).andDeletedEqualTo(false);
        return goodsMapper.selectOneByExampleWithBLOBs(example);
    }

    /**
     * 获取某个商品信息，仅展示相关内容
     *
     * @param id
     * @return
     */
    public LitemallGoods findByIdVO(Integer id) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andIdEqualTo(id).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return goodsMapper.selectOneByExampleSelective(example, columns);
    }


    /**
     * 获取所有在售物品总数
     *
     * @return
     */
    public Integer queryOnSale() {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return (int) goodsMapper.countByExample(example);
    }

    public int updateById(LitemallGoods goods) {
        goods.setUpdateTime(LocalDateTime.now());
        return goodsMapper.updateByPrimaryKeySelective(goods);
    }

    public void deleteById(Integer id) {
        goodsMapper.logicalDeleteByPrimaryKey(id);
    }

    public void add(LitemallGoods goods) {
        goods.setAddTime(LocalDateTime.now());
        goods.setUpdateTime(LocalDateTime.now());
        goodsMapper.insertSelective(goods);
    }

    /**
     * 获取所有物品总数，包括在售的和下架的，但是不包括已删除的商品
     *
     * @return
     */
    public int count() {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andDeletedEqualTo(false);
        return (int) goodsMapper.countByExample(example);
    }

    public List<Integer> getCatIds(Integer brandId, String keywords, Boolean isHot, Boolean isNew) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        LitemallGoodsExample.Criteria criteria1 = example.or();
        LitemallGoodsExample.Criteria criteria2 = example.or();

        if (!StringUtils.isEmpty(brandId)) {
            criteria1.andBrandIdEqualTo(brandId);
            criteria2.andBrandIdEqualTo(brandId);
        }
        if (!StringUtils.isEmpty(isNew)) {
            criteria1.andIsNewEqualTo(isNew);
            criteria2.andIsNewEqualTo(isNew);
        }
        if (!StringUtils.isEmpty(isHot)) {
            criteria1.andIsHotEqualTo(isHot);
            criteria2.andIsHotEqualTo(isHot);
        }
        if (!StringUtils.isEmpty(keywords)) {
            criteria1.andKeywordsLike("%" + keywords + "%");
            criteria2.andNameLike("%" + keywords + "%");
        }
        criteria1.andIsOnSaleEqualTo(true);
        criteria2.andIsOnSaleEqualTo(true);
        criteria1.andDeletedEqualTo(false);
        criteria2.andDeletedEqualTo(false);

        List<LitemallGoods> goodsList = goodsMapper.selectByExampleSelective(example, Column.categoryId);
        List<Integer> cats = new ArrayList<Integer>();
        for (LitemallGoods goods : goodsList) {
            cats.add(goods.getCategoryId());
        }
        return cats;
    }

    public boolean checkExistByName(String name) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andNameEqualTo(name).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return goodsMapper.countByExample(example) != 0;
    }

    public List<LitemallGoods> queryByIds(Integer[] ids) {
        LitemallGoodsExample example = new LitemallGoodsExample();
        example.or().andIdIn(Arrays.asList(ids)).andIsOnSaleEqualTo(true).andDeletedEqualTo(false);
        return goodsMapper.selectByExampleSelective(example, columns);
    }

    /**
     * 获取分类商品详细信息
     * @return
     */
    public List<CategoryGoodsVO> getCategoryWithGoodsList(){
        //获取所有的商品分类
        LitemallCategoryExample litemallCategoryExample = new LitemallCategoryExample();
        litemallCategoryExample.or().andPidEqualTo(0).andDeletedEqualTo(false);
        List<LitemallCategory> categoryList = categoryMapper.selectByExample(litemallCategoryExample);
        //获取所有的商品
        LitemallGoodsExample goodsExample = new LitemallGoodsExample();
        goodsExample.or().andLogicalDeleted(false).andIsOnSaleEqualTo(true);
        List<LitemallGoods> goodsList = goodsMapper.selectByExample(goodsExample);
        List<Integer> goodsIdList = Arrays.asList(goodsList.stream().mapToInt(item -> item.getId()).boxed().toArray(Integer[]::new));
        //获取所有的规格
        LitemallGoodsAttributeExample attributeExample = new LitemallGoodsAttributeExample();
        attributeExample.or().andGoodsIdIn(goodsIdList);
        List<LitemallGoodsAttribute> attributeList = goodsAttributeMapper.selectByExample(attributeExample);
        //获取所有的属性
        LitemallGoodsSpecificationExample specificationExample = new LitemallGoodsSpecificationExample();
        specificationExample.or().andGoodsIdIn(goodsIdList);
        List<LitemallGoodsSpecification> specificationList = goodsSpecificationMapper.selectByExample(specificationExample);
        //获取所有的sku
        LitemallGoodsProductExample productExample = new LitemallGoodsProductExample();
        productExample.or().andGoodsIdIn(goodsIdList);
        List<LitemallGoodsProduct> productList = litemallGoodsProductMapper.selectByExample(productExample);
        //属性按商品进行分组
        Map<Integer,List<LitemallGoodsAttribute>> attributeMap = new HashMap<>();
        for (LitemallGoodsAttribute attribute: attributeList){
            List<LitemallGoodsAttribute> attributes = attributeMap.get(attribute.getGoodsId());
            if(attributes == null){
                attributes = new ArrayList<>();
            }
            attributes.add(attribute);
            attributeMap.put(attribute.getGoodsId(),attributes);
        }
        //规格按商品进行分组
        Map<Integer,List<LitemallGoodsSpecification>> specificationMap = new HashMap<>();
        for (LitemallGoodsSpecification specification : specificationList){
            List<LitemallGoodsSpecification> specifications = specificationMap.get(specification.getGoodsId());
            if(specifications == null){
                specifications = new ArrayList<>();
            }
            specifications.add(specification);
            specificationMap.put(specification.getGoodsId(),specifications);
        }
        //sku按商品进行分组
        Map<Integer,List<LitemallGoodsProduct>> productMap = new HashMap<>();
        for ( LitemallGoodsProduct product : productList){
            List<LitemallGoodsProduct> products = productMap.get(product.getGoodsId());
            if(products == null){
                products = new ArrayList<>();
            }
            products.add(product);
            productMap.put(product.getGoodsId(),products);
        }
        Map<Integer,List<GoodsDetailVO>> goodsMap = new HashMap<>();
        for (LitemallGoods goods : goodsList){
            List<GoodsDetailVO> goodsDetailVOList = goodsMap.get(goods.getCategoryId());
            if(goodsDetailVOList == null){
                goodsDetailVOList = new ArrayList<>();
            }
            GoodsDetailVO goodsDetailVO = new GoodsDetailVO();
            goodsDetailVO.setId(goods.getId());
            goodsDetailVO.setName(goods.getName());
            goodsDetailVO.setCategoryId(goods.getCategoryId());
            goodsDetailVO.setBrandId(goods.getBrandId());
            goodsDetailVO.setGallery(goods.getGallery());
            goodsDetailVO.setKeywords(goods.getKeywords());
            goodsDetailVO.setBrief(goods.getBrief());
            goodsDetailVO.setOnSale(goods.getIsOnSale());
            goodsDetailVO.setPicUrl(goods.getPicUrl());
            goodsDetailVO.setShareUrl(goods.getShareUrl());
            goodsDetailVO.setNew(goods.getIsNew());
            goodsDetailVO.setHot(goods.getIsHot());
            goodsDetailVO.setUnit(goods.getUnit());
            goodsDetailVO.setCounterPrice(goods.getCounterPrice());
            goodsDetailVO.setRetailPrice(goods.getRetailPrice());
            goodsDetailVO.setDetail(goods.getDetail());
            goodsDetailVO.setAttributeList(attributeMap.get(goods.getId()));
            List<LitemallGoodsSpecification> goodsSpecificationList = specificationMap.get(goods.getId());
            List<GoodsSpecificationVO> goodsSpecificationVOList = litemallGoodsSpecificationService.getVoListBySpecificationList(goodsSpecificationList);
            goodsDetailVO.setSpecificationList(goodsSpecificationVOList);
            goodsDetailVO.setProductList(productMap.get(goods.getId()));
            goodsDetailVOList.add(goodsDetailVO);
            goodsMap.put(goods.getCategoryId(),goodsDetailVOList);
        }
        List<CategoryGoodsVO> categoryGoodsVOList = new ArrayList<>();
        for (LitemallCategory category: categoryList){
            List<GoodsDetailVO> goodsDetailVOList = goodsMap.get(category.getId());
            if(goodsDetailVOList == null){
                continue;
            }
            CategoryGoodsVO categoryGoodsVO = new CategoryGoodsVO();
            categoryGoodsVO.setId(category.getId());
            categoryGoodsVO.setName(category.getName());
            categoryGoodsVO.setKeywords(category.getKeywords());
            categoryGoodsVO.setDesc(category.getDesc());
            categoryGoodsVO.setPid(category.getPid());
            categoryGoodsVO.setIconUrl(category.getIconUrl());
            categoryGoodsVO.setPicUrl(category.getPicUrl());
            categoryGoodsVO.setLevel(category.getLevel());
            categoryGoodsVO.setSortOrder(category.getSortOrder());
            categoryGoodsVO.setGoodsList(goodsDetailVOList);
            categoryGoodsVOList.add(categoryGoodsVO);
        }
        return categoryGoodsVOList;
    }

}
