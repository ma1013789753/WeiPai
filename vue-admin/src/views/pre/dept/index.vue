<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入部门名称" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="1">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
        <el-col :span="19" style="text-align:right">
            <router-link class="inlineBlock" to="/pre/dept/add">
              <el-button type="primary">新增</el-button>
            </router-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata" stripe height="380px">
        <el-table-column
          type="index" prop="date" label="编号" sortable width="60px">
        </el-table-column>
        <el-table-column
          prop="name" label="部门名称" sortable>
        </el-table-column>
        <el-table-column 
          prop="memo" label="部门描述" sortable >
        </el-table-column>
        <el-table-column
          prop="createTime" label="创建时间" sortable>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
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
        <pagination ref="pagination" :total="page.pages" @pageChange="getList"></pagination>
    </el-footer>
  </el-container>
</template>

<script>
import { list,del} from '@/api/sysdept'
import pagination from '@/components/pagination'
export default {
  components: {
    pagination,
  },
  data() {
      return {
        page: {
          pages: 1,
          search1:"",
          search2:""
        },
        item: {
          tid:null
        },
        tabledata: []
      }
    },
  created() {
    
  },
  mounted() {},
  methods: {
    handleEdit(res){
      this.sendParams(res.tid);
    },
    handleDel(res){
        this.$confirm('确定删除该条目, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.itemDelete(res.tid)
        }).catch(() => {
       
        });
    },
    //删除之后通知分页器刷新
    itemDelete(val){
      this.item.tid = val
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
      list(page).then(res => {
        this.tabledata = res.data.records
        this.page.pages = res.data.pages
      })
    },
    //搜索时调用分页器的功能
    onSearch(){
      this.$refs.pagination.parentHandleclick(this.page);
    },
    sendParams (res) {
      this.$router.push({
          name: 'editDept',
          // params: { 
          //     name: 'rid', 
          //     dataObj: res
          // },
          query: {
            tid: res
          }
      })
    }

  }
}

</script>
<style lang='scss' scoped>

</style>
