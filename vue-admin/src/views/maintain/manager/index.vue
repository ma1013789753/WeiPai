<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入任务名称" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="4">
          <el-select v-model="page.search2" placeholder="请选择任务状态">
            <el-option label="任务状态" value=""></el-option>
            <el-option label="进行中" value="1"></el-option>
            <el-option label="已完成" value="2"></el-option>
            <el-option label="已取消" value="3"></el-option>
          </el-select>
        </el-col>
        <el-col :span="5">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
        <el-col :span="11" style="text-align:right">
            <router-link class="inlineBlock" to="/maintain/manager/add">
              <el-button type="primary">新增</el-button>
            </router-link>
        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata"  empty-text="暂无数据" stripe height="400px" @current-change="handleCurrentChange">
        <el-table-column   type="index" label="编号" sortable width="60px">></el-table-column>
        <el-table-column  prop="name" label="任务名称" sortable width="200px" show-overflow-tooltip></el-table-column>
        <el-table-column prop="award" label="任务奖励" show-overflow-tooltip></el-table-column>
        <el-table-column prop="content" label="任务描述" width="240px"  show-overflow-tooltip></el-table-column>
        <el-table-column prop="link" label="任务附件" show-overflow-tooltip ></el-table-column>
        <el-table-column  prop="state" label="任务状态" :formatter="getIsPass">
        </el-table-column>
        <el-table-column prop="endTime" label="截止日期" width="150px" > </el-table-column>
        <el-table-column prop="createTime" label="创建时间" width="150px" > </el-table-column>
        <el-table-column label="操作" width="150" fixed="right" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.row)"
              >编辑</el-button>
            <!-- <el-button
              size="mini"
              type="danger"
              @click="handleDel(scope.row)"
              >推荐</el-button> -->
          </template>
        </el-table-column>
      </el-table>
    </el-main>
    <el-footer height="40">
        <pagination ref="pagination" :total="page.total" @pageChange="getList"></pagination>
    </el-footer>
  </el-container>
</template>

<script>
import {isOk,isProcess} from '@/utils/common'
import { list} from '@/api/task'
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
          total:0
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
      if(1 == cellValue){
        return '进行中'
      }
      if(2 == cellValue){
        return '已完成'
      }
      return cellValue
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
        this.page.total = res.data.total
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
