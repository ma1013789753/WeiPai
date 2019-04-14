<template>
  <div class="contain">
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="部门名称" prop="name">
          <el-input v-model="form.name" clearable maxlength="10" minlength="2" ></el-input>
        </el-form-item>
        <el-form-item label="部门简介">
          <el-input v-model="form.memo" clearable   type="textarea"
            :rows="2" placeholder="请输入内容">
          </el-input>
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
  import { getId,add } from '@/api/sysdept'
  export default {
    data() {
      return {
        form: {
          tid:null,
          name: '',
          memo: ''
        },
        rules: {
          name: [
            { required: true, message: '请输入角色名称', trigger: 'blur' },
            { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
          ],
        }
      }
    },
    created(){
        this.getParams()
    },
    methods: {
      submitForm(formName) {
        this.$refs[formName].validate((valid) => {
          if (valid) {
            add(this.form).then(res => {
                this.$message({
                  message: '保存成功',
                  type: 'success'
                });
                this.cancel();

            })
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      cancel(){
         this.$router.push({//你需要接受路由的参数再跳转
          path: "/pre/dept/index"
        });
      },
      getParams () {
        // 取到路由带过来的参数 
        this.tid = this.$route.query.tid
        console.log(this.tid)
        this.getUserById(this.tid)
      },
      getUserById(tid){
          getId(tid).then(res =>{
            this.form = res.data
          })
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
