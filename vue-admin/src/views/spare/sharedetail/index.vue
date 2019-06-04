<template>
  <div class="tab-container">
    <el-tabs v-model="activeName" style="margin-top:15px;" type="border-card">
      <el-tab-pane  :key="key1" :label="label1" :name="key1">
        <keep-alive>
          <detail-pane  :type="key1" />
        </keep-alive>
      </el-tab-pane>
      <el-tab-pane  :key="key2" :label="label2" :name="key2">
        <keep-alive>
          <tab-pane  :type="key2" />
        </keep-alive>
      </el-tab-pane>
    </el-tabs>
  </div>

</template>

<script>
  import tabPane from './components/ListTabPane'
  import detailPane from './components/DetailPane'

  export default {
    name: 'shareDetailTab',
    components: { tabPane,detailPane },
    data() {
      return {
           label1: '互推详情',
        key1: '0' ,
           label2: '分享列表',
        key2: '1' ,
        activeName: '0',
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
