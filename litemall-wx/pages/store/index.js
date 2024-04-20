var util = require('../../utils/util.js');
var api = require('../../config/api.js');
const app = getApp()
Page({
  data: {
    toView:'',
    heightList:[],
    scrollTopHeight: 0,
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    Custom: app.globalData.Custom,
    list:[],
    goodsMap:{},
    curActive:0,
    cartMap:{},
    cartGoods:[],
    cartTotal:0,
    cartShow: false,
    detailShow: false,
    goodsId: 0,
    goodsName:'',
    goodsPicUrl:'',
    goodsBrief:'',
    attributeList: [],
    specificationList: [],
    productList: [],
    checkedSpecPrice: 0,
    tmpSpecText: '',
    goodsNumber: 1,
    mallNmae: '',
    mallDesc: '',
    mallNotice: '',
    mallBanner: '',
    mallLogo: '',
    mallLatitude: '',
    mallLongitude: '',
    mallPhone: '',
    mallBusinessStartTime: '',
    mallBusinessEndTime: '',
    noticeShow: false,
  },
  onLoad: function (options) {
    this.getMallDetail()
    this.getCatalog()
    this.getCartList()
  },
  getMallDetail(){
    util.request(api.MallDetail).then((res)=>{
      const {name, desc, notice, address, banner, logo, latitude, longitude, phone, businessStartTime, businessEndTime } = res.data
      this.setData({
        mallName: name,
        mallDesc: desc,
        mallNotice: notice,
        mallBanner: banner,
        mallLogo: logo,
        mallAddress: address,
        mallLatitude: latitude,
        mallLongitude: longitude,
        mallPhone: phone,
        mallBusinessStartTime: businessStartTime,
        mallBusinessEndTime: businessEndTime
      });
    });
  },
  getCatalog: function() {
    //CatalogList
    wx.showLoading({
      title: '加载中...',
    });
    util.request(api.StoreGoods).then((res)=>{
      this.setData({
        list: res.data
      });
      this.getProductViewHeight()
      wx.hideLoading()
    });
  },
  getCartList: function() {
    util.request(api.CartList).then((res) => {
      if (res.errno === 0) {
        let cartMap = {}
        res.data.cartList.forEach(element => {
          cartMap[element.goodsId] = element
        })
        this.setData({
          cartGoods: res.data.cartList,
          cartTotal: res.data.cartTotal.goodsAmount,
          cartMap: cartMap,
        })
        if(this.data.cartShow && !res.data.cartList.length){
          this.setData({
            cartShow: false
          })
        }
      }
    });
  },
  clickCategory(e) {
    this.setData({
      curActive: e.currentTarget.dataset.index,
      toView: 'nav' + e.currentTarget.dataset.id
    })
  },
  getProductViewHeight () {
    let tmpHeightList = []
    let tmpH = 0
    const query = wx.createSelectorQuery()
    query.selectAll('.product-block').boundingClientRect()
    query.exec((res)=> {
        res[0].forEach((item) => {
            tmpH += item.height
            // Math.floor()向下取整 【根据个人需要修改】
            tmpHeightList.push(Math.floor(tmpH))
        })
        this.setData({
          heightList:tmpHeightList
        })    
        console.log(tmpHeightList)
    })
  },
  scroll(e) {
    const heightList = this.data.heightList
    let scrollTopHeight = this.data.scrollTopHeight
    if(heightList.length == 0) return
    let scrollTop = e.detail.scrollTop + 10
    let current = this.data.curActive;
    if (scrollTop > scrollTopHeight) {
        //如果右侧 可视区域的竖直滚动位置 超过 当前列表选中项距顶部的高度（且没有下标越界），则更新左侧选中项
        if ((current + 1 < heightList.length) && (scrollTop >= heightList[current])) {
            this.setData({
                curActive: current + 1
            })
        } 
    } else {
        //如果右侧 可视区域的竖直滚动位置 小于 当前列表选中的项距顶部的高度，则更新左侧选中项
        if ((current - 1 >= 0) && (scrollTop < heightList[current - 1])) {
            this.setData({
                curActive: current - 1
            })
        }
    }

    //更新顶部的距离
    this.setData({
      scrollTopHeight: scrollTop
    })
  },
  navigateToIndex(){
    wx.navigateTo({  
      url: '/pages/index/index'  
    });  
  },
  navigateToProductSearch(){
    wx.navigateTo({  
      url: '/pages/search/search'  
    });  
  },
  navigateToProductDetail(event){
    const id = event.currentTarget.dataset.id
    wx.navigateTo({  
      url: '/pages/goods/goods?id=' + id  
    });  
  },
  onTaggleCartPopup: function(){
    if(this.data.cartShow){
      this.onCloseCartPopup()
    } else {
      this.onOpenCartPopup()
    }
  },
  onOpenCartPopup: function(){
    if( this.isObjectEmpty(this.data.cartMap) ){
      return false
    }
    this.setData({cartShow:true})
  },
  onCloseCartPopup: function(){
    this.setData({cartShow:false})
  },
  //添加到购物车
  addCartProduct: function() {
     //提示选择完整规格
    if (!this.isCheckedAllSpec()) {
      util.showErrorToast('请选择完整规格');
      return false;
    }

    //根据选中的规格，判断是否有对应的sku信息
    let checkedProductArray = this.getCheckedProductItem(this.getCheckedSpecKey());
    if (!checkedProductArray || checkedProductArray.length <= 0) {
      //找不到对应的product信息，提示没有库存
      util.showErrorToast('没有库存');
      return false;
    }

    let checkedProduct = checkedProductArray[0];
    console.log( checkedProduct )
    //验证库存
    if (checkedProduct.number <= 0) {
      util.showErrorToast('没有库存');
      return false;
    }

    //添加到购物车
    util.request(api.CartAdd, {
        goodsId: this.data.goodsId,
        number: this.data.goodsNumber,
        productId: checkedProduct.id
      }, "POST")
      .then((res) => {
        if (res.errno == 0) {
          wx.showToast({
            title: '添加成功'
          });

          this.getCartList()
        } else {
          util.showErrorToast(res.errmsg);
        }

      });
  },
  updateCart: function(productId, goodsId, number, id) {
    util.request(api.CartUpdate, {
      productId: productId,
      goodsId: goodsId,
      number: number,
      id: id
    }, 'POST').then((res) => {
      this.getCartList()
    });
  },
  incCartProduct: function(event) {
    const {cart} = event.target.dataset
    this.updateCart(cart.productId, cart.goodsId, cart.number + 1, cart.id);
  },
  decCartProduct: function(event) {
    const {cart} = event.target.dataset
    let number = cart.number - 1;
    if(number > 0){
      this.updateCart(cart.productId, cart.goodsId, number, cart.id);
    } else {
      this.deleteCartProduct([cart.productId])
    }
  },
  deleteCartProduct: function(productIds) {
    util.request(api.CartDelete, {
      productIds: productIds
    }, 'POST').then((res) => {
      if (res.errno === 0) {
        this.getCartList()
      }
    });
  },
  clearCartProduct: function(){
    let productIds = []
    this.data.cartGoods.forEach(element => {
      productIds.push(element.productId)
    });
    this.deleteCartProduct(productIds)
  },
  isObjectEmpty: function( obj ){
    return Object.keys(obj).length === 0 && obj.constructor === Object;
  },
  showProductPopup( event ){
    const product = event.currentTarget.dataset.goods
    this.setData({
      goodsId: product.id,
      goodsName: product.name,
      goodsPicUrl: product.picUrl,
      goodsBrief: product.brief,
      attributeList: product.attributeList,
      specificationList: product.specificationList,
      productList: product.productList,
      checkedSpecPrice: product.retailPrice,
      tmpSpecText: '',
      goodsNumber: 1,
      detailShow: true
    })
  },
  closeProductPopup(){
    this.setData({
      detailShow: false
    })
  },
  // 规格选择
  clickSkuValue: function(event) {
    let specName = event.currentTarget.dataset.name;
    let specValueId = event.currentTarget.dataset.valueId;
    //判断是否可以点击
    //TODO 性能优化，可在wx:for中添加index，可以直接获取点击的属性名和属性值，不用循环
    let _specificationList = this.data.specificationList;
    for (let i = 0; i < _specificationList.length; i++) {
      if (_specificationList[i].name === specName) {
        for (let j = 0; j < _specificationList[i].valueList.length; j++) {
          if (_specificationList[i].valueList[j].id == specValueId) {
            //如果已经选中，则反选
            if (_specificationList[i].valueList[j].checked) {
              _specificationList[i].valueList[j].checked = false;
            } else {
              _specificationList[i].valueList[j].checked = true;
            }
          } else {
            _specificationList[i].valueList[j].checked = false;
          }
        }
      }
    }
    this.setData({
      specificationList: _specificationList
    });
    //重新计算spec改变后的信息
    this.changeSpecInfo();

    //重新计算哪些值不可以点击
  },
  // 规格改变时，重新计算价格及显示信息
  changeSpecInfo: function() {
    let checkedNameValue = this.getCheckedSpecValue();
    //设置选择的信息
    let checkedValue = checkedNameValue.filter(function(v) {
      if (v.valueId != 0) {
        return true;
      } else {
        return false;
      }
    }).map(function(v) {
      return v.valueText;
    });
    if (checkedValue.length > 0) {
      this.setData({
        tmpSpecText: checkedValue.join('　')
      });
    } else {
      this.setData({
        tmpSpecText: '请选择规格数量'
      });
    }

    if (this.isCheckedAllSpec()) {
      this.setData({
        checkedSpecText: this.data.tmpSpecText
      });

      // 规格所对应的货品选择以后
      let checkedProductArray = this.getCheckedProductItem(this.getCheckedSpecKey());
      if (!checkedProductArray || checkedProductArray.length <= 0) {
        this.setData({
          soldout: true
        });
        console.error('规格所对应货品不存在');
        return;
      }

      let checkedProduct = checkedProductArray[0];
      //console.log("checkedProduct: "+checkedProduct.url);
      if (checkedProduct.number > 0) {
        this.setData({
          checkedSpecPrice: checkedProduct.price,
          tmpPicUrl: checkedProduct.url,
          soldout: false
        });
      } else {
        this.setData({
          checkedSpecPrice: this.data.goods.retailPrice,
          soldout: true
        });
      }

    } else {
      this.setData({
        checkedSpecText: '规格数量选择',
        checkedSpecPrice: this.data.goods.retailPrice,
        soldout: false
      });
    }

  },
  //获取选中的规格信息
  getCheckedSpecValue: function() {
    let checkedValues = [];
    let _specificationList = this.data.specificationList;
    for (let i = 0; i < _specificationList.length; i++) {
      let _checkedObj = {
        name: _specificationList[i].name,
        valueId: 0,
        valueText: ''
      };
      for (let j = 0; j < _specificationList[i].valueList.length; j++) {
        if (_specificationList[i].valueList[j].checked) {
          _checkedObj.valueId = _specificationList[i].valueList[j].id;
          _checkedObj.valueText = _specificationList[i].valueList[j].value;
        }
      }
      checkedValues.push(_checkedObj);
    }

    return checkedValues;
  },
  //判断规格是否选择完整
  isCheckedAllSpec: function() {
    return !this.getCheckedSpecValue().some(function(v) {
      if (v.valueId == 0) {
        return true;
      }
    });
  },

  getCheckedSpecKey: function() {
    let checkedValue = this.getCheckedSpecValue().map(function(v) {
      return v.valueText;
    });
    return checkedValue;
  },

    // 获取选中的产品（根据规格）
  getCheckedProductItem: function(key) {
    return this.data.productList.filter(function(v) {
      if (v.specifications.toString() == key.toString()) {
        return true;
      } else {
        return false;
      }
    });
  },
  decProductNumber: function(){
    const goodsNumber = this.data.goodsNumber
    if(goodsNumber == 1){
      util.showErrorToast('不能在减少啦～')
      return false
    }
    this.setData({
      goodsNumber : goodsNumber - 1
    })
  },
  incProductNumber: function(){
    const goodsNumber = this.data.goodsNumber
    this.setData({
      goodsNumber : goodsNumber + 1
    })
  },

  checkoutOrder: function() {
    //获取已选择的商品
    if(!this.data.cartGoods.length){
      return false
    }
    wx.navigateTo({
      url: '/pages/checkout/checkout'
    })
  },
  onOpenNoticePopup: function(){
    this.setData({noticeShow: true})
  },
  onCloseNoticePopup: function(){
    this.setData({noticeShow: false})
  },
  showLocation: function (e) {
    wx.openLocation({
      latitude: parseFloat(this.data.mallLatitude),
      longitude: parseFloat(this.data.mallLongitude),
      name: this.data.mallNmae,
      address: this.data.mallAddress,
    })
  },
  callPhone: function (e) {
    wx.makePhoneCall({
      phoneNumber: this.data.mallPhone,
    })
  },
  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady: function () {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow: function () {
    console.log( 222222 )
  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide: function () {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload: function () {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh: function () {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom: function () {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage: function () {

  }
})