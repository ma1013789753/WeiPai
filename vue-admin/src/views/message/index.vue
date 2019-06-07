<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入消息内容" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="10">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
        <el-col :span="6" style="text-align: right">
            <el-button type="primary" icon="el-icon-circle-plus-outline" @click="dialogFormVisible = true">发布消息</el-button>
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
        <el-table-column prop="noticeId" label="ID" sortable width="90px"></el-table-column>
        <el-table-column prop="noticeContent" label="内容" show-overflow-tooltip></el-table-column>
        <el-table-column prop="authorName" label="发布者" width="110px" ></el-table-column>
        <el-table-column prop="userName" label="发送对象" show-overflow-tooltip width="100px">
          <template slot-scope="scope">
            <span v-if="scope.row.isAll == 1">{{scope.row.userName}}</span>
            <span v-else>所有人</span>
          </template>
        </el-table-column>
        <el-table-column  prop="addTime" label="发送时间"  width="170px" align="center">
          <template slot-scope="scope">
            <span> {{scope.row.addTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="90" align="center">
          <template slot-scope="scope">
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

    <el-dialog title="消息发布" :visible.sync="dialogFormVisible">
      <el-form  ref="msgform" label-width="80px">
        <el-form-item label="消息内容" prop="noticeContent">
          <el-input type="textarea"
                    :rows="4"
                   v-model="msgform.noticeContent"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" >
        <el-button type="danger" @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handleSubmit">发 布</el-button>
      </div>
    </el-dialog>
  </el-container>
</template>

<script>
  import { systemMsg,delMsg,addMsg} from '@/api/notification'
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
          noticeId:null
        },
        msgform:{
          noticeType:'system',
          noticeContent:null,
          authorName:'admin'
        },
        tabledata: [],
        loading: true,
        dialogFormVisible: false,
      }
    },
    methods: {
      handleDel(res){
        this.$confirm('确定删除该消息, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.itemDelete(res.noticeId)
        }).catch(() => {

        });
      },
      handleSubmit() {
        addMsg(this.msgform).then(res => {
          this.$message({
            message: '发布成功',
            type: 'success'
          });
          this.dialogFormVisible = false;
        })
      },
      //删除之后通知分页器刷新
      itemDelete(val){
        this.item.noticeId = val
        delMsg(this.item).then(res => {
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
        systemMsg(page).then(res => {
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
