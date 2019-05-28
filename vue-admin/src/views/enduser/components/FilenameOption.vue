<template>
  <div style="display:inline-block; margin-left: 80px">
    <!-- $t is vue-i18n global function to translate lang -->
    <label class="radio-label" style="padding-left:0;">文件名: </label>
    <el-input v-model="filename" placeholder="请输入文件名(默认excel-list)" style="width:260px;" prefix-icon="el-icon-document" />
  </div>
</template>

<script>
export default {
  props: {
    value: {
      type: String,
      default: ''
    }
  },
  computed: {
    filename: {
      get() {
        return this.value
      },
      set(val) {
        this.$emit('input', val)
      }
    }
  }
}
</script>
