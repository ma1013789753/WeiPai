<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入用户昵称" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="10">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata"
                v-loading="loading"
                element-loading-text="拼命加载中"
                empty-text="暂无数据"
                width="500px"
                stripe height="400px">
        <el-table-column prop="userName" label="反馈人" show-overflow-tooltip width="120px"></el-table-column>
        <el-table-column prop="userId" label="反馈人ID" width="80px"></el-table-column>
        <el-table-column prop="feedbackContent" label="反馈内容" show-overflow-tooltip ></el-table-column>
        <el-table-column prop="userMail" label="联系方式" show-overflow-tooltip width="180px"></el-table-column>
        <el-table-column  prop="addTime" label="反馈时间"  width="170px" align="center">
          <template slot-scope="scope">
            <span> {{scope.row.addTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="90" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="success"
              v-if="scope.row.isRead == 0"
              @click="handleRead(scope.row)"
            >待处理</el-button>
            <el-tag
              v-else
            >已读</el-tag>
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
  import { getFeedback,handleFeedback} from '@/api/notification'
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
        tabledata: [],
        loading: true,
        item: {
          feedbackId:null
        },
      }
    },
    methods: {
      handleRead(val){
        this.item.feedbackId = val.feedbackId
        handleFeedback(this.item).then(res => {
          this.$message({
            type: 'success',
            message: '操作成功'
          });
          this.onSearch();
        })
      },
      //分页器自动调用
      getList(page){
        this.loading = true
        this.currentRow = null
        getFeedback(page).then(res => {
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
