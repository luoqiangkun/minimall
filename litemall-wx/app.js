App({
  onLaunch: function() {
    Promise.prototype.finally = function(callback){
      let P = this.constructor;
      return this.then(
        value => {
              P.resolve(callback()).then(() => value)
          },
          reason => {
              P.resolve(callback()).then(() => { throw reason })
          }
      )
    }
    const updateManager = wx.getUpdateManager();
    wx.getUpdateManager().onUpdateReady(function() {
      wx.showModal({
        title: '更新提示',
        content: '新版本已经准备好，是否重启应用？',
        success: function(res) {
          if (res.confirm) {
            // 新的版本已经下载好，调用 applyUpdate 应用新版本并重启
            updateManager.applyUpdate()
          }
        }
      })
    })

    wx.getSystemInfo({
      success: e => {
        this.globalData.StatusBar = e.statusBarHeight;
        let custom = wx.getMenuButtonBoundingClientRect();
        this.globalData.Custom = custom;  
        this.globalData.CustomBar = custom.bottom + custom.top - e.statusBarHeight;
      }
    })

    const token = wx.getStorageSync('token');
    if (token) {
      // 如果缓存中存在 token，则设置 hasLogin 为 true
      this.globalData.hasLogin = true;
    }

  },
  globalData: {
    hasLogin: false
  }
})