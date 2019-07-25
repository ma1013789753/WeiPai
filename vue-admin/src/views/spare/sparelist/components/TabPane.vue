<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入用户名称" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="4">
          <el-select v-model="page.search2" placeholder="请选择审核状态">
            <el-option label="分享状态" value=""></el-option>
            <el-option label="进行中" value="0"></el-option>
            <el-option label="审核中" value="1"></el-option>
            <el-option label="已结束" value="3"></el-option>
            <el-option label="已取消" value="4"></el-option>
          </el-select>
        </el-col>
        <el-col :span="10">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
          <el-button
            style="margin-left: 10px"
            type="primary" icon="el-icon-star-on"
            @click="handleLabel">互推标签</el-button>
          </el-col>
        <el-col :span="6" style="text-align: right">
          <!-- <router-link class="inlineBlock" to="/spare/spareList/add">
            <el-button type="primary" icon="el-icon-circle-plus-outline">新增</el-button>
          </router-link> -->
        </el-col>
      </el-row>
    </el-header>
    <el-main>
  <el-table :data="tabledata" border fit highlight-current-row
            v-loading="loading"
            style="width: 100%"
            stripe
            height="320px"
            element-loading-text="拼命加载中"
            empty-text="暂无数据">
    <el-table-column  prop="shareId" label="编号" sortable width="80px"></el-table-column>
    <el-table-column  prop="userName" label="用户名称" sortable width="120px" show-overflow-tooltip></el-table-column>
    <el-table-column prop="shareType" label="分享类型" show-overflow-tooltip :formatter="getIsFrom"></el-table-column>
    <el-table-column prop="shareNum" label="分享次数" show-overflow-tooltip ></el-table-column>
    <el-table-column v-if="this.type != 2" prop="haveSharedNum" label="已分享" show-overflow-tooltip></el-table-column>
    <el-table-column v-if="this.type == 1" prop="shareCoin" label="现金总额">
      <template slot-scope="scope">
        <span> {{scope.row.shareNum*scope.row.coinMax | formatDecimal}}</span>
      </template>
    </el-table-column>
    <el-table-column v-if="this.type == 0" prop="totalCoin" label="积分总额">
      <template slot-scope="scope">
        <span> {{scope.row.totalCoin | formatDecimal}}</span>
      </template>
    </el-table-column>
    <el-table-column v-if="this.type != 2" prop="costNum" label="已消耗" >
      <template slot-scope="scope">
        <span> {{scope.row.costNum | formatDecimal}}</span>
      </template>
    </el-table-column>
    <el-table-column  prop="shareState" label="互推状态" :formatter="getIsPass"></el-table-column>
<!--    <el-table-column  prop="isOriginal" label="是否原创">-->
<!--      <template slot-scope="scope">-->
<!--        <el-tag v-if="scope.row.isOriginal == 0" type="danger">{{getIsOk(0,0,scope.row.isOriginal)}}</el-tag>-->
<!--        <el-tag v-else > {{getIsOk(0,0,scope.row.isOriginal)}}</el-tag>-->
<!--      </template>-->
<!--    </el-table-column>-->
    <el-table-column  prop="shareRecommend" label="是否推荐" :formatter="getIsOk"></el-table-column>
    <el-table-column  prop="tagName" label="标签" ></el-table-column>
    <el-table-column  prop="coinMax" label="最高奖励">
      <template slot-scope="scope">
        <span> {{scope.row.coinMax | formatDecimal}}</span>
      </template>
    </el-table-column>
    <el-table-column  prop="coinMin" label="最低奖励">
      <template slot-scope="scope">
        <span> {{scope.row.coinMin | formatDecimal}}</span>
      </template>
    </el-table-column>
    <el-table-column prop="addTime" label="创建时间" width="150px" :formatter="getTime"> </el-table-column>
    <el-table-column label="操作" width="300" fixed="right" align="center">
      <template slot-scope="scope">
        <el-button
          size="mini"
          type="success"
          v-if="scope.row.shareState != 1 "
          @click="handleShow(scope.row)"
        >查看</el-button>
