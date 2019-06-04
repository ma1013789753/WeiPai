<!--  -->
<template>
  <el-container>
    <el-main >
      <el-table highlight-current-row border :data="tabledata"
                v-loading="loading"
                element-loading-text="拼命加载中"
                empty-text="暂无数据"
                stripe height="400px">
        <el-table-column prop="adId" label="ID" sortable width="60px"></el-table-column>
        <el-table-column prop="adType" label="显示位置" width="80px" show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="adImg" label="封面图" width="100px" align="center">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <img
                style="width: 200px; height: 200px"
                :src="scope.row.adImg"
                >
<!--              <p>姓名: {{ scope.row.adImg }}</p>-->
              <div slot="reference" class="name-wrapper">
                <el-tag size="medium">查看</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="adSort" label="排序" width="80px"></el-table-column>
        <el-table-column prop="addTime" label="添加时间"  width="170px" align="center">
          <template slot-scope="scope">
            <span> {{scope.row.addTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column  prop="adState" label="状态" width="80px">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.adState"> {{0 | statusFilter}}</el-tag>
            <el-tag v-else type="danger"> {{1 | statusFilter}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="220" align="center">
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
            >禁用</el-button>
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
  import { adlist,updateTag} from '@/api/cms'
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
          cmsId:null
        },
        tabledata: [],
        loading: true,
      }
    },
    mounted() {},
    methods: {
      handleEdit(res){
        this.sendParams(res.cmsId);
      },
      handleDel(res){
        this.$confirm('确定删除该广告, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.itemDelete(res.cmsId)
        }).catch(() => {

        });
      },
      //删除之后通知分页器刷新
      itemDelete(val){
        this.item.cmsId = val
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
        adlist(page).then(res => {
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
      handleFreeze(res){
        this.$confirm('确定禁用该广告, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.tagform.cateId = res.cateId
          this.tagform.cateSort = null
          updateTag(this.tagform,1).then(res => {
            this.$message({
              type: 'success',
              message: '操作成功'
            });
            this.tagform.cateId = null;
            this.tagform.cateName = null;
            this.tagform.cateSort = 0;
            this.handleLabel();
          })
        }).catch(() => {

        });
      },
      handleUnfreeze(res){
        this.$confirm('启用该广告, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.tagform.cateId = res.cateId
          this.tagform.cateSort = null
          this.tagform.cateState = null
          updateTag(this.tagform,2).then(res => {
            this.$message({
              type: 'success',
              message: '操作成功'
            });
            this.tagform.cateId = null;
            this.tagform.cateName = null;
            this.tagform.cateSort = 0;
            this.handleLabel();
          })
        }).catch(() => {

        });
      },
      sendParams (res) {
        this.$router.push({
          name: 'cmsedit',
          query: {
            cmsid: res
          }
        })
      },
    },

  }

</script>
