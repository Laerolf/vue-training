import type { Meta, StoryObj } from "@storybook/vue3-vite";

import CsInputField from "@components/input/CsInputField.vue";

const meta = {
    component: CsInputField,
    tags: ['autodocs'],
    render: (args: any) => ({
        components: { CsInputField },
        setup() {
            return { args }
        },
        template: '<cs-input-field id="input-field" v-model="args.modelValue" :label="args.label" :stacked="args.stacked" />'
    }),
    argTypes: {
        stacked: { control: "boolean" },
        label: {control: "text"}
    },
    args: {
        modelValue: "Value",
        label: ""
    }
} satisfies Meta<typeof CsInputField>

export default meta;
type Story = StoryObj<typeof meta>

export const Default: Story = {
    args: {
        stacked: false
    }
}

export const Stacked: Story = {
    argTypes: {
        stacked: { table: { readonly: true } }
    },
    args: {
        label: "First name",
        modelValue: "Henry",
        stacked: true
    }
}