<template>
  <div class="index-container">
    <div class="index-text">{{ nickname }}欢迎您！</div>

    
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'index',
  computed: {
    ...mapGetters([
      'nickname'
    ])
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
.index {
  &-container {
    margin: 30px;
  }
  &-text {
    font-size: 30px;
    line-height: 46px;
  }
}
</style>
