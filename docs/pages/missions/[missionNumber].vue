<script setup lang="ts">
import type { ContentNavigationItem } from '@nuxt/content'

definePageMeta({
  layout: 'content'
})

const { params } = useRoute()

const { data: selectedMission } = await useAsyncData('selectedMission', () =>
  queryCollection('missions')
    .where('path', '=', `/missions/${params.missionNumber}`)
    .first()
)

const { data: navigation } = await useAsyncData('selectedMissionNavigation', () =>
  queryCollectionItemSurroundings('missions', `/missions/${params.missionNumber}`)
)

const nextMission = computed<ContentNavigationItem | null>(() => (navigation.value || []).length > 1 ? (navigation.value || [])[1] : null)
</script>

<template>
  <rs-grid
    rows
    template="repeat(2, max-content)"
    align-content="space-between"
  >
    <content-renderer
      v-if="selectedMission"
      tag="article"
      :value="selectedMission"
    />

    <rs-grid
      v-if="nextMission"
      justify-content="center"
    >
      <nuxt-link :to="nextMission.path">
        Go to next mission
      </nuxt-link>
    </rs-grid>
  </rs-grid>
</template>
