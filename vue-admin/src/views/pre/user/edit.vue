<template>
  <div class="contain">
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="用户账号" prop="username">
          <el-input v-model="form.username" clearable maxlength="10" minlength="2" ></el-input>
        </el-form-item>
        <el-form-item label="用户密码" prop="password" >
          <el-input type="password" v-model="form.password" clearable maxlength="10" minlength="2" disabled></el-input>
        </el-form-item>
        <el-form-item label="用户姓名" prop="nickname">
          <el-input v-model="form.nickname" clearable maxlength="10" minlength="2" ></el-input>
        </el-form-item>
        <el-form-item label="部门" prop="deptId">
          <el-select v-model="form.deptId" placeholder="请选择">
            <el-option
              v-for="item in depts"
              :key="item.tid"
              :label="item.name"
              :value="item.tid">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="角色" prop="roleId">
          <el-select v-model="form.roleId" placeholder="请选择">
            <el-option
              v-for="item in roles"
              :key="item.rid"
              :label="item.name"
              :value="item.rid">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="手机号" prop="phone">
          <el-input type="phone" v-model="form.phone" clearable  maxlength="11" minlength="11"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="mail">
          <el-input v-model="form.mail" clearable maxlength="30" minlength="2" ></el-input>
        </el-form-item>
        <el-form-item>

        </el-form-item>
        <el-form-item>
          <el-button class="fleft" @click="cancel()">取消</el-button>
          <el-button class="fright" type="primary" @click="submitForm('form')">保存</el-button>
        </el-form-item>
      </el-form>
  </div>
</template>

<script>
  import { add,getId } from '@/api/sysuser'
  import { all} from '@/api/sysdept'
  import { allRole} from '@/api/sysrole'
  import { validatePhone,validateMail } from '@/utils/validate'
  export default {
    data() {
      var vphone = (rule, value, callback) => {
        if ( value & !validatePhone(value)) {
          callback(new Error('请输入11位有效手机号码'));
        } else {
          callback();
        }
      };
      var vmail = (rule, value, callback) => {
        if (value & !validateMail(value)) {
          callback(new Error('请输入正确的邮箱地址'));
        } else {
          callback();
        }
      };

      return {
        depts:[],
        roles:[],
        form: {
          uid:null,
          username: '',
          password: '',
          nickname: null,
          roleId:null,
          deptId:null,
          phone:null,
          mail:null,
        },
        rules: {
          username: [
            { required: true, message: '请输入账号', trigger: 'blur' },
            { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入账号密码', trigger: 'blur' },
          ],
          deptId: [
            { required: true, message: '请选择用户部门', trigger: 'blur' },
          ],
          roleId: [
            { required: true, message: '请选择用户角色', trigger: 'blur' },
          ],
          phone: [
            { required: false, validator: vphone, trigger: 'blur' }
          ],
          mail: [
            { required: false, validator: vmail, trigger: 'blur' }
          ],
        }
      }
    },
    created(){
        this.getAllDept()
        this.getAllRole()
        this.getParams () 
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            add(this.form).then(res => {
                this.$message({
                  message: '添加成功',
                  type: 'success'
                });
                this.cancel();

            })
          }else{
            console.log("sdafffffffffffffffff")
          }

        });
      },
      getParams () {
        // 取到路由带过来的参数 
        this.form.uid = this.$route.query.uid
        this.getUserById()
      },
      getUserById(){
        getId(this.form.uid).then(res =>{
          this.form = res.data
        })
      },

      getAllDept(){
        all().then(res => {
            this.depts = res.data;
        })
      },
      getAllRole(){
        allRole().then(res => {
            this.roles = res.data;
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      cancel(){
         this.$router.push({//你需要接受路由的参数再跳转
          path: "/pre/user/index"
        });
      }
    }
  }
</script>
<style lang='scss' scoped>
  .contain{
    margin-top: 50px;
    margin-left: 10%;
    width: 50%;
    .fright{
      float: right;
      width: 6rem;
    }
    .fleft{
      width: 6rem;
    }
  }
  
</style>
