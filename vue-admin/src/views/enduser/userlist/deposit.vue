<template>
  <div class="contain">
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="充值账号" prop="userName">
          <el-col :span="12">
          <el-input v-model="form.userName" disabled ></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="充值方式" prop="admin">
          <el-col :span="12">
          <el-input value="后台充值" disabled></el-input>
          </el-col>
        </el-form-item>
        <el-form-item label="充值类型" prop="chargetype">
          <el-select v-model="form.chargetype" placeholder="请选择" >
            <el-option
              v-for="item in options"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="充值额度" prop="amount">
          <el-input-number v-model="form.amount" :step="100" min="-5000" max="10000"></el-input-number>
        </el-form-item>
        <el-form-item >
          <el-col :span="12">
          <el-alert
            description="可充值范围 -5000 到 10000"
            type="warning"
            :closable="false">
          </el-alert>
          </el-col>
        </el-form-item>
        <el-form-item label="备注" prop="memo">
          <el-col :span="12">
        <el-input
          type="textarea"
          :rows="2"
          placeholder="充值说明"
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
  import { Recharge,getById } from '@/api/customer'
  export default {
    data() {
      return {
        options: [{
          value: 0,
          label: '现金'
        }, {
          value: 1,
          label: '积分'
        }],
        form: {
          userId:null,
          userName: '',
          userCoin:null,
          availablePredeposit:null,
          desc:'',
          chargetype:0,
          amount:100
        }
      }
    },
    created(){
        this.getParams () 
    },
    methods: {
      submitForm() {
        Recharge(this.form).then(res => {
          this.$message({
                  message: '充值成功',
                  type: 'success'
                });
          this.cancel();
      })},
      getParams () {
        // 取到路由带过来的参数 
        this.form.userId = this.$route.query.uid
        this.getUserById()
      },
      getUserById(){
        getById(this.form.userId).then(res =>{
          this.form.userId = res.data.userId
          this.form.userName = res.data.userName
          this.form.userCoin = res.data.userCoin
          this.form.availablePredeposit = res.data.availablePredeposit
        })
      },
      cancel(){
         this.$router.push({//你需要接受路由的参数再跳转
          path: "/enduser/userlist/list"
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
