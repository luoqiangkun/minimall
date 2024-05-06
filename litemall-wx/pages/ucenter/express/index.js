var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
Page({

    /**
     * 页面的初始数据
     */
    data: {
      orderSn:'',
      expressList:[],
      logisticsData: [
        { time: '2024-05-01 10:00', place: '发件地点1' },
        { time: '2024-05-02 12:00', place: '中转地点1' },
        { time: '2024-05-03 14:00', place: '中转地点2' },
        { time: '2024-05-04 16:00', place: '到达目的地' },
      ],
    },
    getExpress: function() {
      wx.showLoading({
        title: '加载中',
      });

      setTimeout(function() {
        wx.hideLoading()
      }, 2000);

      util.request(api.OrderExpress, {
        orderSn: this.data.orderSn
      }).then((res) => {
        if (res.errno === 0) {
          this.setData({
            expressList: res.data && res.data.length > 0 ? res.data.reverse() : []
          });
        }
  
        wx.hideLoading();
      });
    },
    /**
     * 生命周期函数--监听页面加载
     */
    onLoad(options) {
    
      this.setData({
        orderSn: '20240504223368'
      });

      this.getExpress()
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