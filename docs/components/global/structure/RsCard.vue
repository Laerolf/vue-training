<script setup lang="ts">
import type { RouteLocationRaw } from '#vue-router'

export type TitleLevel = 'h1' | 'h2' | 'h3' | 'h4' | 'h5' | 'h6'

type Props = {
  title?: string
  level?: TitleLevel
  to?: RouteLocationRaw
}

const props = withDefaults(defineProps<Props>(), {
  title: undefined,
  level: 'h1',
  to: undefined
})

const classes = computed(() => ({
  clickable: props.to
}))

const headerComponent = computed(() => props.level)

async function handleNavigation() {
  if (props.to) {
    await navigateTo(props.to)
  }
}
</script>

<template>
  <rs-grid
    class="rs-card"
    :class="classes"
    rows
    @click="handleNavigation"
  >
    <rs-grid v-if="title">
      <slot name="header">
        <component :is="headerComponent">
          {{ title }}
        </component>
      </slot>
    </rs-grid>

    <rs-grid rows>
      <slot />
    </rs-grid>
  </rs-grid>
</template>

<style scoped>
.rs-card {
  border-radius: var(--border-radius-normal);
  padding: 0.5rem;

  &.clickable {
  cursor: pointer;
  }
}
</style>
