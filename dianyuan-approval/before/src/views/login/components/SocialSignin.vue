<template>
  <div class="social-signup-container">
    <!--<div class="sign-btn" @click="handleClick('wechat')">
      <span class="wx-svg-container"><svg-icon icon-class="wechat" class="icon" /></span>
      WeChat
    </div>-->
    <div class="sign-btn" @click="handleClick('github')">
      <span class="qq-svg-container"><svg-icon icon-class="GitHub" class="icon" /></span>
      GitHub
    </div>
    <div class="sign-btn" @click="handleClick('typebd')">
      <span class="qq-svg-container"><svg-icon icon-class="baidu" class="icon" /></span>
      baidu
    </div>
    <div class="sign-btn" @click="handleClick('oschina')">
      <span class="qq-svg-container"><svg-icon icon-class="oschina" class="icon" /></span>
      oschina
    </div>
  </div>
</template>

<script>
// import openWindow from '@/utils/open-window'

export default {
  name: 'SocialSignin',
  methods: {
    handleClick(thirdpart) {
      window.location.href = process.env.BASE_API + '/to/oauth2/' + thirdpart
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
      font-size: 24px;
      margin-top: 8px;
    }
    .wx-svg-container,
    .qq-svg-container {
      display: inline-block;
      width: 40px;
      height: 40px;
      line-height: 40px;
      text-align: center;
      padding-top: 1px;
      border-radius: 4px;
      margin-bottom: 20px;
      margin-right: 5px;
    }
    .wx-svg-container {
      background-color: #24da70;
    }
    .qq-svg-container {
      background-color: #cccfda;
      margin-left: 50px;
    }
  }
</style>
