<template>
  <div class="contain">
    <el-form ref="form" :rules="rules" :model="form" label-width="120px">
      <el-form-item label="job name" prop="jobName">
        <el-input v-model="form.jobName" clearable maxlength="20" minlength="2" ></el-input>
      </el-form-item>
      <el-form-item label="job group" prop="jobGroup">
        <el-input v-model="form.jobGroup" clearable maxlength="15" minlength="2" ></el-input>
      </el-form-item>
      <el-form-item label="job status" prop="jobStatus">
        <el-input v-model="form.jobStatus" clearable maxlength="10" minlength="2" :disabled="true"></el-input>
      </el-form-item>
      <el-form-item label="job desc" prop="desc">
        <el-input v-model="form.desc" clearable maxlength="10" minlength="2" ></el-input>
      </el-form-item>
      <el-form-item label="job type" prop="jobType">
        <el-input v-model="form.jobType" clearable  maxlength="11" minlength="11"></el-input>
      </el-form-item>
      <el-form-item label="crons" prop="cronExpression">
        <div class="cron">
          <el-popover v-model="cronPopover">
            <cron @change="changeCron" @close="cronPopover=false" i18n="en"></cron>
            <el-input slot="reference" @click="cronPopover=true" v-model="form.cronExpression" placeholder="请输入定时策略"></el-input>
          </el-popover>
        </div>
      </el-form-item>
      <el-form-item>
        <el-button class="fleft" @click="cancel()">取消</el-button>
        <el-button class="fright" type="primary" @click="submitForm('form')">创建</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
  import { add } from '@/api/job'
  import {cron} from 'vue-cron'
  export default {
    components: { cron },
    data() {
      return {
        cronPopover:false,
        form: {
          jobName: '',
          jobGroup: '',
          jobStatus: 'NORMAL',
          desc:null,
          jobType:null,
          cronExpression:null
        },
        rules: {
          jobName: [
            { required: true, message: '请输入job名称', trigger: 'blur' },
          ],
          jobGroup: [
            { required: true, message: '请输入job组', trigger: 'blur' },
          ],
          jobType: [
            { required: true, message: '请输入job类型', trigger: 'blur' },
          ],
        }
      }
    },
    methods: {
      changeCron(val){
        this.form.cronExpression=val
      },
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
            console.log("数据验证失败")
          }

        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      cancel(){
        this.$router.push({//你需要接受路由的参数再跳转
          path: "/joblist/list"
        });
      },
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
