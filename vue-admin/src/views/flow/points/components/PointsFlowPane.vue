<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入用户名称" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="1">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
      </el-row>
    </el-header>
    <el-main>
  <el-table :data="tabledata" border fit highlight-current-row
            v-loading="loading"
            style="width: 100%"
            stripe
            height="360px"
            element-loading-text="拼命加载中"
            empty-text="暂无数据">
    <el-table-column  prop="logUserId" label="用户ID" sortable width="100px"></el-table-column>
    <el-table-column  prop="logUserName" label="用户名称" show-overflow-tooltip ></el-table-column>
    <el-table-column prop="logType" label="收入类型" show-overflow-tooltip>
      <template slot-scope="scope">
        <span> {{scope.row.logType | incomeTypeFilter}}</span>
      </template>
    </el-table-column>
    <el-table-column prop="logAvCoin" label="可用积分变更" >
      <template slot-scope="scope">
        <span> {{scope.row.logAvCoin | formatDecimal}}</span>
      </template>
    </el-table-column>
    <el-table-column prop="logFreezeCoin" label="冻结积分变更" >
      <template slot-scope="scope">
        <span> {{scope.row.logFreezeCoin | formatDecimal}}</span>
      </template>
    </el-table-column>
    <el-table-column prop="addTime" label="添加时间" width="170px" align="center">
      <template slot-scope="scope">
        <span> {{scope.row.addTime | formatDate}}</span>
      </template>
    </el-table-column>
    <el-table-column prop="logAdminName" label="添加人" ></el-table-column>
  </el-table>
    </el-main>
    <el-footer height="40">
      <pagination
        ref="pagination"
        :total="page.total"
        @pageChange="getList">
      </pagination>
    </el-footer>
  </el-container>

</template>

<script>
  import { pointsFlow} from '@/api/flow'
  import pagination from '@/components/pagination'
  import { delay } from '@/utils/index'
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
          total: 0

        },
        item: {
          shareId:null
        },
        tabledata: [],
        loading: true
      }
    },
    mounted() {},
    methods: {
      //分页器自动调用
      getList(page){
        this.loading = true
        this.currentRow = null
        pointsFlow(page).then(res => {
          this.tabledata = res.data.records
          this.page.pages = res.data.pages
          this.page.total = res.data.total
          this.loading = false
        })
      },
      onSearch(){
        this.$refs.pagination.parentHandleclick(this.page);
      },
    },
    watch:{
      "page.search1" (){
        delay(() => {
          this.onSearch()
        },500);
      }
    }
  }

</script>


