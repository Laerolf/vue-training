<script setup lang="ts">
import type { ContentNavigationItem, PageCollections } from '@nuxt/content'

type Props = {
  type: keyof PageCollections
}

const props = withDefaults(defineProps<Props>(), {})

const { data: contentCollection } = await useAsyncData('navigation', () => {
  return queryCollectionNavigation(props.type, ['id', 'path', 'description', 'title'])
})

const computedContent = computed<ContentNavigationItem[]>(() => contentCollection.value?.flatMap(({ children }) => children || []) ?? [])
const computedContentChildren = computed<ContentNavigationItem[]>(() => computedContent.value.flatMap(({ children }) => children || []) ?? [])
</script>

<template>
  <rs-grid
    class="navigation-container"
    rows
  >
    <rs-grid
      class="navigation-section"
      rows
      row-gap="0"
    >
      <slot name="header" />

      <rs-list :items="computedContent" :children="computedContentChildren">
        <template #default="{ item: { path, title } }">
          <nuxt-link :to="path">
            {{ title }}
          </nuxt-link>
        </template>
      </rs-list>
    </rs-grid>
  </rs-grid>
</template>

<style scoped>
.navigation-container {
  border-right: var(--border-accent);
}

.navigation-section {
  padding: var(--padding-normal);
  padding-top: 0.5rem;

  .navigation-section-title {
    font-size: large;
  }
}
</style>
