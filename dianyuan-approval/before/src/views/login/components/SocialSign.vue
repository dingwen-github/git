<template>
  <div class="social-signup-container">
    <!--微信扫码登录-->
    <div class="sign-btn" @click="handleClick('typebd')">
      <span class="wx-svg-container"><svg-icon icon-class="wechat" class="icon" /></span>
    </div>
  </div>
</template>

<script>
// import openWindow from '@/utils/open-window'

export default {
  name: 'SocialSign',
  methods: {
    handleClick(thirdpart) {
      window.location.href = process.env.BASE_API + '/to/oauth2/' + thirdpart
    }
  }
}
</script>

<style lang="scss" scoped>
  .social-signup-container {
    margin: 20px auto;
    .sign-btn {
      display: inline-block;
      cursor: pointer;
    }
    .icon {
      color: #fff;
      font-size: 24px;
      margin-top: 3px;
    }
    .wx-svg-container,
    .qq-svg-container {
      display: inline-block;
      width: 30px;
      height: 30px;
      line-height: 30px;
      text-align: center;
      padding-top: 1px;
      border-radius: 4px;
    }
    .wx-svg-container {
      background-color: #24da70;
    }
    .qq-svg-container {
      background-color: rgba(255, 255, 255, 0.25);
      margin-left: 10px;
    }
  }
</style>
