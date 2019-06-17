<template>
  <div class="contain">
      <el-form ref="form" :rules="rules" :model="form" label-width="180px">
        <el-form-item label="任务名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入任务名称"  type="text" max="20"></el-input>
        </el-form-item>
        <el-form-item label="任务奖励" prop="award">
          <el-input v-model="form.award" placeholder="请输入任务奖励" type="number"max="100"></el-input>
        </el-form-item>
        <el-form-item label="任务描述" prop="content">
          <Tinymce ref="editor" v-model="form.content" :height="400" />
        </el-form-item>
        <el-form-item label="任务链接资源" prop="link">
            <el-input v-model="form.link" placeholder="请输入任务链接资源" type="text" max="50"></el-input>
        </el-form-item >
        <el-form-item label="截止时间" prop="link">
              <el-date-picker
                value-format="yyyy-MM-dd-HH:mm:ss"
                v-model="form.endingTime"
                type="datetime"
                placeholder="选择日期时间">
              </el-date-picker>
            <!-- <el-input v-model="form.endTime" placeholder="请选择截止时间" suffix-icon="el-icon-date" type="text" max="50"></el-input> -->
        </el-form-item >
        <el-form-item label="派发账户">
            <el-button type="primary" @click="dialogVisible = true">选择账户</el-button>
        </el-form-item>
        <el-form-item  prop="names">
            <el-input
              disabled
              type="textarea"
              :rows="4"
              placeholder="请选择派发账户"
               v-model="form.names"
              >
            </el-input> 
        </el-form-item>
        
        <el-form-item>
          <el-button class="fleft" @click="cancel()">取消</el-button>
          <el-button class="fright" type="primary" @click="submitForm('form')">确定</el-button>
        </el-form-item>
      </el-form>

      <el-dialog
        title="微博账户选择"
        width="700px"
        :visible.sync="dialogVisible"
        >
        <el-header>
          <el-row :gutter="10">
            <el-col :span="5">
            </el-col>
            <el-col :span="6">
              <el-input  placeholder="请输入账号名称" maxlength="5" minlength="1" v-model="param.name" clearable ></el-input>
            </el-col>
            <el-col :span="6">
              <el-select  placeholder="请选择认证状态" v-model="param.type">
                <el-option label="认证状态" value=""></el-option>
                <el-option label="已认证" value="1"></el-option>
                <el-option label="未认证" value="0"></el-option>
              </el-select>
            </el-col>
            <el-col :span="5">
              <el-button icon="el-icon-search" type="primary" @click="search1()">搜索</el-button>
            </el-col>
          </el-row>
        </el-header>
          <el-table
          style="height: 350px;

width: 450px;

float: left;"
            highlight-current-row border   empty-text="暂无数据" stripe 
            ref="multipleTable"
            :data="users"
            tooltip-effect="dark"
            height="350px"
            @selection-change="handleSelectionChange">
            <el-table-column
              type="selection">
            </el-table-column>
            <el-table-column
              prop="accountName"
              label="姓名">
            </el-table-column>
            <el-table-column
              prop="followNum"
              label="粉丝数"
              show-overflow-tooltip>
            </el-table-column>
            <el-table-column
              prop="vlegalize"
              label="认证"
              show-overflow-tooltip>
                    <template slot-scope="scope">
                    <el-tag v-if="scope.row.vlegalize == 1"> V</el-tag>
                    <span v-else type="danger"> 未认证</span>
                  </template>

            </el-table-column>
          </el-table>
           <el-table
           style="width: 200px;"
            highlight-current-row border   empty-text="暂无数据" stripe 
            ref="multipleTable"
            :data="users_select"
            tooltip-effect="dark"
            height="350px">
            <el-table-column
              prop="accountName"
              label="已选择">
            </el-table-column>
          </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button @click="cancelSelect">取 消</el-button>
          <el-button type="primary" @click="cancelSelect">确 定</el-button>
        </span>
      </el-dialog>
  </div>
</template>

