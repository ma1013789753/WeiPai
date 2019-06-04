<template>
  <el-container>
    <el-main>
  <el-table :data="tabledata" border fit highlight-current-row
            v-loading="loading"
            style="width: 100%"
            stripe
            height="400px"
            element-loading-text="拼命加载中"
            empty-text="暂无数据">
    <el-table-column  prop="shareId" label="用户ID" sortable width="100px"></el-table-column>
    <el-table-column  prop="userName" label="用户名称" sortable width="120px" show-overflow-tooltip></el-table-column>
    <el-table-column prop="shareType" label="转发账号" show-overflow-tooltip :formatter="getIsFrom"></el-table-column>
    <el-table-column prop="shareNum" label="评论内容" show-overflow-tooltip ></el-table-column>
    <el-table-column v-if="this.type != 2" prop="haveSharedNum" label="状态" show-overflow-tooltip></el-table-column>
    <el-table-column prop="addTime" label="转发时间" width="150px" :formatter="getTime"> </el-table-column>
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
  import { list} from '@/api/spare'
  import pagination from '@/components/pagination'
  export default {
    components: {
      pagination,
    },
    props: {
      type: {
        type: String,
        default: '1'
      }
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
          total: 0

        },
        item: {
          shareId:null
        },
        tabledata: [],
        loading: true
      }
    },
    created() {
    },
    mounted() {},
    methods: {
      //分页器自动调用
      getList(page){
        this.loading = true
        this.currentRow = null
        list(page,this.type).then(res => {
          this.tabledata = res.data.records
          this.page.pages = res.data.pages
          this.page.total = res.data.total
          this.loading = false
        })
      },
    }
  }

</script>


