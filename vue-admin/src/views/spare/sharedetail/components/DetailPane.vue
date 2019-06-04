<template>
  <div class="contain">
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="账号头像" prop="accountAvatar">
          <img style="width: 100px; height: 100px;border-radius:50%;"
               :src="form.accountAvatar">
        </el-form-item>

        <el-form-item label="用户名称" prop="userName">
          <el-input v-model="form.userName"  disabled></el-input>
        </el-form-item>

        <el-row>
          <el-col :span="12">
        <el-form-item label="账号ID" prop="accountId">
          <el-input v-model="form.accountId" disabled ></el-input>
        </el-form-item>
          </el-col>
          <el-col :span="12">
        <el-form-item label="账号名称" prop="accountName">
          <el-input v-model="form.accountName" disabled ></el-input>
        </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
        <el-form-item label="分享额度" prop="shareCoin">
          <el-input v-model="form.shareCoin"  disabled></el-input>
        </el-form-item>
        </el-col>
          <el-col :span="12">
        <el-form-item label="分享次数" prop="shareNum">
          <el-input v-model="form.shareNum"  disabled></el-input>
        </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="推广类型" prop="shareType" >
              <el-input v-if="form.shareType == 1" value="微博" disabled ></el-input>
              <el-input v-if="form.shareType == 2" value="公众号" disabled ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="奖励类型" prop="shareStatus">
              <el-input v-if="form.shareStatus == 1" value="现金" disabled ></el-input>
              <el-input v-if="form.shareStatus == 0" value="积分" disabled ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12">
            <el-form-item label="所属领域" prop="tagName">
              <el-input v-model="form.tagName" disabled ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="添加时间" prop="addTime">
              <el-input v-model="form.addTime" disabled >
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="图片" prop="shareImage">

          <div v-for="pic in form.shareImage" >
            <el-col :span="8">
            <img style="width: 100px; height: 100px;"
                 :src="pic">
            </el-col>
          </div>

        </el-form-item>
        <el-form-item label="背景图片" prop="backgroundImage">
          <img style="width: 100px; height: 100px;"
               :src="form.backgroundImage">
        </el-form-item>
        <el-form-item label="分享内容" prop="shareContent">
          <el-input v-model="form.shareContent" autosize type="textarea"></el-input>
        </el-form-item>

      </el-form>
  </div>
</template>

<script>
  import { getShare } from '@/api/spare'
  import moment from 'moment'
  export default {
    data() {
      return {
        form: {
          shareId: '',
          userName: '',
          accountId: '',
          accountName:'',
          accountAvatar:'',
          shareCoin: '',
          shareNum: 0,
          shareType: 0,
          shareStatus: '',
          tagName: '',
          addTime: 0,
          backgroundImage: '',
          shareImage:null,
          shareContent:''
        },
      }

    },
    created(){
      this.getShareById()
    },
    methods: {
      getShareById(){
        this.form.shareId = this.$route.query.id
        getShare({'shareId':this.form.shareId}).then(res =>{
          this.form = res.data
          this.form.addTime = moment(parseInt(this.form.addTime)*1000).format("YYYY-MM-DD HH:mm:ss");
          this.form.shareContent = Base64.decode(this.form.shareContent);
          this.form.shareImage = JSON.parse(this.form.shareImage).map(val => {
            return val.original_pic
          });

        })
      },
    }
  }
</script>
<style lang='scss' scoped>
  .contain{
    margin-top: 50px;
    margin-left: 10%;
    width: 50%;
  }
  
</style>
