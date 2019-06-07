<template>
  <div class="index-container">
      <img style="width: 150px;
      height: 150px;border-radius:50%;"
           src="http://psnskyixd.bkt.clouddn.com/weipro.jpg">
      <h3>{{ nickname }}欢迎您！</h3>
    <panel-group />
    <div class="chart-container">
      <chart height="100%" width="100%" />
    </div>
  </div>

</template>

<script>
import { mapGetters } from 'vuex'
import PanelGroup from '@/components/PanelGroup'
import Chart from '@/components/Charts/MixChart'

export default {
  name: 'index',
  components: {
    PanelGroup,
    Chart
  },
  computed: {
    ...mapGetters([
      'nickname'
    ])
  }
}
</script>

<style scoped>
.chart-container{
  position: relative;
  width: 100%;
  height: calc(100vh - 84px);
}
</style>
