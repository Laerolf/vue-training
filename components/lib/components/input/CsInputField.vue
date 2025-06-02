<script setup lang="ts">
import CsInput from './CsInput.vue'

interface Props {
    /**
     * The text of the label.
     */
    label?: string

    /**
     * Show the input field with the label on top of it.
     */
    stacked?: boolean
}

const value = defineModel<string>()

defineProps<Props>()
</script>

<template>
    <cs-input :label="label" :stacked="stacked">
        <input v-bind="$attrs" class="cs-input-field" v-model="value" />
    </cs-input>
</template>

<style scoped>
.cs-input-field {
    border: var(--border-normal);
    border-radius: var(--border-radius-normal);
    padding: var(--padding-normal);
}
</style>