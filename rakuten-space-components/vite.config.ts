import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],

  resolve: {
    alias: {
      '@': '/src',
    }
  },

  build: {
    lib: {
      entry: path.resolve(import.meta.url, 'src/index.ts'),
      name: 'RakutenSpaceComponents',
      fileName: 'rakuten-space-components',
    },
    rollupOptions: {
      external: ['vue'],
      output: {
        globals: {
          vue: 'Vue'
        }
      }
    }
  }
})
