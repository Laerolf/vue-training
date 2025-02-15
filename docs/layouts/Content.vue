<script setup lang="ts">
import type { PageCollections } from '@nuxt/content'

import type { RouteLocationRaw } from 'vue-router'
import TheHeader from '@/components/TheHeader.vue'
import TheContentNavigation from '@/components/TheContentNavigation.vue'

const route = useRoute()

const selectedContentType = computed<keyof PageCollections | null>(() => {
  if (!route.name) {
    return null
  }

  if (route.name.toString().startsWith('missions')) {
    return 'missions'
  }
  else if (route.name.toString().startsWith('specs')) {
    return 'specs'
  }
  else {
    return null
  }
})

const headerDetails = computed<{ title?: string, path: RouteLocationRaw }>(() => ({
  title: capitalize(selectedContentType.value ?? undefined),
  path: { name: selectedContentType.value || undefined }
}))
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
      column-gap="0"
      template="1fr 4fr"
      align-items="stretch"
    >
      <the-content-navigation
        v-if="selectedContentType"
        :type="selectedContentType"
      >
        <template #header>
          <nuxt-link
            :to="headerDetails.path"
            class="navigation-section-title bold"
          >
            {{ headerDetails.title }}
          </nuxt-link>
        </template>
      </the-content-navigation>

      <rs-grid
        id="content"
        align-items="stretch"
        rows
      >
        <nuxt-page />
      </rs-grid>
    </rs-grid>
  </rs-grid>
</template>

<style scoped>
#layout-container {
  height: 100vh;
}

#content {
  padding: 1.5rem 5rem;
}
</style>
