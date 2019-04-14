<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入菜单名" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="1">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
        <el-col :span="19" style="text-align:right">
            <!-- <router-link class="inlineBlock" to="/pre/user/add">
              <el-button type="primary">新增</el-button>
            </router-link> -->
        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata" stripe height="380px" >
        <el-table-column
          prop="pid" label="编号" sortable width="80px">
        </el-table-column>
        <el-table-column
          prop="title" label="菜单名称" sortable>
        </el-table-column>
        <el-table-column
          prop="icon" label="资源图标" sortable>
        </el-table-column>
        <el-table-column 
          prop="father" label="父菜单" sortable >
        </el-table-column>
        <el-table-column 
          prop="createTime" label="创建时间" sortable width="180px">
        </el-table-column>
        <el-table-column label="操作" width="80">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.row)"
              >编辑</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer height="40">
        <pagination ref="pagination" :total="page.pages" @pageChange="getList"></pagination>
    </el-footer>

    <el-dialog title="编辑菜单" :visible.sync="dialogTableVisible" lock-scroll height="60%">
      <template center>
        <el-form ref="currentRow" :rules="rules" :model="currentRow" label-width="80px">
          <el-form-item label="菜单名称" prop="title">
            <el-input v-model="currentRow.title" clearable maxlength="10" minlength="2" ></el-input>
          </el-form-item>
          <el-form-item label="菜单图标">
            <el-input v-model="currentRow.icon" clearable prop="icon"  placeholder="请输入内容">
            </el-input>
          </el-form-item>
          <el-form-item>

          </el-form-item>
        </el-form>
      </template>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="editMenu">确 定</el-button>
      </span>
    </el-dialog>
  </el-container>
</template>

<script>
import { list,edit,getMenuVo} from '@/api/sysperm'
import pagination from '@/components/pagination'
export default {
  components: {
    pagination,
  },
  data() {
      return {
        dialogTableVisible:false,
        page: {
          pages: 1,
          search1:"",
          search2:""
        },
        item: {
          uid:null
        },
        currentRow:{},
        tabledata: [],
        rules: {
          title: [
            { required: true, message: '请输入菜单名称', trigger: 'blur' },
            { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
          ],
          icon: [
            { required: true, message: '请输入角色图标', trigger: 'blur' },
            { min: 2, max: 10, message: '长度在 2 到 10 个字符', trigger: 'blur' }
          ],
        }
      }
    },
  created() {
    // this.getMenus()
  },
  mounted() {},
  methods: {
    handleEdit(res){

      this.currentRow = res;
      this.dialogTableVisible = true;

    },
    handleDel(res){
        this.$confirm('确定删除该条目, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.itemDelete(res.uid)
        }).catch(() => {
       
        });
    },
    //删除之后通知分页器刷新
    itemDelete(val){
      this.item.uid = val
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
    getMenus(){
      getMenuVo().then(res =>{
        this.tabledata = res.data
      })
    },
    editMenu(){
      this.dialogTableVisible = false
      edit(this.currentRow).then(res =>{
        this.$message({
          type: 'success',
          message: '编辑成功,重新登录生效'
        });
        this.onSearch();  
      })
    }

  }
}

</script>
<style lang='scss' scoped>

td {
	padding: 120px 150px !important;

  
}
.el-table__expanded-cell{
  background-color: darkgrey !important;
}

</style>
