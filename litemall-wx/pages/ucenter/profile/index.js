var util = require('../../../utils/util.js');
var api = require('../../../config/api.js');
Page({

  /**
   * 页面的初始数据
   */
  data: {
    nickname:'',
    avatar:'',
    gender:'',
    birthday:'',
    mobile:'',
    addTime:'',
    fileList: [],
    show: false
  },
  getUserInfo: function () {
    util.request(api.UserInfo).then( (res) => {
      if (res.errno === 0) {
        const {nickname, avatar, gender, birthday, mobile, addTime} = res.data;
        this.setData({
          nickname: nickname,
          avatar: avatar,
          gender: gender,
          birthday: birthday,
          mobile: mobile,
          addTime: addTime,
          fileList: avatar ? [{
            url: avatar,
            name: nickname,
            deletable: false
          }] : []
        });
      }

      console.log( this.data.fileList)
    });
  },
  afterRead(event) {
    const { file } = event.detail
    let that = this
    wx.uploadFile({
      url: api.StorageUpload,
      filePath: file.path,
      name: 'file',
      success: (res) => {
        var _res = JSON.parse(res.data);
        if (_res.errno === 0) {
          this.setData({
            fileList: [{
              url: _res.data.url,
              name: this.data.nickname,
              deletable: false
            }],
            avatar: _res.data.url
          })
        }
      },
      fail: function (e) {
        wx.showModal({
          title: '错误',
          content: '上传失败',
          showCancel: false
        })
      },
    })
  },
  previewImage: function (e) {
    wx.previewImage({
      current: e.currentTarget.id, // 当前显示图片的http链接
      urls: this.data.files // 需要预览的图片http链接列表
    })
  },
  onOpenPopup(){
    console.log( 2222 )
    this.setData({ show: true });
  },
  onClosePopup() {
    this.setData({ show: false });
  },
  onConfirm(event){
    const date = new Date(event.detail);
    const year = date.getFullYear();
    const month = (date.getMonth() + 1).toString().padStart(2, '0'); // 月份从0开始，需要加1，并且保证两位数
    const day = date.getDate().toString().padStart(2, '0'); // 保证两位数的日期
    this.setData({birthday: `${year}-${month}-${day}`, show: false})
  },
  onCancel(){
    this.setData({show: false})
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getUserInfo()
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