<script setup lang="ts">
import { computed } from 'vue'

import CsInputLabel from './CsInputLabel.vue'

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

defineOptions({
    inheritAttrs: false
})

const props = defineProps<Props>()


const classes = computed(() => ({
    stacked: props.stacked
}))


</script>

<template>
    <div class="cs-input" :class="classes">
        <cs-input-label v-if="label" :for="$attrs.id">{{ label }}</cs-input-label>
        <slot />
    </div>
</template>

<style>
.cs-input {
    .cs-input-label {
        margin-right: var(--margin-normal);
    }

    &.stacked {
        * {
            display: block;
        }

        .cs-input-label {
            padding: var(--padding-small) 0;
            margin-right: revert;
        }
    }
}
</style>