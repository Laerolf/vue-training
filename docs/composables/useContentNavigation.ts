import type { ContentNavigationItem, PageCollections } from '@nuxt/content'

export default async function useContentNavigation(collection: keyof PageCollections, path: string) {
  const { data: navigation } = await useAsyncData('selectedSpecNavigation', () =>
    queryCollectionItemSurroundings(collection, path)
  )

  const previousItem = computed<ContentNavigationItem | null>(() => (navigation.value || []).length > 1 ? (navigation.value || [])[0] : null)
  const nextItem = computed<ContentNavigationItem | null>(() => (navigation.value || []).length > 1 ? (navigation.value || [])[1] : null)

  return {
    previousItem,
    nextItem
  }
}
