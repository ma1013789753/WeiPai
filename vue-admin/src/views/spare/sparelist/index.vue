<template>
  <div class="tab-container">
    <el-tabs v-model="activeName" style="margin-top:15px;" type="border-card">
      <el-tab-pane v-for="item in tabMapOptions" :key="item.key" :label="item.label" :name="item.key">
        <keep-alive>
          <tab-pane v-if="activeName==item.key" :type="item.key" />
        </keep-alive>
      </el-tab-pane>
    </el-tabs>
  </div>

</template>

<script>
  import tabPane from './components/TabPane'

  export default {
    name: 'shareTab',
    components: { tabPane },
    data() {
      return {
        tabMapOptions: [
          { label: '现金互推',key: '1' },
          { label: '积分互推', key: '0' },
          { label: '原创审核', key: '2' }
        ],
        activeName: '2',
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
