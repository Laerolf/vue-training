<script setup lang="ts">
import {computed, type ComputedRef} from 'vue'

const props = withDefaults(defineProps<{
    /**
     * If true, disables the border radius, making the component edges fully square.
     */
    square: boolean
}>(), {
    
})

const classes: ComputedRef<Record<string, boolean>> = computed(() => ({
    square: props.square
}))
</script>

<template>
    <button role="button" class="cs-button" :class="classes">
        <slot />
    </button>
</template>

<style scoped>
.cs-button {
    padding: var(--padding-vertical-small) var(--padding-horizontal-large);
    cursor: pointer;
    background-color: var(--color-light);
    color: var(--color-dark);
    border: var(--border-normal);
    border-radius: var(--border-radius-normal);

    &.square {
        border-radius: 0;
    }
}
</style>