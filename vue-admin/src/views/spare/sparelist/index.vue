<!--  -->
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
        <el-col :span="5">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
        <el-col :span="11" style="text-align:right">
            <router-link class="inlineBlock" to="/spare/spareList/add">
              <el-button type="primary">新增</el-button>
            </router-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata"  empty-text="暂无数据" stripe height="400px" @current-change="handleCurrentChange">
        <el-table-column  prop="shareId" label="编号" sortable width="80px"></el-table-column>
        <el-table-column  prop="userName" label="用户名称" sortable width="120px" show-overflow-tooltip></el-table-column>
        <el-table-column prop="shareType" label="分享类型" show-overflow-tooltip :formatter="getIsFrom"></el-table-column>
        <el-table-column prop="shareUrl" label="分享链接" width="140px"  show-overflow-tooltip></el-table-column>
        <el-table-column prop="shareNum" label="分享数量" show-overflow-tooltip ></el-table-column>
        <el-table-column  prop="haveSharedNum" label="已经分享" show-overflow-tooltip></el-table-column>
        <el-table-column  prop="isOriginal" label="是否原创">
          <template slot-scope="scope">
            <el-tag v-if="scope.row.isOriginal == 0">{{getIsOk(0,0,scope.row.isOriginal)}}</el-tag>
            <el-tag v-else type="danger"> {{getIsOk(0,0,scope.row.isOriginal)}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column  prop="shareRecommend" label="是否推荐" :formatter="getIsOk"></el-table-column>
        <el-table-column  prop="shareState" label="审核状态" :formatter="getIsPass"></el-table-column>
        <el-table-column  prop="shareStatus" label="奖励类型" :formatter="getIsReward"></el-table-column>
        <el-table-column prop="shareCoin" label="积分奖励" show-overflow-tooltip></el-table-column>
        <el-table-column  prop="coinMax" label="最高奖励"></el-table-column>
        <el-table-column  prop="coinMin" label="最低奖励"></el-table-column>
        <el-table-column prop="addTime" label="创建时间" width="150px" :formatter="getTime"> </el-table-column>
        <el-table-column label="操作" width="150" fixed="right" align="center"> 
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
              >推荐</el-button>
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
import {isOk,isPass,isReward,isFrom,getRealTime} from '@/utils/common'
import { list,del,tuijian} from '@/api/spare'
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
          search2:"",
          search3:"",
          search4:"",

        },
        item: {
          shareId:null
        },
        tabledata: []
      }
    },
  created() {
  },
  mounted() {},
  methods: {
    handleEdit(res){
      this.sendParams(res.id);
    },
    getIsOk(row, column, cellValue){
        return isOk(row, column, cellValue)
    },
    getIsPass(row, column, cellValue){
        return isPass(row, column, cellValue)
    },
    getIsReward(row, column, cellValue){
        return isReward(row, column, cellValue)
    },
    getIsFrom(row, column, cellValue){
        return isFrom(row, column, cellValue)
    },
    getTime(row, column, cellValue){
      return getRealTime(row, column, cellValue)
    },
    handleDel(res){
        this.$confirm('确定推荐该分享, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.itemTuijian(res.shareId)

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
          name: 'edit',
          query: {
            id: res
          }
      })
    }
  }
}

</script>
<style lang='scss' scoped>

</style>
