<template>
  <el-menu class="navbar" mode="horizontal">
    <hamburger :toggle-click="toggleSideBar" :is-active="sidebar.opened" class="hamburger-container"/>
    <breadcrumb />
    <el-dropdown class="avatar-container" trigger="click">
      <div class="avatar-wrapper">
        <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">
        <i class="el-icon-caret-bottom"/>
      </div>
      <el-dropdown-menu slot="dropdown" class="user-dropdown">
        <router-link class="inlineBlock" to="/">
          <el-dropdown-item>主页</el-dropdown-item>
        </router-link>
         <el-dropdown-item divided>
          <span style="display:block;" @click="showdia">修改密码</span>
        </el-dropdown-item>

        <el-dropdown-item divided>
          <span style="display:block;" @click="logout">注销登录</span>
        </el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    <el-dialog title="重置密码" :visible.sync="dialogTableVisible" lock-scroll height="60%">
      <template center>
        <el-form ref="form" class="demo-form-inline" :model="form" :rules="rules">
          <!-- <el-form-item label="旧密码:">
            <el-input v-model="oldPassword" placeholder="请输入旧密码" size="medium"></el-input>
          </el-form-item> -->
          <el-form-item label="新密码:"  prop="newPassword">
            <el-input type="password" v-model=" form.newPassword" placeholder="请输入新密码" size="medium" ></el-input>
          </el-form-item>
          <el-form-item label="新密码:" prop="checkPassword">
             <el-input type="password" v-model="form.checkPassword" placeholder="请再次输入新密码" size="medium" ></el-input>
          </el-form-item>
        </el-form>
      </template>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="resetPsw">确 定</el-button>
      </span>
    </el-dialog>

  </el-menu>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import { reset } from '@/api/sysuser'

export default {
  components: {
    Breadcrumb,
    Hamburger
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'nickname'
    ])
  },
  data(){
    return{
      dialogTableVisible:false,
      form:{
        newPassword:'',
        checkPassword:'',
      },
      rules: {
        newPassword: [
          { required: true, message: '请输入您的新密码', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
        checkPassword: [
          { required: true, message: '请再次输入您的新密码', trigger: 'blur' },
          { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
        ],
      }

    }
  },
  methods: {
    toggleSideBar() {
      this.$store.dispatch('ToggleSideBar')
    },
    logout() {
      this.$store.dispatch('LogOut').then(() => {
        location.reload() // 为了重新实例化vue-router对象 避免bug
      })
    },
    showdia(){
      this.dialogTableVisible = true
      this.newPassword = ''
      this.checkPassword = ''
    },
    resetPsw(){
        this.$refs['form'].validate((valid) => {
          if (valid) {
            if(this.form.newPassword == '' | this.form.checkPassword == '' ){
                this.$message({
                  message: '密码不能为空！',
                  type: 'warning'
                });
                return
            }
            if(this.form.newPassword != this.form.checkPassword){
                this.$message({
                  message: '两次密码输入不同！',
                  type: 'warning'
                });
                return
            }
            reset(this.form).then(res=>{

              this.dialogTableVisible = false;
                this.$message({
                  message: '修改成功',
                  type: 'success'
                });
            })


          }
        });

     
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.navbar {
  height: 50px;
  line-height: 50px;
  border-radius: 0px !important;
  .hamburger-container {
    line-height: 58px;
    height: 50px;
    float: left;
    padding: 0 10px;
  }
  .screenfull {
    position: absolute;
    right: 90px;
    top: 16px;
    color: red;
  }
  .avatar-container {
    height: 50px;
    display: inline-block;
    position: absolute;
    right: 35px;
    .avatar-wrapper {
      cursor: pointer;
      margin-top: 5px;
      position: relative;
      .uname{
        height: 40px;
      }
      .user-avatar {
        width: 40px;
        height: 40px;
        border-radius: 10px;
      }
      .el-icon-caret-bottom {
        position: absolute;
        right: -20px;
        top: 25px;
        font-size: 12px;
      }
    }
  }
}
</style>

