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
            <el-input
              type="textarea"
              :rows="4"
              placeholder="请输入任务描述"
              max="100"
              v-model="form.content">
            </el-input>
        </el-form-item>
        <el-form-item label="任务链接资源" prop="link">
            <el-input v-model="form.link" placeholder="请输入任务链接资源" type="text" max="50"></el-input>
        </el-form-item >
        <el-form-item label="派发账户">
            <el-button type="primary" @click="dialogVisible = true">选择账户</el-button>
        </el-form-item>
        <el-form-item  prop="names">
            <!-- <el-input
              disabled
              type="textarea"
              :rows="4"
              placeholder="请选择派发账户"
               v-model="form.names"
              >
            </el-input>  -->
          <el-table
            highlight-current-row border   empty-text="暂无数据" stripe 
            ref="multipleTable"
            :data="users"
            tooltip-effect="dark"
            height="350px"
            @selection-change="handleSelectionChange">
            <el-table-column
              type="selection"
              width="100px">
            </el-table-column>
            <el-table-column
              prop="accountName"
              label="姓名"
              width="200px">
            </el-table-column>
            <el-table-column
              prop="followNum"
              label="粉丝数"
               width="200px"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        </el-form-item>
        
        <el-form-item>
          <el-button class="fleft" @click="cancel()">取消</el-button>
          <el-button class="fright" type="primary" @click="submitForm('form')">确定</el-button>
        </el-form-item>
      </el-form>

      <el-dialog
        title="微博账户选择"
        width="500px"
        :visible.sync="dialogVisible"
        >
          <el-table
            highlight-current-row border   empty-text="暂无数据" stripe 
            ref="multipleTable"
            :data="users"
            tooltip-effect="dark"
            height="350px"
            @selection-change="handleSelectionChange">
            <el-table-column
              type="selection"
              width="100px">
            </el-table-column>
            <el-table-column
              prop="accountName"
              label="姓名"
              width="200px">
            </el-table-column>
            <el-table-column
              prop="followNum"
              label="粉丝数"
               width="200px"
              show-overflow-tooltip>
            </el-table-column>
          </el-table>
        <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
        </span>
      </el-dialog>
  </div>
</template>

<script>
  import { add ,getAccount} from '@/api/task'
  export default {
    data() {
      return {
        dialogVisible:false,
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
        
        amount:100,
        rules: {
          name: [
            { required: true, message: '请输入任务名称', trigger: 'blur' },
            { min: 2, max: 20, message: '长度在 2 到 20 个字符', trigger: 'blur' }
          ],
          award: [
            { required: true, message: '请输入任务奖励', trigger: 'blur' },
          ],
          content: [
            { required: true, message: '请输入任务描述', trigger: 'blur' },
            { min: 2, max: 200, message: '长度在 2 到 200 个字符', trigger: 'blur' }

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
      getUser(){
          getAccount().then(res => {
            console.log(JSON.stringify(res));
            this.users = res.data
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
        // this.form.ids = val;
        this.form.names = '';
        this.form.ids = '';
        for (let index = 0; index < val.length; index++) {
          this.form.names = this.form.names+val[index].accountName+","
          this.form.ids = this.form.ids+val[index].accountId+","
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
