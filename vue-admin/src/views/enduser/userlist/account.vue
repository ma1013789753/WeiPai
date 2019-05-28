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
    name: 'Tab',
    components: { tabPane },
    data() {
      return {
        tabMapOptions: [
          { label: '微博', key: 0 },
          { label: '头条', key: 3 },
          { label: '微信', key: 2 },
          { label: '公众号', key: 1 }
        ],
        activeName: 0,
        createdTimes: 0
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
