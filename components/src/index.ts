import components from '@/components'

import type { Plugin } from 'vue'

export default {
  install(app) {
    Object.entries(components).forEach(([name, component]) =>
      app.component(name, component)
    )
  }
} satisfies Plugin

export { components }
