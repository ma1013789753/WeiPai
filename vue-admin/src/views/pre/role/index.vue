<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入角色名" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="1">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
        <el-col :span="19" style="text-align:right">
            <el-button type="primary" @click="rolePerm">角色授权</el-button>
            <router-link class="inlineBlock" to="/pre/role/add">
              <el-button type="primary">新增角色</el-button>
            </router-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata" stripe height="380px" @current-change="handleCurrentChange">
        <el-table-column
          type="index" prop="date" label="编号" sortable width="60px">
        </el-table-column>
        <el-table-column
          prop="name" label="角色名称" sortable>
        </el-table-column>
        <el-table-column 
          prop="memo" label="描述" sortable >
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
    <el-dialog title="角色授权" :visible.sync="dialogTableVisible" lock-scroll height="60%">
      <template center>
        <el-transfer
          :titles="['可分配权限', '角色权限']"
          v-model="values"
          :data="data">
        </el-transfer>
      </template>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogTableVisible = false">取 消</el-button>
        <el-button type="primary" @click="resetRole">确 定</el-button>
      </span>
    </el-dialog>
      
  </el-container>
</template>

<script>
import { list,del,setRole} from '@/api/sysrole'
import { getMenuVo} from '@/api/sysperm'
import pagination from '@/components/pagination'
export default {
  components: {
    pagination,
  },
  data() {
      return {
        dialogTableVisible:false,
        currentRow:null,
        data: [],
        values:[],
        page: {
          pages: 1,
          search1:"",
          search2:""
        },
        item: {
          rid:null
        },
        tabledata: []
      }
    },
  created() {
    this.getMenus()
  },
  mounted() {},
  methods: {
    handleEdit(res){
      this.sendParams(res.rid);
    },
    handleDel(res){
        this.$confirm('确定删除该条目, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.itemDelete(res.rid)
        }).catch(() => {
       
        });
    },
    handleCurrentChange(val){
      this.values = [];
      this.currentRow = val
      val.permissions.forEach((perm, index) => {
        if(perm.father != 0){
          this.values.push(perm.pid)
        }
      })
    },
    //删除之后通知分页器刷新
    itemDelete(val){
      this.item.rid = val
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
      this.currentRow = null
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
          name: 'editRole',
          // params: { 
          //     name: 'rid', 
          //     dataObj: res
          // },
          query: {
            rid: res
          }
      })
    },
    getMenus(){
      getMenuVo().then(res =>{
        // this.data = res.data
        res.data.forEach((perm, index) => {
          if(perm.father != 0){
            this.data.push({
              label: perm.title,
              key: perm.pid,
              disabled:false
            })
          }
        })
      })
    },
    rolePerm(){
      if(this.currentRow){
        this.dialogTableVisible = true
      }else{
        this.$message({
          type: 'warning',
          message: '请选择一个角色'
        });
      }
    },
    resetRole(){
      this.dialogTableVisible = false
      let params = {
        pids:this.values,
        rid:this.currentRow.rid
      }
      setRole(params).then(res => {



      })

    }
    

  }
}

</script>
<style lang='scss' scoped>

</style>
