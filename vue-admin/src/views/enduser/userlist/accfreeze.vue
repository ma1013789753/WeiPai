<template>
  <div class="contain">
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="封禁账号" prop="userName">
          <el-col :span="12">
          <el-input v-model="form.accountName" disabled ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="封禁天数" prop="amount">
          <el-input-number v-model="form.amount" :step="5" min="1" max="10000"></el-input-number>
        </el-form-item>
        <el-form-item label="封禁原因" prop="memo">
          <el-col :span="12">
        <el-input
          type="textarea"
          :rows="2"
          placeholder="封禁说明"
          v-model="form.desc">
        </el-input>
          </el-col>
        </el-form-item>
        <el-form-item>
          <el-col :span="12">
          <el-button class="fleft" @click="cancel()">取消</el-button>
          <el-button class="fright" type="primary" @click="submitForm()">确定</el-button>
          </el-col>
        </el-form-item>
      </el-form>
  </div>
</template>

<script>
  import { ban } from '@/api/customer'
  export default {
    data() {
      return {
        form: {
          userId:null,
          accountName: '',
          desc:'',
          amount:5
        }
      }
    },
    created(){
        this.getParams () 
    },
    methods: {
      submitForm() {
        ban(this.form).then(res => {
          this.$message({
                  message: '操作成功',
                  type: 'success'
                });
          this.cancel();
      })},
      getParams () {
        // 取到路由带过来的参数 
        this.form.userId = this.$route.query.accid
        this.form.accountName = this.$route.query.accname

      },
      cancel(){
        this.$router.go(-1);
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
