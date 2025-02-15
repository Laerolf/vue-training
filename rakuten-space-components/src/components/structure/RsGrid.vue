<script setup lang="ts">
import {computed} from "vue";

type Props = {
  /**
   * Stack the content items on top of each other.
   */
  rows?: boolean
}

const props = withDefaults(defineProps<Props>(), {})

const classes = computed(() => ({
  rows: props.rows,
  columns: !props.rows
}))
</script>

<template>
  <div class="rs-grid" :class="classes">
    <slot/>
  </div>
</template>

<style scoped>
.rs-grid {
  display: grid;

  &.columns {
    grid-auto-flow: column;
  }

  &.rows {
    grid-auto-flow: row;
  }
}
</style>
