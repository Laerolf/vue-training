<script setup lang="ts">
definePageMeta({
  layout: 'content'
})

const { params } = useRoute()

const { previousItem: previousSubSpec, nextItem: nextSubSpec } = await useContentNavigation('specs', `/specs/${params.spec}/${params.subSpec}`)

const { data: selectedSubSpec } = await useAsyncData('selectedSubSpec', () =>
  queryCollection('specs')
    .where('path', '=', `/specs/${params.spec}/${params.subSpec}`)
    .first()
)
</script>

<template>
  <rs-grid
    rows
    template="repeat(2, max-content)"
    align-content="space-between"
  >
    <content-renderer
      v-if="selectedSubSpec"
      tag="article"
      :value="selectedSubSpec"
      class="content"
    />

    <rs-grid
      v-if="previousSubSpec || nextSubSpec"
      justify-content="center"
    >
      <nuxt-link
        v-if="previousSubSpec"
        :to="previousSubSpec.path"
      >
        Go to {{ previousSubSpec.title }}
      </nuxt-link>

      <nuxt-link
        v-if="nextSubSpec"
        :to="nextSubSpec.path"
      >
        Go to {{ nextSubSpec.title }}
      </nuxt-link>
    </rs-grid>
  </rs-grid>
</template>
