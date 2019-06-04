<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入资讯标题" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="10">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
          <el-button
            style="margin-left: 10px"
            type="primary" icon="el-icon-star-on"
            @click="handleLabel">资讯标签</el-button>
        </el-col>
        <el-col :span="6" style="text-align: right">
          <router-link class="inlineBlock" to="/cms/news/createcms">
            <el-button type="primary" icon="el-icon-circle-plus-outline">发布资讯</el-button>
          </router-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata"
                v-loading="loading"
                element-loading-text="拼命加载中"
                empty-text="暂无数据"
                stripe height="400px">
        <el-table-column prop="cmsId" label="资讯ID" sortable width="90px"></el-table-column>
        <el-table-column prop="cmsTitle" label="资讯标题" width="300px" show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="cateName" label="所属分类" width="100px" ></el-table-column>
        <el-table-column prop="seeNum" label="浏览量" width="80px"></el-table-column>
        <el-table-column prop="cmsSort" label="排序" sortable width="80px"></el-table-column>
        <el-table-column  prop="cmsAuthor" label="作者" width="80px"></el-table-column>
        <el-table-column  prop="addTime" label="发布时间"  width="170px" align="center">
          <template slot-scope="scope">
            <span> {{scope.row.addTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="160" align="center">
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
      <pagination
        ref="pagination"
        :total="page.total"
        @pageChange="getList"
      >
      </pagination>
    </el-footer>
    <el-dialog title="标签列表" :visible.sync="dialogTagVisible" width="49%">

      <el-dialog
        width="25%"
        title="新增标签"
        :visible.sync="innerVisible"
        append-to-body>
        <el-form ref="tagform"  label-width="100px">
          <el-form-item label="标签名称" prop="cateName">
            <el-input v-model="tagform.cateName" clearable maxlength="10" minlength="2" ></el-input>
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
        <el-table-column label="ID" width="65" prop="cateId"></el-table-column>

        <el-table-column  width="150" label="名称" prop="cateName">

          <template slot-scope="{row}">
            <template v-if="row.edit">
              <el-input v-model="row.cateName" size="small" class="edit-tag"/>
            </template>
            <span v-else>{{row.cateName}}</span>
          </template>

        </el-table-column>

        <el-table-column width="150px" label="排序" prop="cateSort">
          <template slot-scope="{row}">
            <template v-if="row.edit">
              <el-input v-model="row.cateSort" size="small" class="edit-tag"/><el-button class="cancel-tag" circle icon="el-icon-refresh" type="warning" @click="cancelEdit(row)"></el-button>
            </template>
            <span v-else>{{row.cateSort}}</span>
          </template>
        </el-table-column>

        <el-table-column  prop="cateState" label="状态" width="80px">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.cateState == 0"> {{scope.row.cateState | statusFilter}}</el-tag>
            <el-tag v-else type="danger"> {{scope.row.cateState | statusFilter}}</el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="150" align="center">
          <template slot-scope="{row}">
            <el-button
              size="mini"
              type="success"
              v-if="row.edit"
              @click="confirmEdit(row)"
            >确定</el-button>
            <el-button
              size="mini"
              type="primary"
              v-else
              @click="row.edit=!row.edit"
            >编辑</el-button>
            <el-button
              size="mini"
              type="danger"
              v-if="!row.cateState"
              @click="handleFreeze(row)"
            >禁用</el-button>
            <el-button
              size="mini"
              type="success"
              v-else
              @click="handleUnfreeze(row)"
            >启用</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </el-container>
</template>

<script>
  import { list,del,getCategory,updateTag,addTag} from '@/api/cms'
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
          cmsId:null
        },
        tabledata: [],
        loading: true,
        dialogTagVisible:false,
        innerVisible:false,
        taglist:null,
        tagform:{
          cateId:null,
          cateName:null,
          cateSort:0,
          cateState:0
        },
      }
    },
    mounted() {},
    methods: {
      async handleLabel(){
        const res  = await getCategory(1)
        const items = res.data
        this.taglist = items.map(v => {
          this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
          v.originalCateName = v.cateName //  will be used when user click the cancel botton
          v.originalCateSort = v.cateSort
          return v
        })
        this.dialogTagVisible = true
      },
      handleEdit(res){
        this.sendParams(res.cmsId);
      },
      handleDel(res){
        this.$confirm('确定删除该资讯, 是否继续?', '提示', {
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
        list(page).then(res => {
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
      cancelEdit(row) {
        row.cateName = row.originalCateName
        row.cateSort = row.originalCateSort
        row.edit = false
        this.$message({
          message: '取消编辑',
          type: 'warning'
        })
      },
      handleFreeze(res){
        this.$confirm('确定禁用该标签, 是否继续?', '提示', {
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
        this.$confirm('启用该标签, 是否继续?', '提示', {
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
      confirmEdit(row) {
        row.edit = false
        this.tagform.cateId = row.cateId
        this.tagform.cateName = row.cateName
        this.tagform.cateSort = row.cateSort
        this.tagform.cateState = null
        updateTag(this.tagform,0).then(res => {
          this.$message({
            type: 'success',
            message: '更新成功'
          });
          this.tagform.cateId = null;
          this.tagform.cateName = null;
          this.tagform.cateSort = 0;
          this.handleLabel();
        })
      },
      handleAddTag(){
        this.innerVisible = true
      },
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
      sendParams (res) {
        this.$router.push({
          name: 'cmsedit',
          query: {
            cmsid: res
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
<style scoped>
  .edit-tag {
    padding-right: 50px;
    width: 120px;
  }
  .cancel-tag {
    position: absolute;
    right: 15px;
    top: 12px;
  }
</style>
