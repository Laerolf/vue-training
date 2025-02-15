import RsInputField from "../../components/input/RsInputField.vue";

import type { Meta, StoryObj } from "@storybook/vue3";

const meta = {
  component: RsInputField,
  tags: ["autodocs"],
  argTypes: {
    label: { control: "text" },
  },
  args: {
    label: "Label",
    stacked: false,
  },
} satisfies Meta<typeof RsInputField>;

type Story = StoryObj<typeof meta>;

export const Default: Story = {
  argTypes: {
    stacked: { control: false },
  },
};

export const Stacked: Story = {
  argTypes: {
    stacked: { control: false },
  },
  args: {
    stacked: true,
  },
};

export default meta;
