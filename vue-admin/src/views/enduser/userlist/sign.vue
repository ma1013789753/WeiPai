<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入签到时间" maxlength="10" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="1">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
      </el-row>
    </el-header>
    <el-main>
      <el-table highlight-current-row border :data="tabledata"
                v-loading="loading"
                element-loading-text="拼命加载中"
                style="width: 410px;"
                empty-text="暂无数据"
                stripe height="380px"
                @current-change="handleCurrentChange">
        <el-table-column prop="signId" label="签到ID" sortable width="90px"></el-table-column>
        <el-table-column prop="userName" label="用户昵称" show-overflow-tooltip="true" width="100px"></el-table-column>
        <el-table-column prop="signCoin" label="签到奖励" width="80px" >
          <template slot-scope="scope">
            <span> {{scope.row.signCoin | formatDecimal}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="signDate" label="签到日期"  width="120"></el-table-column>
      </el-table>
    </el-main>
    <el-footer height="40">
      <pagination
        ref="pagination"
        :total="page.total"
        style="width: 410px;"
        @pageChange="getList"
      >
      </pagination>
    </el-footer>
  </el-container>
</template>

<script>
  import { getSignById} from '@/api/customer'
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
        this.item.id = this.$route.query.uid
        this.loading = true
        this.currentRow = null
        getSignById(page,this.item.id).then(res => {
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
    }

  }

</script>
<style lang='scss' scoped>

</style>
