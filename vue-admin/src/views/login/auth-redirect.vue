<script>
export default {
  name: 'AuthRedirect',
  created() {
    const hash = window.location.hash.slice(1)
    if (window.localStorage) {
      window.localStorage.setItem('x-admin-oauth-code', hash)
      window.close()
    }
  },
  render: function(h) {
    return h() // avoid warning message
  }
}
</script>
