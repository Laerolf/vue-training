<script setup lang="ts">
import TheHeader from '@/components/TheHeader.vue'
</script>

<template>
  <rs-grid
    id="layout-container"
    row-gap="0"
    rows
    align-items="stretch"
    template="max-content 1fr"
  >
    <the-header inverted />

    <rs-grid
      id="content"
      rows
    >
      <nuxt-page />
    </rs-grid>
  </rs-grid>
</template>

<style scoped>
#layout-container {
  height: 100vh;
}

#content {
  padding: var(--padding-normal);
}
</style>
