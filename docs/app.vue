<script setup lang="ts">
const head = useLocaleHead()
</script>

<template>
  <Html
    :lang="head?.htmlAttrs?.lang"
    :dir="head?.htmlAttrs?.dir"
  >
    <nuxt-layout />
  </Html>
</template>
