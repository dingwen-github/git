<template>
  <div v-loading.fullscreen.lock="true"/>
</template>

<script>
import { setToken } from '@/utils/auth' // getToken from cookie
import store from '@/store'
export default {
  name: 'Oauth2',
  props: { token: { type: String, default: '' }},
  created: function() {
    store.commit('SET_TOKEN', this.token)
    setToken(this.token)
    this.$router.push('/')
  }
}
</script>

<style scoped>

</style>
