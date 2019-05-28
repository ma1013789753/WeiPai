<template>
  <el-table :data="list" border fit highlight-current-row style="width: 100%">
    <el-table-column label="ID" width="65" prop="accountId"></el-table-column>
    <el-table-column width="150px" label="账号名称" prop="accountName"></el-table-column>

    <el-table-column v-if="this.type == 0" width="120px" label="微博ID" prop="uid"></el-table-column>
    <el-table-column v-else-if="this.type == 1" width="120px" label="公众号ID" prop="uid"></el-table-column>
    <el-table-column v-else-if="this.type == 2" width="140px" label="微信ID" prop="uid" ></el-table-column>
    <el-table-column v-else-if="this.type == 3" width="120px" label="头条账号ID" prop="uid"></el-table-column>

    <el-table-column width="100px" label="微博数" prop="statuses_count"></el-table-column>


    <el-table-column width="100px" label="粉丝数" prop="follow_num"></el-table-column>
    <el-table-column width="110px"  label="状态">
      <template slot-scope="scope">
        <el-tag v-if="scope.row.accountState == 1"> {{scope.row.accountState | accStatusFilter}}</el-tag>
        <el-tag v-else type="danger"> {{scope.row.accountState | accStatusFilter}}</el-tag>
      </template>
    </el-table-column>
    <el-table-column width="120px" label="禁用原因" prop="disableReason"></el-table-column>
    <el-table-column label="申请时间" width="195" prop="addTime">
      <template slot-scope="scope">
        <span> {{scope.row.addTime | formatDate}}</span>
      </template>
    </el-table-column>
    <el-table-column label="操作" width="90" align="center">
      <template slot-scope="scope">
        <el-button v-if="scope.row.accountState == 1" size="mini" type="warning" @click="handleFreeze(scope.row)">禁用</el-button>
        <el-button v-else-if="scope.row.accountState == -1" size="mini" type="success" @click="handleUnbanned(scope.row)">解禁</el-button>
      </template>
    </el-table-column>
  </el-table>
</template>

<script>
  import { getUserAccount,accunbanned} from '@/api/customer'
export default {
  props: {
    type: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      list: null,
      listQuery: {
        userId: null,
        type: this.type,
      },
      loading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    handleUnbanned(res){
      this.$confirm('确定解封该用户, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.unbanned(res.accountId)
      }).catch(() => {

      });
    },
    handleFreeze(res){
      this.sendParams(res.accountId,res.accountName);
    },
    sendParams (id,name) {
      this.$router.push({
        name: 'accfreeze',
        query: {
          accid: id,
          accname:name
        }
      })
    },
    getList() {
      this.listQuery.userId = this.$route.query.uid
      this.loading = true
      this.$emit('create') // for test
      getUserAccount(this.listQuery.userId,this.listQuery.type).then(response => {
        this.list = response.data
        this.loading = false
      })
    },
    //封禁
    unbanned(val){
      accunbanned(val).then(res => {
        this.$message({
          type: 'success',
          message: '操作成功'
        });
        this.getList();
      })
    },
  }
}
</script>

