import { dirname, resolve } from 'node:path'
import { fileURLToPath } from 'node:url'
import { defineConfig } from 'vite'

import vue from '@vitejs/plugin-vue'
import cssInjectedByJsPlugin from 'vite-plugin-css-injected-by-js'
import tsconfigPaths from "vite-tsconfig-paths"
import dts from 'vite-plugin-dts'

const baseFolderPath = fileURLToPath(import.meta.url);
const __dirname = dirname(baseFolderPath)

// From https://vite.dev/guide/build.html#library-mode
export default defineConfig({
  plugins: [vue(), cssInjectedByJsPlugin(), tsconfigPaths(), dts({
    tsconfigPath: "./tsconfig.build.json"
  })],
  build: {
    lib: {
      entry: {
        'company-space': resolve(__dirname, 'src/index.ts'),
        components: resolve(__dirname, 'src/components/index.ts')
      },
      name: 'company-space',
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
        "src"
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