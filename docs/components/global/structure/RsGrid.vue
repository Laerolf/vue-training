<script setup lang="ts">
import { computed } from 'vue'

import type { CSSProperties } from 'vue'

type Props = {
  /**
   * Stack the content items on top of each other.
   */
  rows?: boolean
  rowGap?: CSSProperties['rowGap']
  columnGap?: CSSProperties['columnGap']
  alignItems?: CSSProperties['alignItems']
  justifyContent?: CSSProperties['justifyContent']
  alignContent?: CSSProperties['alignContent']
  template?: CSSProperties['gridTemplateColumns'] | CSSProperties['gridTemplateRows']
}

const props = withDefaults(defineProps<Props>(), {
  rowGap: '1rem',
  columnGap: '1rem',
  alignItems: 'baseline',
  justifyContent: 'normal',
  alignContent: 'normal',
  template: undefined
})

const classes = computed<Record<string, boolean>>(() => ({
  rows: props.rows && !props.template,
  columns: !props.rows && !props.template
}))

const styling = computed<CSSProperties>(() => ({
  rowGap: props.rowGap,
  columnGap: props.columnGap,
  alignItems: props.alignItems,
  justifyContent: props.justifyContent,
  alignContent: props.alignContent,
  gridTemplateColumns: !props.rows ? props.template : undefined,
  gridTemplateRows: props.rows ? props.template : undefined
}))
</script>

<template>
  <div
    class="rs-grid"
    :class="classes"
    :style="styling"
  >
    <slot />
  </div>
</template>

<style scoped>
.rs-grid {
  display: grid;
  grid-auto-columns: auto;

  &.columns {
    grid-auto-flow: column;
  }

  &.rows {
    grid-auto-flow: row;
  }
}
</style>
