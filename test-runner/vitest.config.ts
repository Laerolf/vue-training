/// <reference types="vitest" />
import { defineConfig } from 'vitest/config'
import tsconfigPaths from 'vite-tsconfig-paths'

export default defineConfig({
  plugins: [tsconfigPaths()],
  test: {
    include: ['test\/**\/*.{test,spec}.?(c|m)[jt]s?(x)'],
    passWithNoTests: true,
    reporters: 'verbose',

    coverage: {
      reportOnFailure: true,
      provider: 'v8'
    }
  }
})