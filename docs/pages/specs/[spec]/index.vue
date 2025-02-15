<script setup lang="ts">
import type { ContentNavigationItem } from '@nuxt/content'

definePageMeta({
  layout: 'content'
})

const route = useRoute()

const { data: specsCollection } = await useAsyncData('navigation', () => {
  return queryCollectionNavigation('specs', ['id', 'title', 'description'])
})

const computedSpecs = computed<ContentNavigationItem[]>(() => specsCollection.value?.flatMap(({ children }) => (children || []).flatMap(({ path, children }: ContentNavigationItem) => {
  if (path !== route.path) {
    return []
  }

  return children || []
})) ?? [])
</script>

<template>
  <rs-grid
    rows
    template="repeat(2, max-content)"
    align-content="space-between"
  >
    <rs-grid template="repeat(4, 1fr)">
      <rs-card
        v-for="spec in computedSpecs"
        :key="`spec-${spec.id}`"
        class="spec"
        :title="spec.title"
        level="h2"
        :to="spec.path"
      >
        {{ spec.description }}
      </rs-card>
    </rs-grid>
  </rs-grid>
</template>
