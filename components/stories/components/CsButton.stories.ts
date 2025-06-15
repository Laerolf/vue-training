import type { ComponentPropsAndSlots, Meta, StoryObj } from "@storybook/vue3-vite";

import CsButton from "@components/CsButton.vue";

type CsButtonStory = ComponentPropsAndSlots<typeof CsButton> & {
    /**
     * The label of the button.
     */
    label?: string
};

const meta = {
    component: CsButton,
    tags: ['autodocs'],
    render: (args: any) => ({
        components: { CsButton },
        setup() {
            return { args }
        },
        template: '<cs-button v-bind="args">{{args.label}}</cs-button>'
    }),
    args: {
        label: "Click",
        square: false
    }
} satisfies Meta<CsButtonStory>

export default meta;
type Story = StoryObj<typeof meta>

export const Default: Story = {}

export const Disabled: Story = {
    render: (args: any) => ({
        components: { CsButton },
        setup() {
            return { args }
        },
        template: '<cs-button v-bind="args" disabled>{{args.label}}</cs-button>'
    }),    
}