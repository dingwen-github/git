<template>
  <div v-loading.fullscreen.lock="true"/>
</template>

<script>
export default {
  name: 'Cas',
  props: { token: { type: String, default: '' }}
}
</script>

<style scoped>

</style>
