<template>
  <div class="login-container">
    <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form" auto-complete="on" label-position="left">
      <h3 class="title">WeiPro管理中心</h3>
      <el-form-item prop="username">
        <span class="svg-container">
          <svg-icon icon-class="user" />
        </span>
        <el-input 
        v-model="loginForm.username" 
        name="username" 
        type="text" 
        auto-complete="on" 
        maxlength="10"
        clearable
        placeholder="用户名" />
      </el-form-item>
      <el-form-item prop="password" >
        <span class="svg-container">
          <svg-icon icon-class="password" />
        </span>
        <el-input
          :type="pwdType"
          v-model="loginForm.password"
          name="password"
          auto-complete="on"
          clearable
          maxlength="10"
          placeholder="密码"
          @keyup.enter.native="handleLogin" />
        <span class="show-pwd" @click="showPwd">
          <svg-icon icon-class="eye" />
        </span>
      </el-form-item>
      <el-form-item style="margin-top:50px">
        <el-button :loading="loading" type="primary" style="width:100%" @click.native.prevent="handleLogin">
          登录
        </el-button>
      </el-form-item>
    </el-form>

      <el-dialog
        title="第三方验证"
        :visible.sync="dialogVisible"
        append-to-body=true
        :before-close="handleClose">
        <span>请选择快捷验证方式</span>
        <br>
        <SocialSignin/>
      </el-dialog>
  </div>

</template>

<script>
import { isvalidUsername } from '@/utils/validate'
import { getQueryObject } from '@/utils/index'
import SocialSignin from "./components/SocialSignIn";

export default {
  name: 'Login',
  components: {SocialSignin},
  data() {
      const validatePass = (rule, value, callback) => {
        if (value.length < 4) {
          callback(new Error('密码不能小于5位'))
        } else {
          callback()
        }
      }

    const validateUsername = (rule, value, callback) => {
      if (!isvalidUsername(value)) {
        callback(new Error('请输入正确的用户名'))
      } else {
        callback()
      }
    }

    return {
      loginForm: {
        username: 'admin',
        password: 'admin'
      },
      loginRules: {
        username: [{ required: true, trigger: 'blur', validator: validateUsername }],
        password: [{ required: true, trigger: 'blur', validator: validatePass }]
      },
      loading: false,
      pwdType: 'password',
      redirect: undefined,
      dialogVisible: false,
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  //添加监听存储事件
  created() {
     window.addEventListener('storage', this.afterQRScan)
  },
  destroyed() {
    window.removeEventListener('storage', this.afterQRScan)
  },
  methods: {
      wpen(){
         window.open("",'wechat','toolbar=0, location=0, menubar=0, scrollbars=0, titlebar=0, status=0, width=500, height=500, left=450')
      },
      TestDialog() {
      this.dialogVisible = true
      },
    showPwd() {
      if (this.pwdType === 'password') {
        this.pwdType = ''
      } else {
        this.pwdType = 'password'
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('Login', this.loginForm).then(() => {
            this.loading = false
            //this.$router.push({ path: '/' })
            this.dialogVisible = true
          }).catch(() => {
            this.loading = false
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    //处理第三方登陆
    afterQRScan(e) {
      if (e.key === 'x-admin-oauth-code') {
        console.log('第三方type:' + this.$store.getters.authtype)
        const codeval = getQueryObject(e.newValue)
        const platform = {
          code : codeval['code'],
          type : this.$store.getters.authtype
        }
        if (platform.code) {
            this.$store.dispatch('LoginCheck', platform).then(() => {
            //this.$router.push({ path: this.redirect || '/' })
            this.$router.push({ path: '/' })
          }).catch(() => {
              this.loading = false
            })
        } else {
          alert('第三方登录失败')
          console.log('login fail')
          return false
        }
      }
    }
  }

}
</script>

<style rel="stylesheet/scss" lang="scss">
$bg:#2d3a4b;
$light_gray:#eee;

/* reset element-ui css */
.login-container {
  .el-input {
    display: inline-block;
    height: 47px;
    width: 85%;
    input {
      background: transparent;
      border: 0px;
      -webkit-appearance: none;
      border-radius: 0px;
      padding: 12px 5px 12px 15px;
      color: $light_gray;
      height: 47px;
      &:-webkit-autofill {
        -webkit-box-shadow: 0 0 0px 1000px $bg inset !important;
        -webkit-text-fill-color: #fff !important;
      }
    }
  }
  .el-form-item {
    border: 1px solid rgba(255, 255, 255, 0.1);
    background: rgba(0, 0, 0, 0.1);
    border-radius: 5px;
    color: #454545;
  }
}

</style>

<style rel="stylesheet/scss" lang="scss" scoped>
$bg:#2d3a4b;
$dark_gray:#889aa4;
$light_gray:#eee;
.login-container {
  position: fixed;
  height: 100%;
  width: 100%;
  background-color: $bg;
  .login-form {
    position: absolute;
    left: 0;
    right: 0;
    width: 520px;
    max-width: 100%;
    padding: 35px 35px 15px 35px;
    margin: 120px auto;
  }
  .tips {
    font-size: 14px;
    color: #fff;
    margin-bottom: 10px;
    span {
      &:first-of-type {
        margin-right: 16px;
      }
    }
  }
  .svg-container {
    padding: 6px 5px 6px 15px;
    color: $dark_gray;
    vertical-align: middle;
    width: 30px;
    display: inline-block;
  }
  .title {
    font-size: 32px;
    font-weight: 400;
    color: $light_gray;
    margin: 0px auto 100px auto;
    text-align: center;
  }
  .show-pwd {
    position: absolute;
    right: 10px;
    top: 7px;
    font-size: 16px;
    color: $dark_gray;
    cursor: pointer;
    user-select: none;
  }


}
</style>
