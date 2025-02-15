<script setup lang="ts">
import RsBaseInput from "@/components/input/RsBaseInput.vue";

type Props = {
  stacked?: boolean;
  label: string;
};

const value = defineModel<string>();

withDefaults(defineProps<Props>(), {
  label: undefined,
});
</script>

<template>
  <rs-base-input :stacked="stacked" :label="label">
    <input v-bind="$attrs" v-model="value" class="input-field" />
  </rs-base-input>
</template>

<style scoped>
.input-field {
  border: 0.1rem solid black;
  padding: 0.25rem 0.5rem;

  .stacked & {
    border: none;
    padding: 0;
  }
}
</style>
