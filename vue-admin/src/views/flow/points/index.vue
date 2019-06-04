<template>
  <div class="tab-container">
    <el-tabs v-model="activeName" style="margin-top:15px;" type="border-card">
      <el-tab-pane  :key="key1" :label="label1" :name="key1">
        <keep-alive>
          <pointsList-pane  :type="key1" />
        </keep-alive>
      </el-tab-pane>
      <el-tab-pane  :key="key2" :label="label2" :name="key2">
        <keep-alive>
          <pointsFlow-pane  :type="key2" />
        </keep-alive>
      </el-tab-pane>

    </el-tabs>
  </div>

</template>

<script>
  import pointsFlowPane from './components/PointsFlowPane'
  import pointsListPane from './components/PointsListPane'

  export default {
    name: 'pointsTab',
    components: { pointsFlowPane,pointsListPane },
    data() {
      return {
        label1: '积分充值',
        key1: 0 ,
        label2: '积分流水',
        key2: 1 ,
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
