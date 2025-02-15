import type { StorybookConfig } from "@storybook/vue3-vite";

export default {
  stories: ["../src/**/*.mdx", "../src/**/*.stories.@(js|jsx|mjs|ts|tsx)"],
  addons: [
    "@storybook/addon-onboarding",
    "@storybook/addon-links",
    "@storybook/addon-essentials",
    "@chromatic-com/storybook",
    "@storybook/addon-interactions",
      "@whitespace/storybook-addon-html"
  ],
  framework: {
    name: "@storybook/vue3-vite",
    options: {},
  }
} satisfies StorybookConfig

