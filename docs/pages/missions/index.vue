<script setup lang="ts">
import type { ContentNavigationItem } from '@nuxt/content'

const { data: missionCollection } = await useAsyncData('navigation', () => {
  return queryCollectionNavigation('missions', ['id', 'title', 'description', 'description'])
})

const computedMissions = computed<ContentNavigationItem[]>(() => missionCollection.value?.flatMap(({ children }) => children || []) ?? [])
</script>

<template>
  <rs-grid rows>
    <h1>Missions</h1>

    <rs-grid template="repeat(4, 1fr)">
      <rs-card
        v-for="mission in computedMissions"
        :key="`mission-${mission.id}`"
        class="mission"
        :title="mission.title"
        level="h2"
        :to="mission.path"
      >
        {{ mission.description }}
      </rs-card>
    </rs-grid>
  </rs-grid>
</template>

<style scoped>
.mission {
  border: 0.1rem solid #747474;
  border-radius: var(--border-radius-normal);
}
</style>
