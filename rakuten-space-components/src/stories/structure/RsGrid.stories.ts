import RsGrid from "../../components/structure/RsGrid.vue";

import SbHighlight from "../components/SbHighlight.vue";

import type {Meta, StoryObj} from "@storybook/vue3";

const meta = {
 component: RsGrid,
    tags: ['autodocs'],
    args: {
     rows: false
    },
    argTypes: {
        rows: {control: "boolean"},
        default: {control:false}
    },
    render: (args) => ({
        components: {
            SbHighlight,
            RsGrid
        },
        setup() {
            return {args}
        },
        template: `
          <sb-highlight>
            <rs-grid v-bind="args">
              <p>Hello</p>
              <p>world</p>
            </rs-grid>
          </sb-highlight>
        `
    })
} satisfies Meta<typeof RsGrid>

type Story = StoryObj<typeof meta>

export const Default: Story = {
}

export const Columns: Story = {
    args: {
        rows: false
    },
    argTypes: {
        rows: { control: false }
    }
}

export const Rows: Story = {
    args: {
        rows: true
    },
    argTypes: {
        rows: { control: false }
    }
}

export default meta
