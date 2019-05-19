<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
                <el-col :span="15" style="text-align:right">
                    <router-link class="inlineBlock" to="/joblist/add">
                      <el-button type="primary">新增Job</el-button>
                    </router-link>
                </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata"  empty-text="暂无数据" stripe height="380px" @current-change="handleCurrentChange">
        <el-table-column type="index" prop="date" label="ID" sortable ></el-table-column>
        <el-table-column prop="jobName" label="job名称" sortable ></el-table-column>
        <el-table-column prop="jobGroup" label="group" sortable ></el-table-column>
        <el-table-column prop="jobType" label="type" sortable ></el-table-column>
        <el-table-column prop="jobStatus" label="state" sortable ></el-table-column>
        <el-table-column prop="cronExpression" label="crons" sortable ></el-table-column>
        <el-table-column  prop="desc" label="desc" sortable ></el-table-column>
        <el-table-column label="操作" width="380px">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleRun(scope.row)"
            >运行</el-button>
            <el-button
              size="mini"
              type="warning"
              @click="handlePause(scope.row)"
            >暂停</el-button>
            <el-button
              size="mini"
              type="success"
              @click="handleResume(scope.row)"
            >恢复</el-button>
            <el-button
              size="mini"
              type="success"
              @click="handleEdit(scope.row)"
            >编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDel(scope.row)"
            >删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer height="40">
    </el-footer>
  </el-container>
</template>

<script>
  import { list,del,run,pause,resume} from '@/api/job'
  export default {
    data() {
      return {
        currentRow:null,
        data: [],
        values:[],
        page: {
          pages: 1,
          total: 0
        },
        item: {
          id:null
        },
        tabledata: []
      }
    },
    created() {
      list().then(res => {
        this.tabledata = res.data
        console.log(res.data)
      })
    },
    mounted() {},
    methods: {
      handleEdit(res){
        this.sendParams(res.jobName);
      },
      handleRun(res){
        this.$confirm('立即运行该job, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.runJob(res)
          clearTimeout(this.timer);  //清除延迟执行

          this.timer = setTimeout(()=>{   //设置延迟执行
            list().then(res => {
              this.tabledata = res.data
            })
          },1000);
        }).catch(() => {

        });
      },
      handlePause(res){
        this.$confirm('暂停该job, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.pauseJob(res)
          clearTimeout(this.timer);  //清除延迟执行

          this.timer = setTimeout(()=>{   //设置延迟执行
            list().then(res => {
              this.tabledata = res.data
            })
          },1000);
        }).catch(() => {

        });
      },
      handleResume(res){
        this.$confirm('恢复该job, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.resumeJob(res)
          clearTimeout(this.timer);  //清除延迟执行

          this.timer = setTimeout(()=>{   //设置延迟执行
            list().then(res => {
              this.tabledata = res.data
            })
          },1000);

        }).catch(() => {

        });
      },
      handleDel(res){
        this.$confirm('确定删除该job, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.delJob(res)
          clearTimeout(this.timer);

          this.timer = setTimeout(()=>{
            list().then(res => {
              this.tabledata = res.data
            })
          },1000);
        }).catch(() => {

        });
      },
      handleCurrentChange(val){
        this.values = [];
        this.currentRow = val
        val.permissions.forEach((perm, index) => {
          if(perm.father != 0){
            this.values.push(perm.pid)
          }
        })
      },
      //运行job
      runJob(val){
        run(val).then(res => {
          this.$message({
            type: 'success',
            message: '执行成功'
          });

        })
      },
      //暂停job
      pauseJob(val){
        pause(val).then(res => {
          this.$message({
            type: 'success',
            message: '执行成功'
          });
        })
      },
      //恢复job
      resumeJob(val){
        resume(val).then(res => {
          this.$message({
            type: 'success',
            message: '执行成功'
          });
        })
      },
      //删除job
      delJob(val){
        del(val).then(res => {
          this.$message({
            type: 'success',
            message: '执行成功'
          });
        })
      },
      sendParams (res) {
        this.$router.push({
          name: 'editjob',
          query: {
            jobs: res
          }
        })
      }

    }
  }

</script>
<style lang='scss' scoped>

</style>
