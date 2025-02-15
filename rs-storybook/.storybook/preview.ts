import type { Preview} from "@storybook/vue3";

import "components/dist/components.css"

export default {
  parameters: {
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/i,
      },
    },
  },
  tags: ["autodocs"]
} satisfies Preview;
