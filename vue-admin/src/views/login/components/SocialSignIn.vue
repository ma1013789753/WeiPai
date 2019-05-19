<template>
  <div class="social-signup-container">
    <div class="sign-btn" @click="wechatHandleClick('wechat')">
      <span class="wx-svg-container"><svg-icon icon-class="wechat" class="icon" /></span>
      微信
    </div>
    <div class="sign-btn" >
      <span class="wx-svg-container"><svg-icon icon-class="sina" class="icon" /></span>
      微博
    </div>
  </div>
</template>

<script>
   import openWindow from '@/utils/open-window'

  export default {
    name: 'SocialSignin',
    methods: {
      wechatHandleClick(thirdpart) {
        this.$store.commit('SET_AUTHTYPE', thirdpart)
        const appid = 'wxa4a19403389e6c81'
        const redirect_uri = encodeURIComponent('http://m.weiyoua.com/#/redirect/auth-redirect')
        const url = 'https://open.weixin.qq.com/connect/qrconnect?appid=' + appid + '&redirect_uri=' + redirect_uri + '&response_type=code&scope=snsapi_login&state=wechat'
         openWindow(url, thirdpart, 540, 540)
      }
    }
  }
</script>

<style lang="scss" scoped>
  .social-signup-container {
    margin: 20px 0;
  .sign-btn {
    display: inline-block;
    cursor: pointer;
  }
  .icon {
    color: #fff;
    font-size: 40px;
    margin-top: 8px;
  }
  .wx-svg-container,
  .wx-svg-container {
    //background-color: #24da70;
  }
  }
</style>

