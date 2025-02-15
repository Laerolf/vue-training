import type { Meta, StoryObj } from '@storybook/vue3';

import {components as RsComponents} from 'components'

const RsGrid = RsComponents.RsGrid

const meta = {
  component: RsGrid,
  render: (args) => ({
    components: { RsGrid },
    setup() {
      return { args }
    },
    template: `
    <rs-grid v-bind="args">
      <span>Hello</span>
      <span>world!</span>
    </rs-grid>
    `
  })
} satisfies Meta<typeof RsGrid>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  args: {
  },
};
