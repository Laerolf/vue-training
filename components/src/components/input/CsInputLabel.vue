<script setup lang="ts">
defineOptions({
    name: "CsInputLabel"
})
</script>

<template>
    <label class="cs-input-label">
        <slot />
    </label>
</template>

<style scoped>
.cs-input-label {
    cursor: default;
}
</style>