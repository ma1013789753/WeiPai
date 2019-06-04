<template>
  <div class="tab-container">
    <el-tabs v-model="activeName" style="margin-top:15px;" type="border-card">
      <el-tab-pane  :key="key1" :label="label1" :name="key1">
        <keep-alive>
          <cashList-pane  :type="key1" />
        </keep-alive>
      </el-tab-pane>
      <el-tab-pane  :key="key2" :label="label2" :name="key2">
        <keep-alive>
          <cashList-pane  :type="key2" />
        </keep-alive>
      </el-tab-pane>
      <el-tab-pane  :key="key3" :label="label3" :name="key3">
        <keep-alive>
          <cashList-pane  :type="key3" />
        </keep-alive>
      </el-tab-pane>
      <el-tab-pane  :key="key4" :label="label4" :name="key4">
        <keep-alive>
          <cashFlow-pane  :type="key4" />
        </keep-alive>
      </el-tab-pane>
    </el-tabs>
  </div>

</template>

<script>
  import cashFlowPane from './components/CashFlowPane'
  import cashListPane from './components/CashListPane'

  export default {
    name: 'cashTab',
    components: { cashFlowPane,cashListPane },
    data() {
      return {
        label1: '提现申请',
        key1: 0 ,
        label2: '提现列表',
        key2: 1 ,
        label3: '拒绝记录',
        key3: 2 ,
        label4: '资金流水',
        key4: 3 ,
        activeName: 0,
      }
    },
    methods: {

    }
  }
</script>

<style scoped>
  .tab-container {
    margin: 30px;
  }
</style>
