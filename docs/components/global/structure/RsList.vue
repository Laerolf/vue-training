<script setup lang="ts" generic="T extends { path: string; title: string }">
type Props<T> = {
  items?: T[]
  children?: T[]
}

withDefaults(defineProps<Props<T>>(), {
  items: () => [],
  children: () => []
})

defineSlots<{
  default(props: { item: T }): never
}>()
</script>

<template>
  <ul class="rs-list">
    <li
      v-for="(item, itemIndex) in items"
      :key="`item-${itemIndex}`"
      class="rs-list-item"
    >
      <slot :item="item">
        {{ item }}
      </slot>
    </li>

    <li v-if="children.length">
      <rs-list :items="children">
        <template #default="{ item: { path, title } }">
          <nuxt-link :to="path">
            {{ title }}
          </nuxt-link>
        </template>
      </rs-list>
    </li>
  </ul>
</template>

<style scoped>
.rs-list {
  li ul {
    margin-left: 1rem;
  }
}
</style>