<script>
  import Tinymce from '@/components/Tinymce'
  import { add ,getAccount} from '@/api/task'
  export default {
    components: { Tinymce },
    data() {
      return {
        dialogVisible:false,
        param:{
          name:'',
          type:'',
        },
        search:'',
        form: {
          id:null,
          name: '',
          award:0,
          content:null,
          link:null,
          ids: "",
          names:"",

        },
        users:[],
        users_copy:[],
        users_select:[],
        users_select_copy:[],
        amount:100,
        rules: {
          name: [
            { required: true, message: '请输入任务名称', trigger: 'blur' },
            { min: 2, max: 40, message: '长度在 2 到40 个字符', trigger: 'blur' }
          ],
          award: [
            { required: true, message: '请输入任务奖励', trigger: 'blur' },
          ],
          content: [
            { required: true, message: '请输入任务描述', trigger: 'blur' },

          ],
          names: [
            { required: true, message: '请选择派发账户', trigger: 'blur' },
            { min: 2, max: 200, message: '长度在 2 到 200 个字符', trigger: 'blur' }

          ],
        }
      }
    },
    created(){
        // this.getParams () 
        this.getUser()
    },
    methods: {
      submitForm(formName) {
        // alert("sdfasdf")
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
      cancelSelect(){
        this.dialogVisible = false
        this.users_select = []
        this.users_select_copy = []
      },
      search1(){
        this.users_select_copy = this.users_select
        this.form.ids = '';
        this.users = [];
        if(this.param.name && this.param.type){
          var data = [];
          for (let index = 0; index < this.users_copy.length; index++) {
            var item = this.users_copy[index]
            if(item.vlegalize == this.param.type && item.accountName.indexOf(this.param.name)>=0){
              data.push(item)
            }
          }
          this.users = data
        }
        if(!this.param.name && this.param.type){
          var data = [];
          for (let index = 0; index < this.users_copy.length; index++) {
            var item = this.users_copy[index]
            if(item.vlegalize == this.param.type){
              data.push(item)
            }
          }
          this.users = data
        }
        if(this.param.name && !this.param.type){
          var data = [];
          for (let index = 0; index < this.users_copy.length; index++) {
            var item = this.users_copy[index]
            if(item.accountName.indexOf(this.param.name)>=0){
              data.push(item)
            }
          }
          this.users = data
        }
        if(!this.param.name && !this.param.type){
          this.users = this.users_copy
        }
      },
      getUser(){
          getAccount().then(res => {
            console.log(JSON.stringify(res));
            this.users = res.data
            this.users_copy = this.users;
        })
      },
      getParams () {
        // 取到路由带过来的参数 
        this.form.uid = this.$route.query.uid
        this.getUserById()
      },
      getUserById(){
        getById(this.form.uid).then(res =>{
          this.form = res.data
        })
      },
      toggleSelection(rows) {
        if (rows) {
          rows.forEach(row => {
            this.$refs.multipleTable.toggleRowSelection(row);
          });
        } else {
          this.$refs.multipleTable.clearSelection();
        }
      },
      handleSelectionChange(val) {
        if(!val || val.length <=0){
          return false
        }
        // this.form.ids = val;
        var data = []
        this.form.names = '';
        this.form.ids = '';
        for (let index = 0; index < val.length; index++) {
          data.push(val[index])
        }
        data = data.concat(this.users_select_copy)

        this.users_select = (data)
        for (let index = 0; index < this.users_select.length; index++) {
          this.form.names = this.form.names+this.users_select[index].accountName+","
          this.form.ids = this.form.ids+this.users_select[index].accountId+","
        }
      },
      cancel(){
         this.$router.push({//你需要接受路由的参数再跳转
          path: "/maintain/manager/list"
        });
      }
    }
  }
</script>
<style lang='scss' scoped>
  .contain{
    margin-top: 50px;
    margin-left: 5%;
    width: 80%;
    .fright{
      float: right;
      width: 6rem;
    }
    .fleft{
      width: 6rem;
    }
    .el-form-item{
    width: 100%;
    }
  }

  
</style>
