<template>
  <div class="index-container">
      <img style="width: 150px;
      height: 150px;border-radius:50%;"
           src="https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif?imageView2/1/w/150/h/150">
      <h3>{{ nickname }}欢迎您！</h3>
    <panel-group />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PanelGroup from '@/components/PanelGroup'

export default {
  name: 'index',
  components: {
    PanelGroup,
  },
  computed: {
    ...mapGetters([
      'nickname'
    ])
  }
}
</script>

<style scoped>
.index-text{
  vertical-align: middle;
}
</style>