<!--        <el-button-->
<!--          size="mini"-->
<!--          type="primary"-->
<!--          v-if="scope.row.shareState != 1 "-->
<!--          @click="handleEdit(scope.row)"-->
<!--        >编辑</el-button>-->
        <el-button
          size="mini"
          type="warning"
          v-if="scope.row.shareRecommend == 0 && scope.row.shareState != 1"
          @click="handleDel(scope.row)"
        >推荐</el-button>
        <el-button
          size="mini"
          v-if="scope.row.shareState == 1"
          type="primary"
          @click="handleApprove(scope.row,0)"
        >审核通过</el-button>

        <el-button
          size="mini"
          v-if="scope.row.shareState == 0"
          type="danger"
          @click="handleApprove(scope.row,4)"
        >取消</el-button>

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

    <el-dialog title="标签列表" :visible.sync="dialogTagVisible" width="40%">

      <el-dialog
        width="25%"
        title="新增标签"
        :visible.sync="innerVisible"
        append-to-body>
        <el-form ref="tagform"  label-width="100px">
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
              <el-input v-model="row.tagName" size="small" class="edit-tag"/>
            </template>
            <span v-else>{{row.tagName}}</span>
          </template>

        </el-table-column>

        <el-table-column width="150px" label="排序" prop="tagSort">

          <template slot-scope="{row}">
            <template v-if="row.edit">
              <el-input v-model="row.tagSort" size="small" class="edit-tag"/><el-button class="cancel-tag" circle icon="el-icon-refresh" type="warning" @click="cancelEdit(row)"></el-button>
            </template>
            <span v-else>{{row.tagSort}}</span>
          </template>

        </el-table-column>

        <el-table-column label="操作" width="130" align="center">
          <template slot-scope="{row}">
            <el-button v-if="row.edit" type="primary" icon="el-icon-check" circle @click="confirmEdit(row)"></el-button>
            <el-button v-else type="success" icon="el-icon-edit" circle @click="row.edit=!row.edit"></el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-dialog>
  </el-container>

</template>

<script>
  import {isOk,isPass,isFrom,getRealTime} from '@/utils/common'
  import { list,del,tuijian,getShareTag,updateTag,addTag,approve} from '@/api/spare'
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
        loading: true,
        dialogTagVisible:false,
        innerVisible:false,
        taglist:null,
        tagform:{
          tagId:null,
          tagName:null,
          tagSort:0
        },
      }
    },
    mounted() {},
    methods: {
      async handleLabel(){
        const res  = await getShareTag()
        const items = res.data
        this.taglist = items.map(v => {
          this.$set(v, 'edit', false) // https://vuejs.org/v2/guide/reactivity.html
          v.originalTagName = v.tagName //  will be used when user click the cancel botton
          v.originalTagSort = v.tagSort
          return v
        })
        this.dialogTagVisible = true
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
      handleShow(res){
        this.sendParams(res.shareId);
      },
      getIsOk(row, column, cellValue){
        return isOk(row, column, cellValue)
      },
      getIsPass(row, column, cellValue){
        return isPass(row, column, cellValue)
      },
      getIsFrom(row, column, cellValue){
        return isFrom(row, column, cellValue)
      },
      getTime(row, column, cellValue){
        return getRealTime(row, column, cellValue)
      },
      handleDel(res){
        this.$confirm('推荐该分享?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.itemTuijian(res.shareId)

        }).catch(() => {
        });
      },

      handleApprove(res,type){
        var self = this
        this.$confirm((type==0?'审核通过':'取消')+'该分享?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          var data = res
          data.shareId = res.shareId
          data.shareState = type
          self.itemApprove(data)

        }).catch(() => {
        });
      },

      //删除之后通知分页器刷新
      itemDelete(val){
        this.item.shareId = val
        del(this.item).then(res => {
          this.$message({
            type: 'success',
            message: '删除成功'
          });
          this.onSearch();
        })
      },
      itemTuijian(val){
        this.item.shareId = val
        tuijian(this.item).then(res => {
          this.$message({
            type: 'success',
            message: '推荐成功'
          });
          this.onSearch();
        })
      },

      itemApprove(val){
        approve(val).then(res => {
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
        list(page,this.type).then(res => {
          this.tabledata = res.data.records
          this.page.pages = res.data.pages
          this.page.total = res.data.total
          this.loading = false
        })
      },
      //搜索时调用分页器的功能
      onSearch(){
        this.$refs.pagination.parentHandleclick(this.page);
      },
      sendParams (res) {
        this.$router.push({
          name: 'showdetail',
          query: {
            id: res
          }
        })
      }
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
