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
          <el-button class="fright" type="primary" @click="submitForm('form')">创建</el-button>
        </el-form-item>
      </el-form>
  </div>
</template>

<script>
  import { add } from '@/api/sysdept'
  export default {
    data() {
      return {
        form: {
          name: '',
          memo: ''
        },
        rules: {
          name: [
            { required: true, message: '请输入部门名称', trigger: 'blur' },
            { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
          ],
        }
      }
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
