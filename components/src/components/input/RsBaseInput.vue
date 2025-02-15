<script setup lang="ts">
import RsGrid from "@/components/structure/RsGrid.vue";
import { computed } from "vue";

type Props = {
  stacked?: boolean;
  label: string;
};

const props = withDefaults(defineProps<Props>(), {
  label: undefined,
});

const containerComponent = computed(() =>
  props.stacked ? "fieldset" : RsGrid,
);

const labelComponent = computed(() => (props.stacked ? "legend" : "label"));

const computedClasses = computed(() => ({
  stacked: props.stacked,
}));
</script>

<template>
  <component
    :is="containerComponent"
    class="rs-base-input"
    :class="computedClasses"
  >
    <component :is="labelComponent" v-if="label" class="input-label">
      {{ label }}
    </component>

    <slot />
  </component>
</template>

<style scoped>
.rs-base-input {
  padding: 0.25rem 0.5rem 0.5rem 0.5rem;

  &.stacked {
    border: 0.1rem solid black;

    .input-label {
      padding: 0 0.25rem;
      border: none;
    }
  }
}
</style>
