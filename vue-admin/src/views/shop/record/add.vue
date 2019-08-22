<template>
  <div class="contain">
    <el-form ref="form" :rules="rules" :model="form" label-width="80px">
      <el-form-item label="商品名称" prop="title">
        <el-input v-model="form.title" clearable maxlength="10" minlength="1" ></el-input>
      </el-form-item>
      <el-form-item label="积分" prop="cost">
        <el-input v-model="form.cost" clearable maxlength="10" minlength="1" ></el-input>
      </el-form-item>
      <el-form-item label="商品图片" prop="picUrl" >
        <el-upload
          class="avatar-uploader"
          name="pic"
          action="http://youdianshare.com:8090/User/files_upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload">
          <img v-if="form.picUrl" :src="form.picUrl" class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>
      <el-form-item label="商品简介" prop="memo">
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
  import { add } from '@/api/shop'
  export default {
    data() {
      return {
        form: {
          title: '',
          memo: '',
          picUrl:'',
          cost:''
        },
        rules: {
          title: [
            { required: true, message: '请输入商品名称', trigger: 'blur' },
            { min: 1, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
          ],
          cost: [
            { required: true, message: '请输入商品价格', trigger: 'blur' },
            { min: 1, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
          ],
          memo: [
            { required: true, message: '请输入商品描述', trigger: 'blur' },
            { min: 1, max: 10, message: '长度在 2 到 200 个字符', trigger: 'blur' }
          ],
          picUrl: [
            { required: true, message: '请选择图片', trigger: 'blur' },
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
          path: "/shoplist/list"
        });
      },
      handleAvatarSuccess(res, file) {
        this.form.picUrl = res.datas
      },
      beforeAvatarUpload(file) {
        const isJPG = file.type === 'image/jpeg';
        const isLt2M = file.size / 1024 / 1024 < 2;

        if (!isJPG) {
          this.$message.error('上传头像图片只能是 JPG 格式!');
        }
        if (!isLt2M) {
          this.$message.error('上传头像图片大小不能超过 2MB!');
        }
        return isJPG && isLt2M;
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
    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width: 178px;
      height: 178px;
      line-height: 178px;
      text-align: center;
    }
    .avatar {
      width: 178px;
      height: 178px;
      display: block;
    }

  }

  .el-upload{
    border: 1px solid #ccc!important;
  }

</style>
