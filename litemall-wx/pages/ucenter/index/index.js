var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var user = require('../../../utils/user.js');
var app = getApp();

Page({
  data: {
    userInfo: {
      nickName: '点击登录',
      avatarUrl: '/static/images/avatar.png'
    },
    order: {
      unpaid: 0,
      unship: 0,
      unrecv: 0,
      uncomment: 0
    },
    canIUseGetUserProfile: false,
    hasLogin: false
  },
  onLoad: function(options) {
    if (wx.getUserProfile) {
      this.setData({
        canIUseGetUserProfile: true
      })
    }
  },
  onReady: function() {

  },
  onShow: function() {
    //获取用户的登录信息
    if (app.globalData.hasLogin) {
      let userInfo = wx.getStorageSync('userInfo');
      this.setData({
        userInfo: {
          nickName: userInfo.nickName,
          avatarUrl: userInfo.avatarUrl ? userInfo.avatarUrl : this.data.userInfo.avatarUrl
        },
        hasLogin: true
      });
      console.log( this.data.userInfo )

      let that = this;
      util.request(api.UserIndex).then(function(res) {
        if (res.errno === 0) {
          that.setData({
            order: res.data.order
          });
        }
      });
    }

  },
  onHide: function() {
    // 页面隐藏

  },
  onUnload: function() {
    // 页面关闭
  },
  goLogin() {
    if (!this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    }
  },
  goOrder() {
    if (this.data.hasLogin) {
      try {
        wx.setStorageSync('tab', 0);
      } catch (e) {

      }
      wx.navigateTo({
        url: "/pages/ucenter/order/order"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    }
  },
  goOrderIndex(e) {
    if (this.data.hasLogin) {
      let tab = e.currentTarget.dataset.index
      let route = e.currentTarget.dataset.route
      try {
        wx.setStorageSync('tab', tab);
      } catch (e) {

      }
      wx.navigateTo({
        url: route,
        success: function(res) {},
        fail: function(res) {},
        complete: function(res) {},
      })
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  goCoupon() {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/couponList/couponList"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  goGroupon() {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/groupon/myGroupon/myGroupon"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  goCollect() {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/collect/collect"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  goFeedback(e) {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/feedback/feedback"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  goFootprint() {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/footprint/footprint"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  goAddress() {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/address/address"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  bindPhoneNumber: function(e) {
    if (e.detail.errMsg !== "getPhoneNumber:ok") {
      // 拒绝授权
      return;
    }

    if (!this.data.hasLogin) {
      wx.showToast({
        title: '绑定失败：请先登录',
        icon: 'none',
        duration: 2000
      });
      return;
    }

    util.request(api.AuthBindPhone, {
      iv: e.detail.iv,
      encryptedData: e.detail.encryptedData
    }, 'POST').then(function(res) {
      if (res.errno === 0) {
        wx.showToast({
          title: '绑定手机号码成功',
          icon: 'success',
          duration: 2000
        });
      }
    });
  },
  goAfterSale: function() {
    if (this.data.hasLogin) {
      wx.navigateTo({
        url: "/pages/ucenter/aftersaleList/aftersaleList"
      });
    } else {
      wx.navigateTo({
        url: "/pages/auth/login/login"
      });
    };
  },
  aboutUs: function() {
    wx.navigateTo({
      url: '/pages/about/about'
    });
  },
  goHelp: function () {
    wx.navigateTo({
      url: '/pages/help/help'
    });
  },  
  goProfile: function () {
    wx.navigateTo({
      url: '/pages/ucenter/profile/index'
    });
  },  
  wxLogin: function(e) {
    if (this.data.canIUseGetUserProfile) {
      wx.getUserProfile({
        desc: '用于完善会员资料', // 声明获取用户个人信息后的用途，后续会展示在弹窗中，请谨慎填写
        success: (res) => {
          this.doLogin(res.userInfo)
        },
        fail: () => {
          util.showErrorToast('微信登录失败');
        }
      })
    }
    else {
      if (e.detail.userInfo == undefined) {
        app.globalData.hasLogin = false;
        util.showErrorToast('微信登录失败');
        return;
      }
      this.doLogin(e.detail.userInfo)
    }
  },
  doLogin: function(userInfo) {
    user.checkLogin().then((res)=>{
      console.log( res )
    }).catch(() => {
      user.loginByWeixin(userInfo).then(res => {
        app.globalData.hasLogin = true;
        wx.navigateBack({
          delta: 1
        })
      }).catch((err) => {
        app.globalData.hasLogin = false;
        util.showErrorToast('微信登录失败');
      });

    });
  },
  exitLogin: function() {
    wx.showModal({
      title: '',
      confirmColor: 'var(--pink)',
      content: '退出登录？',
      success: function(res) {
        if (!res.confirm) {
          return;
        }

        util.request(api.AuthLogout, {}, 'POST');
        app.globalData.hasLogin = false;
        wx.removeStorageSync('token');
        wx.removeStorageSync('userInfo');
        wx.reLaunch({
          url: '/pages/index/index'
        });
      }
    })

  }
})