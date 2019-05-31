<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
        <el-input  placeholder="请输入用户昵称" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
      </el-col>
        <el-col :span="4">
          <el-input  placeholder="请输入用户手机号" maxlength="5" minlength="1" clearable v-model="page.search2"></el-input>
        </el-col>
        <el-col :span="4">
          <el-input  placeholder="请输入用户ID" maxlength="5" minlength="1" clearable v-model="page.search3"></el-input>
        </el-col>
        <el-col :span="1">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata"
                v-loading="loading"
                element-loading-text="拼命加载中"
                empty-text="暂无数据"
                stripe height="380px">
        <el-table-column prop="userId" label="用户ID" sortable width="90px"></el-table-column>
        <el-table-column prop="userName" label="用户昵称" show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="userMobile" label="用户手机" width="110px" ></el-table-column>
        <el-table-column prop="availablePredeposit" label="用户余额" >
          <template slot-scope="scope">
            <span> {{scope.row.availablePredeposit | formatDecimal}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="userCoin" label="用户积分" >
          <template slot-scope="scope">
            <span> {{scope.row.userCoin | formatDecimal}}</span>
          </template>
        </el-table-column>
        <el-table-column  prop="userState" label="用户状态" width="80px">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.userState == 0"> {{scope.row.userState | statusFilter}}</el-tag>
            <el-tag v-else type="danger"> {{scope.row.userState | statusFilter}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column  prop="addTime" label="注册时间"  width="170px" align="center">
        <template slot-scope="scope">
          <span> {{scope.row.addTime | formatDate}}</span>
        </template>
      </el-table-column>
        <el-table-column  label="签到记录"  width="100px" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="success" icon="el-icon-view" @click="handleSign(scope.row)">查看</el-button>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="290" align="center">
          <template slot-scope="scope">
            <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.row)"
          >充值</el-button>
            <el-button
              size="mini"
              type="primary"
              @click="handleAcc(scope.row)"
            >账号</el-button>
            <el-button v-if="scope.row.userState == 0" size="mini" type="warning" @click="handleFreeze(scope.row)">禁用</el-button>
            <el-button v-else size="mini" type="success" @click="handleUnfreeze(scope.row)">解禁</el-button>
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
        <pagination
          ref="pagination"
          :total="page.total"
          @pageChange="getList"
          >
        </pagination>
    </el-footer>
  </el-container>
</template>

<script>
import { list,del,freeze,unfreeze} from '@/api/customer'
import { delay } from '@/utils/index'
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
          total: 0
        },
        item: {
          uid:null
        },
        tabledata: [],
        loading: true
      }
    },
  created() {
  },
  mounted() {},
  methods: {
    handleEdit(res){
      this.sendParams(res.userId);
    },
    handleAcc(res){
      this.accParams(res.userId);
    },
    handleSign(res){
      this.signParams(res.userId);
    },
    handleFreeze(res){
      this.$confirm('确定封禁该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.itemFreeze(res.userId)
      }).catch(() => {

      });
    },
    handleUnfreeze(res){
      this.$confirm('解封该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.itemUnfreeze(res.userId)
      }).catch(() => {

      });
    },
    handleDel(res){
      alert(res.userId)
        this.$confirm('确定删除该用户, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.itemDelete(res.userId)
        }).catch(() => {
       
        });
    },
    //封禁
    itemFreeze(val){
      this.item.uid = val
      freeze(this.item).then(res => {
        this.$message({
          type: 'success',
          message: '操作成功'
        });
        this.onSearch();
      })
    },
    //解封
    itemUnfreeze(val){
      this.item.uid = val
      unfreeze(this.item).then(res => {
        this.$message({
          type: 'success',
          message: '操作成功'
        });
        this.onSearch();
      })
    },
    //删除之后通知分页器刷新
    itemDelete(val){
      this.item.uid = val
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
      this.loading = true
      this.currentRow = null
      list(page).then(res => {
        this.tabledata = res.data.records
        this.page.pages = res.data.pages
        this.page.total = res.data.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    //搜索时调用分页器的功能
    onSearch(){
      this.$refs.pagination.parentHandleclick(this.page);
    },
    sendParams (res) {
      this.$router.push({
          name: 'deposit',
          query: {
            uid: res
          }
      })
    },
    accParams (res) {
      this.$router.push({
        name: 'account',
        query: {
          uid: res
        }
      })
    },
    signParams (res) {
      this.$router.push({
        name: 'sign',
        query: {
          uid: res
        }
      })
    },

  },
  watch:{
      "page.search1" (){
        delay(() => {
          this.onSearch()
        },500);
      },
    "page.search2" (){
      delay(() => {
        this.onSearch()
      },500);
    },
    "page.search3" (){
      delay(() => {
        this.onSearch()
      },500);
    }
  }

}

</script>
<style lang='scss' scoped>

</style>
