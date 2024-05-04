/**
 * 用户相关服务
 */
const util = require('../utils/util.js');
const api = require('../config/api.js');
var app = getApp();

// 微信小程序登录方法
function wxLogin() {
  return new Promise((resolve, reject) => {
    // 调用微信登录接口
    wx.login({
      success: (res) => {
        if (res.code) {
          resolve(res.code); // 登录成功，返回登录凭证code
        } else {
          reject("微信登录失败：" + res.errMsg);
        }
      },
      fail: (error) => {
        reject("微信登录调用失败：" + error.errMsg);
      },
    });
  });
}

//服务端登录
function loginServe(code) {
  return new Promise((resolve, reject) => {
    util.request(api.AuthLoginByWeixin, {code: code}, 'POST').then(res => {
      if (res.errno === 0) {
        resolve(res.data);
      } else {
        reject(res);
      }
    })
  });
}
//服务端退出
function logoutServe(params) {
  return new Promise((resolve, reject) => {
    util.request(api.AuthLogout, {}, 'POST').then(res => {
      if (res.errno === 0) {
        resolve(res);
      } else {
        reject(res);
      }
    })
  });
}

//登录
async function login() {
  
  try {
    // 微信小程序登录
    const code = await wxLogin();
    // 服务端登录
    const data = await loginServe(code);
    //存储用户信息
    const {userInfo, token} = data
    console.log( 4 )
    wx.setStorageSync('userInfo', userInfo);
    wx.setStorageSync('token', token);
    app.globalData.hasLogin = true;
    return userInfo;
  } catch (error) {
    console.error("发生错误:", error);
    //删除用户信息
    wx.removeStorageSync('userInfo');
    wx.removeStorageSync('token');
    app.globalData.hasLogin = false;
    util.showErrorToast('登录失败');
    return false;
  }
}


function logout() {
  try { 
    wx.showModal({
      title: '',
      confirmColor: 'var(--pink)',
      content: '退出登录？',
      success: function(res) {
        if (!res.confirm) {
          return;
        }
        logoutServe().then( res => {
          app.globalData.hasLogin = false;
          wx.removeStorageSync('token');
          wx.removeStorageSync('userInfo');
          wx.reLaunch({
            url: '/pages/index/index'
          });
        });
      }
    })
  } catch (error) {
    util.showErrorToast('退出失败');
  }
}


module.exports = {
  login,
  logout
};