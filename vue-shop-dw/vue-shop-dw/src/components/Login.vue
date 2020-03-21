<template>
  <div class="login_container">
   <div class="login_box">
<!--     头像区域-->
     <div class="avatar_box">
       <img src="../assets/logo.png" align="avatar">
     </div>
<!--     登录表单-->
     <div>
       <el-form label-width="0px"
                class="login_form"
                :model="loginForm"
                :rules="loginFormRules"
                ref="loginFormRef"
       >
<!--         用户名-->
         <el-form-item prop="username">
           <el-input prefix-icon=" iconfont icon-user" v-model="loginForm.username"></el-input>
         </el-form-item>
<!--       密码  -->
           <el-form-item prop="password">
           <el-input type="password" prefix-icon="iconfont icon-3702mima" v-model="loginForm.password"></el-input>
         </el-form-item>
<!--         按钮-->
         <el-form-item class="btn">
           <el-button type="primary" @click="login">登录</el-button>
           <el-button @click="resetLoginForm">重置</el-button>
         </el-form-item>
       </el-form>
     </div>
   </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      // 这是登录表单的数据对象
      loginForm: {
        username: 'admin',
        password: '123456'
      },
      // 这是验证规则的对象
      loginFormRules: {
        // 用户名验证规则
        username: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 3 到 10 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 3, max: 10, message: '长度在 6 到 10 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  //  方法
  methods: {
    resetLoginForm () {
      //  重置表单
      this.$refs.loginFormRef.resetFields()
    },
    login () {
      //  登录
      //  使用await和asyns简化promise,但是在最近的方法要用async修饰，前提是方法的返回值是Promise对象
      this.$refs.loginFormRef.validate(async valid => {
        //  console.log(valid)
        // 如果验证通过,则发起axios请求
        if (valid) {
          //   const { data: res }:获取到data数据
          const { data: res } = await this.$http.post('login', this.loginForm)
          //  console.log(res)
          if (res.meta.status !== 200) {
            return this.$message.error('登录失败！')// console.log('登录失败');
          } else {
            this.$message.success('登录成功')//  console.log('登录成功');
            //  将登录成功的token,保存到sessionStorage中
            window.sessionStorage.setItem('token', res.data.token)
            // 通过编程式导航跳转到后台主页/home
            this.$router.push('/home')
          }
        }
      })
    }
  }
}
</script>
<!--scoped：在当前组件样式生效-->
<style lang="less" scoped>
  .login_container{
    background-color: #2b4b6b;
    height: 100%;
  }

.login_box{
  width: 450px;
  height: 360px;
  background-color: #fff;
  border-radius: 3px;
  position: absolute;
  left: 50%;
  top: 50%;
  -webkit-transform: translate(-50%,-50%);
}
  .avatar_box{
    width: 130px;
    height: 130px;
    border: 1px solid #eee;
    border-radius: 50%;
    padding: 10px;
    /*水平 垂直 模糊距离 颜色*/
    box-shadow: 0 0 10px #ddd;
    position: absolute;
    left: 50%;
    -webkit-transform: translate(-50%,-50%);
    background-color: #fff;

    img{
      width: 100%;
      height: 100%;
      border-radius: 50%;
      background-color: #eee;
    }
  }

  .login_form{
    position: absolute;
    bottom: 60px;
    width: 100%;
    /*上下边距0 左右边距10*/
    padding: 0 10px;
    /*为元素指定的任何内边距和边框都将在已设定的宽度和高度内进行绘制*/
    box-sizing: border-box;

  }
  .btn{
    display: flex;
    justify-content: center;
  }
</style>
