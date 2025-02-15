import { defineCollection } from '@nuxt/content'

export const collections = {
  content: defineCollection({
    type: 'page',
    source: '*.md'
  }),
  missions: defineCollection({
    type: 'page',
    source: 'missions/*.md'
  }),
  specs: defineCollection({
    type: 'page',
    source: 'specs/**/*.md'
  })
}
