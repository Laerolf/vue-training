<script setup lang="ts">
import type { RouteLocationRaw } from '#vue-router'

type ActionLink = {
  key: string
  title: string
  path: RouteLocationRaw
}

type Props = {
  inverted?: boolean
}

const props = withDefaults(defineProps<Props>(), {})

const actionLinks: ActionLink[] = [
  {
    key: 'missions',
    title: 'Missions',
    path: '/missions'
  },
  {
    key: 'specs',
    title: 'Specs',
    path: '/specs'
  }
]

const classes = computed(() => ({
  inverted: props.inverted
}))
</script>

<template>
  <rs-grid
    id="header"
    :class="classes"
    justify-content="space-between"
    align-items="center"
  >
    <nuxt-link to="/">
      <h1 class="main-title">
        Rakuten Space
      </h1>
    </nuxt-link>

    <rs-grid
      v-if="actionLinks.length"
      align-items="center"
    >
      <nuxt-link
        v-for="link in actionLinks"
        :key="`header-action-${link.key}`"
        :to="link.path"
        class="action-link"
      >
        {{ link.title }}
      </nuxt-link>
    </rs-grid>
  </rs-grid>
</template>

<style scoped>
#header {
  padding: 0.75rem 0.5rem;;
  border-bottom: var(--border-accent);
}

.main-title {
  font-weight: bold;
  letter-spacing: -0.1rem;
  font-size: 1.5rem;
  color: var(--color-primary);
}

.action-link {
  padding: var(--padding-normal);
  background-color: var(--color-secondary);
  border-radius: var(--border-radius-normal);
}
</style>
