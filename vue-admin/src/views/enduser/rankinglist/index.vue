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
                style="width: 920px;"
                empty-text="暂无数据"
                stripe height="380px"
                @current-change="handleCurrentChange">
        <el-table-column prop="userId" label="用户ID" sortable width="90px"></el-table-column>
        <el-table-column prop="userName" label="用户昵称" show-overflow-tooltip="true" width="100px"></el-table-column>
        <el-table-column prop="userMobile" label="用户手机" width="110px" ></el-table-column>
        <el-table-column prop="shareCount" label="分享次数"  width="80"></el-table-column>
        <el-table-column prop="availablePredeposit" label="可用余额" width="90">
          <template slot-scope="scope">
            <span> {{scope.row.availablePredeposit | formatDecimal}}</span>
          </template>
        </el-table-column>
          <el-table-column prop="freezePredeposit" label="冻结余额" width="90">
            <template slot-scope="scope">
              <span> {{scope.row.freezePredeposit | formatDecimal}}</span>
            </template>
          </el-table-column>
        <el-table-column prop="userCoin" label="用户积分" width="90">
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
      </el-table>
    </el-main>
    <el-footer height="40">
      <pagination
        ref="pagination"
        :total="page.total"
        style="width: 920px;"
        @pageChange="getList"
      >
      </pagination>
    </el-footer>
  </el-container>
</template>

<script>
  import { ranking} from '@/api/customer'
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
          id:null
        },
        tabledata: [],
        loading: true
      }
    },
    created() {
    },
    mounted() {},
    methods: {
      handleCurrentChange(val){
        this.loading = true
        this.values = [];
        this.currentRow = val
        val.permissions.forEach((perm, index) => {
          if(perm.father != 0){
            this.values.push(perm.pid)
          }
        })

      },
      //分页器自动调用
      getList(page){
        this.loading = true
        this.currentRow = null
        ranking(page).then(res => {
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
