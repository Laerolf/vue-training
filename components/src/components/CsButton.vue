<template>
    <button type="button" class="cs-button">
        <slot />
    </button>
</template>

<style scoped>
.cs-button {
    border: var(--border-normal);
    border-radius: var(--border-radius-normal);
    padding: var(--padding-normal);
    cursor: pointer;
}
</style>