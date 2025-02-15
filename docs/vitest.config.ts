import { fileURLToPath } from 'node:url'
import { defineVitestConfig } from '@nuxt/test-utils/config'

export default defineVitestConfig({
  test: {
    environment: 'nuxt',
    dir: fileURLToPath(new URL('./test/unit', import.meta.url)),

    coverage: {
      provider: 'v8',
      reportOnFailure: true
    }
  }
})
