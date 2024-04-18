// pages/demo/index.js
const app = getApp()
Page({

  /**
   * 页面的初始数据
   */
  data: {
    StatusBar: app.globalData.StatusBar,
    CustomBar: app.globalData.CustomBar,
    Custom: app.globalData.Custom,
    richText: '	<p>触发条件：指定部门提交隐患</p><p>触发条件：指定部门提交隐患</p><p>触发条件：指定部门提交隐</p>',
    latitude: 39.90469,
    longitude: 116.40717,
    noticeShow: false
  },
  onOpenNoticePopup() {
    this.setData({noticeShow:true})
  },
  onCloseNoticePopup() {
    this.setData({noticeShow:false})
  },
  showLocation: function (e) {
    wx.openLocation({
      latitude: parseFloat(this.data.latitude),
      longitude: parseFloat(this.data.longitude),
      name: '北京市政府',
      address:'北京市东城区东长安街',
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

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