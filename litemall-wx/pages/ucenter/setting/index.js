var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
var auth = require('../../../utils/auth.js');
var app = getApp();
Page({

    /**
     * 页面的初始数据
     */
    data: {
      hasLogin : false
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
    exitLogin: function() {
      auth.logout();
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
      if (app.globalData.hasLogin) {
        this.setData({
          hasLogin: true
        });
      }
    },

    /**
     * 生命周期函数--监听页面初次渲染完成
     */
    onReady() {

    },
    onShow: function() {
     
    },

    /**
     * 生命周期函数--监听页面隐藏
     */
    onHide() {

    },

    /**
     * 生命周期函数--监听页面卸载
     */
    onUnload() {

    },

    /**
     * 页面相关事件处理函数--监听用户下拉动作
     */
    onPullDownRefresh() {

    },

    /**
     * 页面上拉触底事件的处理函数
     */
    onReachBottom() {

    },

    /**
     * 用户点击右上角分享
     */
    onShareAppMessage() {

    }
})