<template>
  <button class="rs-button">
    <slot />
  </button>
</template>

<style scoped>
.rs-button {
  color: black;
  padding: var(--padding-normal);
  cursor: pointer;

  &:disabled {
    color: gray;
    cursor: default;
  }
}
</style>
