<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入用户ID" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="4">
          <el-input  placeholder="请输入账号名" maxlength="15" minlength="1" clearable v-model="page.search2"></el-input>
        </el-col>
        <el-col :span="1">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
        <el-col :span="10">
          <FilenameOption v-if="this.listQuery.type == 1" v-model="filename" />
        </el-col>
        <el-col :span="2">
          <el-button v-if="this.listQuery.type == 1" :loading="downloadLoading"  type="primary" icon="el-icon-download" @click="handleDownload">导出</el-button>
        </el-col>
        <el-col :span="3">
          <el-button v-if="this.listQuery.type == 1" style="margin-left: 10px" type="primary" icon="el-icon-star-on" @click="handleLabel">公众号标签</el-button>
        </el-col>
      </el-row>

    </el-header>
    <el-main>
  <el-table :data="list" border fit highlight-current-row
            v-loading="loading"
            style="width: 100%"
            element-loading-text="拼命加载中"
            empty-text="暂无数据">
    <el-table-column label="账号ID" width="65" prop="accountId"></el-table-column>
    <el-table-column width="130px" label="账号名称" prop="accountName" show-overflow-tooltip="true">
    </el-table-column>
    <el-table-column width="80px" label="用户ID" prop="userId"></el-table-column>
    <el-table-column width="130px" label="用户名称" prop="userName" show-overflow-tooltip="true"></el-table-column>
    <el-table-column width="120px" label="微信号" prop="uid" show-overflow-tooltip="true"></el-table-column>
    <el-table-column width="110px" label="状态">
      <template slot-scope="scope">
        <el-tag v-if="scope.row.accountState == 1"> {{scope.row.accountState | accStatusFilter}}</el-tag>
        <el-tag v-else type="danger"> {{scope.row.accountState | accStatusFilter}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column v-if="this.listQuery.type == 2" width="130px" label="驳回原因" prop="nopassReason" show-overflow-tooltip="true">
    </el-table-column>
    <el-table-column label="添加时间" width="160" prop="addTime">
      <template slot-scope="scope">
        <span> {{scope.row.addTime | formatDate}}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作" width="150" >
      <template slot-scope="scope" >
        <el-button v-if="scope.row.accountState == 1" size="mini" type="primary" @click="handleShow(scope.row)">查看</el-button>
        <el-button v-if="scope.row.accountState == 1" size="mini" type="danger" @click="handleDel(scope.row)">删除</el-button>
        <el-button v-else-if="scope.row.accountState == 0" size="mini" type="primary" @click="handleShow(scope.row)">审核</el-button>
        <el-button v-else size="mini" type="primary" @click="handleShow(scope.row)">查看</el-button>
      </template>

    </el-table-column>
  </el-table>
    </el-main>
    <el-footer height="40">
      <pagination
        ref="pagination"
        :total="page.total"
        @pageChange="getList">
      </pagination>
    </el-footer>

    <el-dialog title="账号详情" :visible.sync="dialogFormVisible">
      <el-form :model="form" label-width="80px">
        <el-form-item label="头像" prop="accountAvatar">
          <img style="width: 100px; height: 100px;border-radius:50%;"
               :src="form.accountAvatar">
        </el-form-item>

        <el-col :span="18">
        <el-form-item label="账号名称" prop="accountName">
          <el-input disabled v-model="form.accountName">
          </el-input>
        </el-form-item>
        </el-col>

        <el-col :span="6">
        <el-form-item label="性别" prop="gender">
          <el-input v-model="form.gender" disabled></el-input>
        </el-form-item>
        </el-col>

        <el-col :span="12">
        <el-form-item label="粉丝数" prop="followNum">
          <el-input v-model="form.followNum" disabled></el-input>
        </el-form-item>
        </el-col>

        <el-col :span="12">
        <el-form-item label="关注数" prop="friendsCount">
          <el-input v-model="form.friendsCount" disabled></el-input>
        </el-form-item>
        </el-col>

        <el-form-item label="驳回理由" prop="nopassReason">
        <el-input type="textarea"
                  :rows="2"
                  v-model="form.nopassReason"></el-input>
      </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer" v-if="form.accountState == 0">
        <el-button type="danger" @click="handleReject">拒 绝</el-button>
        <el-button type="primary" @click="handlePass">通 过</el-button>
      </div>
    </el-dialog>

    <el-dialog title="标签列表" :visible.sync="dialogTableVisible" width="45%">

      <el-dialog
        width="25%"
        title="新增标签"
        :visible.sync="innerVisible"
        append-to-body>
        <el-form ref="tagform" :rules="rules" :model="form" label-width="100px">
          <el-form-item label="标签名称" prop="tagName">
            <el-input v-model="tagform.tagName" clearable maxlength="10" minlength="2" ></el-input>
          </el-form-item>
          <el-form-item>
            <el-button  @click="innerVisible = false">取消</el-button>
            <el-button  type="primary" @click="submitTagForm">确定</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>

      <el-button icon="el-icon-plus" type="primary" @click="handleAddTag" style="margin-bottom: 10px">新增</el-button>
      <el-table :data="taglist" border fit highlight-current-row
                v-loading="loading"
                style="width: 100%"
                height="300px"
                element-loading-text="拼命加载中"
                empty-text="暂无数据">
        <el-table-column label="ID" width="65" prop="tagId"></el-table-column>

        <el-table-column  width="150" label="名称" prop="tagName">

          <template slot-scope="{row}">
            <template v-if="row.edit">
              <el-input v-model="row.tagName" size="small" class="edit-input"/>
            </template>
            <span v-else>{{row.tagName}}</span>
          </template>

        </el-table-column>

        <el-table-column width="150px" label="排序" prop="tagSort">

          <template slot-scope="{row}">
            <template v-if="row.edit">
              <el-input v-model="row.tagSort" size="small" class="edit-input"/><el-button class="cancel-btn" circle icon="el-icon-refresh" type="warning" @click="cancelEdit(row)"></el-button>
            </template>
            <span v-else>{{row.tagSort}}</span>
          </template>

        </el-table-column>


        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="{row}">
            <el-button v-if="row.edit" type="primary" icon="el-icon-check" circle @click="confirmEdit(row)"></el-button>
            <el-button v-else type="success" icon="el-icon-edit" circle @click="row.edit=!row.edit"></el-button>
            <el-button  type="danger" icon="el-icon-delete" circle @click="deleteTag(row)"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>

  </el-container>

</template>

<script>
  import { getAccounts,delAcc,getAccById,rejectAccById,passAccById,getTagList,addTag,updateTag,delTag} from '@/api/customer'
  import pagination from '@/components/pagination'
  import { delay } from '@/utils/index'
  import FilenameOption from '@/views/enduser/components/FilenameOption'
export default {
  components: {
    pagination,
    FilenameOption
  },
  props: {
    type: {
      type: String,
      default: '1'
    }
  },
  data() {
    return {
      list: null,
      taglist:null,
      listQuery: {
        type: this.type,
      },
      form: {
        accountId:null,
        accountName: null,
        accountAvatar: '',
        followNum:null,
        gender:'',
        friendsCount:0,
        statusesCount:0,
        accountState:0,
        nopassReason:null
      },
      tagform:{
        tagId:null,
        tagName:null,
        tagSort:0
      },
      page: {
        pages: 1,
        search1:"",
        search2:"",
        total: 0
      },
      accountId:null,
      loading: false,
      dialogFormVisible: false,
      dialogTableVisible: false,
      innerVisible:false,
      downloadLoading:false,
      filename:''
    }
  },
  created() {
  },
  methods: {
    submitTagForm() {
          addTag(this.tagform).then(res => {
            this.$message({
              message: '添加成功',
              type: 'success'
            });
            this.innerVisible = false;
            this.handleLabel();
          })
    },
    handleAddTag(){
      this.innerVisible = true
    },
    async handleLabel(){
      const res  = await getTagList()
      const items = res.data
      this.taglist = items.map(v => {
        this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
        v.originalTagName = v.tagName //  will be used when user click the cancel botton
        v.originalTagSort = v.tagSort
        return v
      })
      this.dialogTableVisible = true
    },
    handleDownload() {
      this.downloadLoading = true
      import('@/vendor/Export2Excel').then(excel => {
        const tHeader = ['账号Id', '账号名称', '用户Id', '用户名称', '微博Id', '状态', '添加时间']
        const filterVal = ['accountId', 'accountName', 'userId', 'userName', 'uid', 'accountState','addTime']
        const list = this.list
        const data = this.formatJson(filterVal, list)
        excel.export_json_to_excel({
          header: tHeader,
          data,
          filename: this.filename,
          autoWidth: true,
          bookType: 'xlsx'
        })
        this.downloadLoading = false
      })
    },
    formatJson(filterVal, jsonData) {
      return jsonData.map(v => filterVal.map(j => {
          return v[j]

      }))
    },
    handleReject(){
      this.itemReject(this.form)
      this.dialogFormVisible = false
    },
    handlePass(){
      this.itemPass(this.form.accountId)
      this.dialogFormVisible = false
    },
    handleShow(res){
      this.itemShow(res.accountId);
      this.dialogFormVisible = true
    },
    handleDel(res){
      this.$confirm('确定删除该账号?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
          this.itemDelete(res.accountId)
      }).catch(() => {

      });
    },
    deleteTag(res){
      this.$confirm('确定删除该标签?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.tagDelete(res.tagId)
      }).catch(() => {

      });
    },
    getList(page) {
      this.loading = true
      this.currentRow = null
      getAccounts(page,1,this.listQuery.type).then(res => {
        this.list = res.data.records
        this.page.pages = res.data.pages
        this.page.total = res.data.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    onSearch(){
      this.$refs.pagination.parentHandleclick(this.page);
    },
    itemDelete(val){
      delAcc(val).then(res => {
        this.$message({
          type: 'success',
          message: '删除成功'
        });
        this.onSearch();
      })
    },
    tagDelete(val){
      delTag(val).then(res => {
        this.$message({
          type: 'success',
          message: '删除成功'
        });
        this.handleLabel();
      })
    },
    itemReject(val){
      rejectAccById(val).then(res => {
        this.$message({
          type: 'success',
          message: '操作成功'
        });
        this.onSearch();
      })
    },
    itemPass(val){
      passAccById(val).then(res => {
        this.$message({
          type: 'success',
          message: '审核成功'
        });
        this.onSearch();
      })
    },
    itemShow(val){
      getAccById(val).then(res => {
        this.form = res.data
      })
    },
    cancelEdit(row) {
      row.tagName = row.originalTagName
      row.tagSort = row.originalTagSort
      row.edit = false
        this.$message({
        message: '取消编辑',
        type: 'warning'
      })
    },
    confirmEdit(row) {
      row.edit = false
      this.tagform.tagId = row.tagId
      this.tagform.tagName = row.tagName
      this.tagform.tagSort = row.tagSort
      updateTag(this.tagform).then(res => {
        this.$message({
          type: 'success',
          message: '更新成功'
        });
        this.tagform.tagId = null;
        this.tagform.tagName = null;
        this.tagform.tagSort = 0;
        this.handleLabel();
      })
    }
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
    }
  }
}
</script>
<style scoped>
  .edit-input {
    padding-right: 50px;
    width: 120px;
  }
  .cancel-btn {
    position: absolute;
    right: 20px;
    top: 12px;
  }
</style>
