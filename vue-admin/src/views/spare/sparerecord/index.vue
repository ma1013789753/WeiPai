<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入任务名称" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="4">
          <el-select v-model="page.search2" placeholder="请选择任务状态">
            <el-option label="任务状态" value=""></el-option>
            <el-option label="待审核" value="0"></el-option>
            <el-option label="已通过" value="1"></el-option>
            <el-option label="审核失败" value="2"></el-option>
          </el-select>
        </el-col>
        <el-col :span="5">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
        <el-col :span="11" style="text-align:right">

        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata"  empty-text="暂无数据" stripe height="400px" @current-change="handleCurrentChange">
        <el-table-column   type="index" label="编号" sortable width="60px">></el-table-column>
        <el-table-column  prop="lgMemberName" label="用户名" sortable width="100px" show-overflow-tooltip></el-table-column>
        <el-table-column prop="share.userName" label="发布人" width="100px" show-overflow-tooltip></el-table-column>
        <el-table-column prop="share.shareId" label="ShareId" width="100px" show-overflow-tooltip ></el-table-column>
        <el-table-column prop="lgAvAmount" label="奖励金额" width="100px"  show-overflow-tooltip></el-table-column>
        <el-table-column prop="share.coinMin" label="最低奖励"  show-overflow-tooltip></el-table-column>
        <el-table-column prop="share.coinMax" label="最高奖励"  show-overflow-tooltip></el-table-column>
        <el-table-column prop="shareLog.content" label="链接反馈" width="200px" show-overflow-tooltip ></el-table-column>
        <!-- <el-table-column  prop="idDel" label="是否删除" show-overflow-tooltip></el-table-column> -->
        <el-table-column  prop="shareLog.isPass" label="任务状态" :formatter="getIsPass">
        </el-table-column>
        <el-table-column prop="shareLog.addTime" label="分享时间" width="150px" :formatter="getTime" > </el-table-column>
        <el-table-column label="操作" width="150" fixed="right" align="center"> 
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.row)"
              >通过</el-button>
            <el-button
              size="mini"
              type="danger"
              @click="handleDel(scope.row)"
              >失败</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer height="40">
        <pagination ref="pagination" :total="page.total" @pageChange="getList"></pagination>
    </el-footer>
  </el-container>
</template>

<script>
import {isOk,ApproveState,getRealTime} from '@/utils/common'
import { list,approve,approveFail} from '@/api/pspare'
import pagination from '@/components/pagination'
export default {
  components: {
    pagination,
  },
  data() {
      return {
        currentRow:null,
        data: [],
        values:[],
        page: {
          pages: 1,
          search1:"",
          search2:"",
          search3:"",
          search4:"",

        },
        item: {
          shareId:null
        },
        tabledata: []
      }
    },
  created() {
  },
  mounted() {},
  methods: {
    handleEdit(res){
      if(res.shareLog.isPass!=0){
        this.$message({
          type: 'warning',
          message: '不可重复操作'
        });

        return false;
      }
      approve(res).then(res => {
        this.$message({
          type: 'success',
          message: '审核成功'
        });
        this.onSearch();  
      }) 
    },
    getIsOk(row, column, cellValue){
        return isOk(row, column, cellValue)
    },
    getIsPass(row, column, cellValue){
        return ApproveState(row, column, cellValue)
    },
    getTime(row, column, cellValue){
      return getRealTime(row, column, cellValue)
    },
    handleDel(res){
      if(res.shareLog.isPass!=0){
        this.$message({
          type: 'warning',
          message: '不可重复操作'
        });

        return false;
      }
      approveFail(res).then(res => {
        this.$message({
          type: 'success',
          message: '审核失败'
        });
        this.onSearch();  
      }) 
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
    //删除之后通知分页器刷新
    itemDelete(val){
      this.item.shareId = val
      del(this.item).then(res => {
        this.$message({
          type: 'success',
          message: '删除成功'
        });
        this.onSearch();  
      })
    },

    //分页器自动调用
    getList(page){
      this.currentRow = null
      list(page).then(res => {
        this.tabledata = res.data.records
        this.page.pages = res.data.pages
        this.page.total = res.data.total
      })
    },
    //搜索时调用分页器的功能
    onSearch(){
      this.$refs.pagination.parentHandleclick(this.page);
    },
    sendParams (res) {
      this.$router.push({
          name: 'edit',
          query: {
            id: res
          }
      })
    }
  }
}

</script>
<style lang='scss' scoped>

</style>
