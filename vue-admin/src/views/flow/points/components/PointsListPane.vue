<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入订单编号" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="4">
          <el-input  placeholder="请输入用户名称" maxlength="5" minlength="1" clearable v-model="page.search2"></el-input>
        </el-col>
        <el-col :span="4">
          <el-input  placeholder="请输入交易号" maxlength="5" minlength="1" clearable v-model="page.search3"></el-input>
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
                stripe height="360px">
        <el-table-column prop="orderSn" label="订单编号" sortable width="180px"></el-table-column>
        <el-table-column prop="userId" label="用户编号" ></el-table-column>
        <el-table-column prop="userName" label="用户昵称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="coinAmount" label="充值积分" >
          <template slot-scope="scope">
            <span> {{scope.row.coinAmount | formatDecimal}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="orderAmount" label="订单金额" >
          <template slot-scope="scope">
            <span> {{scope.row.orderAmount | formatDecimal}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="payState" label="支付状态" width="100px" show-overflow-tooltip>
          <template slot-scope="scope">
            <el-tag v-if="scope.row.payState == 1"> {{scope.row.payState | payStatusFilter}}</el-tag>
            <el-tag v-else type="danger"> {{scope.row.payState | payStatusFilter}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="payType" label="支付方式" width="100px" show-overflow-tooltip>
          <template slot-scope="scope">
            <span> {{scope.row.payType | payTypeFilter}}</span>
          </template>
        </el-table-column>
        <el-table-column  prop="payOutSn" label="交易号" show-overflow-tooltip></el-table-column>
        <el-table-column  prop="payTime" label="支付时间"  width="170px" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.payState == 1"> {{scope.row.payTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column  prop="addTime" label="添加时间"  width="170px" align="center">
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
        @pageChange="getList"
      >
      </pagination>
    </el-footer>
  </el-container>
</template>

<script>
  import { pointsList} from '@/api/flow'
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
        tabledata: [],
        loading: true
      }
    },
    methods: {
      //分页器自动调用
      getList(page){
        this.loading = true
        this.currentRow = null
        pointsList(page).then(res => {
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
      },
    }

  }

</script>
<style lang='scss' scoped>

</style>
