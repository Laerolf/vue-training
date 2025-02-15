// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({

  modules: [
    '@nuxt/content',
    '@nuxt/test-utils/module',
    '@nuxtjs/i18n',
    '@nuxt/eslint'
  ],

  components: [
    {
      global: true,
      path: '@/components',
      pathPrefix: false
    }
  ],
  devtools: { enabled: true },

  css: ['@/assets/styling/_main.css'],
  compatibilityDate: '2024-11-01',

  eslint: {
    config: {
      stylistic: {
        commaDangle: 'never'
      }
    }
  },

  i18n: {
    baseUrl: 'localhost',
    defaultLocale: 'en',
    strategy: 'no_prefix',
    locales: [
      {
        language: 'en',
        code: 'en',
        isCatchallLocale: true
      }
    ]
  }
})
