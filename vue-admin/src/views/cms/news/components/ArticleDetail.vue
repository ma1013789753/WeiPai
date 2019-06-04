<template>
  <div class="createPost-container">
    <el-form ref="postForm" :model="postForm" :rules="rules" class="form-container">
      <sticky :z-index="10" >
        <el-button-group>
          <el-button v-loading="loading" type="primary" @click="submitForm">发布</el-button>
          <el-button v-loading="loading" type="warning" @click="cancel">取消</el-button>
        </el-button-group>
      </sticky>

      <div class="createPost-main-container">
        <el-row>
          <el-col :span="24">
            <el-form-item style="margin-bottom: 40px;" prop="cmsTitle">
              <MDinput v-model="postForm.cmsTitle" :maxlength="100" name="name" required>资讯标题:</MDinput>
            </el-form-item>

            <div class="postInfo-container">
              <el-row>
                <el-col :span="8">
                  <el-form-item label-width="45px" label="作者:" class="postInfo-container-item" prop="cmsAuthor">
                    <el-input v-model="postForm.cmsAuthor" disabled ></el-input>
                  </el-form-item>
                </el-col>

                <el-col :span="10">
                  <el-form-item label-width="60px" label="分类:" class="postInfo-container-item">
                    <el-select v-model="postForm.cateId"  placeholder="选择分类" >
                      <el-option v-for="cate in cateListOptions" :key="cate.cateId" :label="cate.cateName" :value="cate.cateId" />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label-width="45px" label="排序:" class="postInfo-container-item" prop="cmsSort">
                    <el-input v-model="postForm.cmsSort" ></el-input>
                  </el-form-item>
                </el-col>
              </el-row>

            </div>
          </el-col>
        </el-row>

        <el-form-item prop="cmsImage" style="margin-bottom: 30px" >
          <span>咨询封面:</span>
          <Upload v-model="postForm.cmsImage" />
        </el-form-item>

        <el-form-item prop="cmsContent" style="margin-bottom: 30px;">
          <span>咨询内容:</span>
          <Tinymce ref="editor" v-model="postForm.cmsContent" :height="400" />
        </el-form-item>

      </div>
    </el-form>
  </div>
</template>

<script>
import Tinymce from '@/components/Tinymce'
import Upload from '@/components/Upload/SingleImage3'
import MDinput from '@/components/MDinput'
import Sticky from '@/components/Sticky' // 粘性header组件

import { getById,getCategory,updateNews } from '@/api/cms'
// import { searchUser } from '@/api/remote-search'

const defaultForm = {
  cmsId: '',//资讯ID
  cmsTitle: '', // 资讯标题
  cmsAuthor: 'admin', // 作者
  cateName:'',//所属分类
  cmsImage:'',//封面图
  cmsContent:'',//内容
  cmsSort:'0',//排序
}

export default {
  name: 'ArticleDetail',
  components: { Tinymce, MDinput, Upload, Sticky },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    }
  },
  data() {
    const validateRequire = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('不能为空'))
      } else {
        callback()
      }
    }
    return {
      postForm: Object.assign({}, defaultForm),
      loading: false,
      cateListOptions: null,
      rules: {
        cmsImage: [{ validator: validateRequire }],
        cmsTitle: [{ validator: validateRequire }],
        cmsContent: [{ validator: validateRequire }],
      },
      tempRoute: {}
    }
  },
  created() {
    if (this.isEdit) {
      this.fetchData()
    } else {
      this.postForm = Object.assign({}, defaultForm)
    }
      this.handelCate()
  },
  methods: {
    fetchData() {
      this.postForm.cmsId = this.$route.query.cmsid
      getById(this.postForm).then(response => {
        this.postForm = response.data
      }).catch(err => {
        console.log(err)
      })
    },
    submitForm() {
      this.$refs.postForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.cateListOptions.map(v => {
            if(v.cateId == this.postForm.cateId){
              this.postForm.cateName = v.cateName
            }
          })
          updateNews(this.postForm).then(res => {
            this.$message({
              message: '编辑成功',
              type: 'success'
            });
            this.loading = false
            this.cancel()
          })
        } else {
          console.log('error submit!!')
          return false
        }
      })
    },
    //拉取资讯分类
    handelCate(){
      getCategory(0).then(response => {
            if (!response.data) return
            this.cateListOptions = response.data
          })
    },
    cancel(){
      this.$router.push({
        path: "/cms/news"
      });
    }
  }
}
</script>

<style lang="scss" scoped>
@import "~@/styles/mixin.scss";

.createPost-container {
  position: relative;

  .createPost-main-container {
    padding: 40px 45px 20px 50px;

    .postInfo-container {
      position: relative;
      @include clearfix;
      margin-bottom: 10px;

      .postInfo-container-item {
        float: left;
      }
    }
  }

}

.article-textarea /deep/ {
  textarea {
    padding-right: 40px;
    resize: none;
    border: none;
    border-radius: 0px;
    border-bottom: 1px solid #bfcbd9;
  }
}

</style>
