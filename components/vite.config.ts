import { dirname, resolve } from 'node:path'
import { fileURLToPath } from 'node:url'
import { defineConfig } from 'vite'

import vue from '@vitejs/plugin-vue'
import cssInjectedByJsPlugin from 'vite-plugin-css-injected-by-js'
import tsconfigPaths from "vite-tsconfig-paths"

const baseFolderPath = fileURLToPath(import.meta.url);
const __dirname = dirname(baseFolderPath)

// From https://vite.dev/guide/build.html#library-mode
export default defineConfig({
  plugins: [vue(), cssInjectedByJsPlugin(), tsconfigPaths()],
  build: {
    lib: {
      entry: resolve(__dirname, 'lib/main.ts'),
      name: 'CompanyComponents',
      fileName: 'company-components'
    },
    rollupOptions: {
      // make sure to externalize deps that shouldn't be bundled
      // into your library
      external: ['vue'],
      output: {
        // Provide global variables to use in the UMD build
        // for externalized deps
        globals: {
          vue: 'Vue',
        },
      },
    },
  },
  test: {
    include: ['test/**\/*.{test,spec}.?(c|m)[jt]s?(x)'],
    environment: "happy-dom",
    coverage: {
      include: [
        "lib/components/**/*.vue"
      ],
      reportOnFailure: true,
      watermarks: {
        statements: [80, 100],
        branches: [80, 100],
        functions: [80, 100],
        lines: [80, 100]
      }
    }
  }
})