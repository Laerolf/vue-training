<script setup lang="ts">
const { data: briefing } = await useAsyncData('briefing', () =>
  queryCollection('content').where('stem', '=', 'briefing').first()
)
</script>

<template>
  <rs-grid rows>
    <content-renderer
      v-if="briefing"
      tag="article"
      :value="briefing"
    />

    <nuxt-link
      :to="{ name: 'missions' }"
    >
      Go to missions!
    </nuxt-link>
  </rs-grid>
</template>
