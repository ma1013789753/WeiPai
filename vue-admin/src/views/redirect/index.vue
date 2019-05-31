<script>
export default {
  beforeCreate() {
    const { params, query } = this.$route
    const { paths } = params
    this.$router.replace({ path: '/auth-redirect' , query: query })
  },
  render: function(h) {
    return h() // avoid warning message
  }
}
</script>
