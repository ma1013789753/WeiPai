<template>
  <div class="contain">
      <el-form ref="form" :rules="rules" :model="form" label-width="80px">
        <el-form-item label="备品名称" prop="name">
          <el-input v-model="form.name" clearable maxlength="10" minlength="2" ></el-input>
        </el-form-item>
        <el-form-item label="备品编码" prop="code">
          <el-input v-model="form.code" clearable maxlength="20" minlength="2" ></el-input>
        </el-form-item>
        <el-form-item label="生产厂家" prop="manufacturer">
          <el-input v-model="form.manufacturer" clearable maxlength="20" minlength="2" ></el-input>
        </el-form-item>
        <el-form-item label="供应商" prop="supplier">
          <el-input v-model="form.supplier" clearable maxlength="20" minlength="2" ></el-input>
        </el-form-item>
        <el-form-item label="购入周期" prop="round">
          <el-input v-model="form.round" type="number" clearable maxlength="10" minlength="1" ></el-input>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="在库数量" prop="number" >
              <el-input v-model="form.number" type="number" clearable maxlength="10" minlength="1" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="警报线" prop="warnLine">
              <el-input v-model="form.warnLine" clearable maxlength="10" minlength="1" type="number" ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="警报邮箱" prop="email">
          <el-input v-model="form.email" clearable maxlength="20" minlength="2" ></el-input>
        </el-form-item>
        <el-form-item label="警报人" prop="user">
          <el-input v-model="form.user" clearable maxlength="10" minlength="2" ></el-input>
        </el-form-item>
        <el-form-item label="摆放位置" prop="address">
          <el-input v-model="form.address" clearable maxlength="20" minlength="2" ></el-input>
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
  import { add,getId} from '@/api/spare'
  import { validatePhone,validateMail } from '@/utils/validate'
  export default {
    data() {
      var vemail = (rule, value, callback) => {
        if (!validateMail(value)) {
          callback(new Error('请输入正确的邮箱地址'));
        } else {
          callback();
        }
      };
      return {
        form: {
          id:'',
          name: '',
          code: '',
          manufacturer: '',
          supplier: '',
          number: 0,
          warnLine: 0,
          email: '',
          user: '',
          round: 0,
          address: '',

        },
        rules: {
          name: [
            { required: true, message: '请输入备品名称', trigger: 'blur' },
            { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
          ],
          code: [
            { required: true, message: '请输入备品编号', trigger: 'blur' },
            { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
          ],
          manufacturer: [
            { required: true, message: '请输入生产厂家', trigger: 'blur' },
            { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
          ],
          supplier: [
            { required: true, message: '请输入供应商', trigger: 'blur' },
            { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
          ],
          warnLine: [
            { required: true, message: '请输入警报线', trigger: 'blur' },
          ],
          email: [
            { required: true, validator: vemail, trigger: 'blur' }
          ],
          round:[
            { required: true, message: '请输入购入周期', trigger: 'blur' },
          ]
        }
      }
    },
    created(){
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
          }
        });
      },
      getParams () {
        // 取到路由带过来的参数 
        this.form.id = this.$route.query.id
        this.getInfoById()
      },
      getInfoById(){
        getId(this.form.id).then(res =>{
          this.form = res.data
        })
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
      },
      cancel(){
         this.$router.push({//你需要接受路由的参数再跳转
          path: "/spare/sparelist/list"
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
