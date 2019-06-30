<!--  -->
<template>
  <el-container>
    <el-header>
      <el-row :gutter="10">
        <el-col :span="4">
          <el-input  placeholder="请输入提现编号" maxlength="5" minlength="1" clearable v-model="page.search1"></el-input>
        </el-col>
        <el-col :span="4">
          <el-input  placeholder="请输入用户名称" maxlength="5" minlength="1" clearable v-model="page.search2"></el-input>
        </el-col>
        <el-col :span="1">
          <el-button icon="el-icon-search" type="primary" @click="onSearch">搜索</el-button>
        </el-col>
      </el-row>
    </el-header>
    <el-main >
      <el-table highlight-current-row border :data="tabledata"
                v-loading="loading"
                element-loading-text="拼命加载中"
                empty-text="暂无数据"
                stripe height="360px">
        <el-table-column prop="pdcSn" label="提现编号" sortable width="180px"></el-table-column>
        <el-table-column prop="pdcMemberId" label="用户编号" ></el-table-column>
        <el-table-column prop="pdcMemberName" label="用户昵称" show-overflow-tooltip></el-table-column>
        <el-table-column prop="pdcAmount" label="提现金额" >
          <template slot-scope="scope">
            <span> {{scope.row.pdcAmount | formatDecimal}}</span>
          </template>
        </el-table-column>
        <el-table-column prop="pdcBankNo" label="收款账号" width="140px" show-overflow-tooltip></el-table-column>
        <el-table-column  prop="pdcBankUser" label="账号姓名" show-overflow-tooltip></el-table-column>
        <el-table-column  prop="pdcPaymentState" label="审核状态" >
          <template slot-scope="scope">
            <el-tag v-if="scope.row.pdcPaymentState == 2" type="danger"> {{scope.row.userState | cashStatusFilter}}</el-tag>
            <el-tag v-else > {{scope.row.pdcPaymentState | cashStatusFilter}}</el-tag>
          </template>
        </el-table-column>
        <el-table-column  prop="pdcAddTime" label="申请时间"  width="170px" align="center">
          <template slot-scope="scope">
            <span> {{scope.row.pdcAddTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column  prop="refuseReason" label="拒绝原因" show-overflow-tooltip v-if="this.type == 2"></el-table-column>
        <el-table-column  prop="pdcPaymentTime" label="付款时间"  width="170px" align="center" v-if="this.type == 1">
          <template slot-scope="scope">
            <span> {{scope.row.pdcPaymentTime | formatDate}}</span>
          </template>
        </el-table-column>
        <el-table-column  prop="pdcPaymentAdmin" label="支付人" v-if="this.type == 1"></el-table-column>
        <el-table-column label="操作" width="160" align="center"  v-if="this.type == 0" fixed="right">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="success"
              @click="handleEdit(scope.row)"
            >通过</el-button>
            <el-button
              size="mini"
              type="warning"
              @click="handleAcc(scope.row)"
            >拒绝</el-button>
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

      <el-dialog title="拒绝理由" :visible.sync="dialogTableVisible">
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入理由"
            v-model="text">
          </el-input>
            <span slot="footer" class="dialog-footer">
              <el-button @click="dialogTableVisible = false">取 消</el-button>
              <el-button type="primary" @click="goFail">确 定</el-button>
            </span>
        </el-dialog>
  </el-container>


</template>

<script>
  import { cashList,caseApprove,caseFail} from '@/api/flow'
  import { delay } from '@/utils/index'
  import pagination from '@/components/pagination'
  export default {
    components: {
      pagination,
    },
    props: {
      type: {
        type: Number,
        default: 0
      }
    },
    data() {
      return {
        dialogTableVisible:false,
        currentRow:null,
        text:'',
        data: [],
        values:[],
        page: {
          pages: 1,
          search1:"",
          search2:"",
          total: 0
        },
        pid:'',
        item: {
          uid:null
        },
        tabledata: [],
        loading: true
      }
    },
    mounted() {},
    methods: {
      handleEdit(res){
        // alert(JSON.stringify(res))
        this.sendParams(res.pdcId);
      },
      handleAcc(res){
        this.dialogTableVisible = true
        this.pid = res.pdcId
      },
      goFail(){
          var data ={
            id:this.pid,
            mes:this.text
          }
          caseFail(data).then(res => {
            this.dialogTableVisible = false
              this.$refs.pagination.parentHandleclick(this.page);
          }).catch(() => {

          })
      },
      handleSign(res){
        this.signParams(res.pdcMemberId);
      },

      //分页器自动调用
      getList(page){
        this.loading = true
        this.currentRow = null
        cashList(page,this.type).then(res => {
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
        caseApprove(res).then(res => {
             this.$refs.pagination.parentHandleclick(this.page);
        }).catch(() => {

        })

        // this.$router.push({
        //   name: 'deposit',
        //   query: {
        //     uid: res
        //   }
        // })
      },

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
      },
    }

  }

</script>
<style lang='scss' scoped>

</style>
