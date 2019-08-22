<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入商品名" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="1">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
        <el-col :span="15" style="text-align:right">
          <router-link class="inlineBlock" to="/shoplist/add">
            <el-button type="primary">新增</el-button>
          </router-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata" stripe height="480px">
        <el-table-column
          type="index" prop="date" label="编号" sortable width="60px">
        </el-table-column>
        <el-table-column
          prop="shop.title" label="商品名" sortable>
        </el-table-column>
        <el-table-column
          prop="name" label="用户名" sortable>
        </el-table-column>
        <el-table-column
          prop="address" label="配送地址" sortable>
        </el-table-column>
        <el-table-column
          prop="user.userMobile" label="联系方式" sortable >
        </el-table-column>
        <el-table-column
          prop="updateTime" label="更新时间" sortable min-width="120px">
        </el-table-column>
        <el-table-column
          prop="state" label="状态" sortable min-width="120px">
          <template slot-scope="scope">
            <el-tag
              v-if="scope.row.state == '0'"
              size="mini"
              type="danger"
            >申请中</el-tag>
            <el-tag
              v-if="scope.row.state == '1'"
              size="mini"
              type="danger"
            >已同意</el-tag>
            <el-tag
              v-if="scope.row.state == '2'"
              size="mini"
              type="danger"
            >已驳回</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="150">
          <template slot-scope="scope">
            <el-button

              size="mini"
              type="success"
              @click="handleEdit(scope.row)"
            >同意</el-button>
            <el-button

              size="mini"
              type="danger"
              @click="handleDel(scope.row)"
            >驳回</el-button>
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
  import { list,approve,fail} from '@/api/shophis'
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
          uid:null
        },
        tabledata: []
      }
    },
    created() {

    },
    mounted() {},
    methods: {
      handleEdit(res){
        this.approve(res);
      },
      handleDel(res){
        this.$confirm('确定驳回该商品, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.itemDelete(res)
        }).catch(() => {

        });
      },
      //删除之后通知分页器刷新
      itemDelete(val){
        if(val.state != '0'){
          return
        }
        fail(val.id).then(res => {
          this.$message({
            type: 'success',
            message: '删除成功'
          });
          this.onSearch();
        })
      },

      //删除之后通知分页器刷新
      approve(val){
        if(val.state == '1'){
          return
        }
        approve(val.id).then(res => {
          this.$message({
            type: 'success',
            message: '成功'
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
          name: 'editUser',
          // params: {
          //     name: 'rid',
          //     dataObj: res
          // },
          query: {
            uid: res
          }
        })
      }

    }
  }

</script>
<style lang='scss' scoped>
  .avatar {
    width: 80px;
    height: 80px;
    display: block;
  }
</style>
