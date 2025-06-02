import { describe, expect, suite, test } from 'vitest'
import { mount } from '@vue/test-utils'

import CsInputField from '@components/input/CsInputField.vue'
import CsInputLabel from '@components/input/CsInputLabel.vue'

suite("CsInputField", () => {

    describe("Default", () => {
        test("It renders", () => {
            // When
            const component = mount(CsInputField)

            // Then
            expect(component).not.toBeNull()
            expect(component.props("modelValue")).toBeUndefined()
            expect(component.props("stacked")).toBeFalsy()
            expect(component.classes()).toStrictEqual(["cs-input"])

            const label = component.findComponent(CsInputLabel)
            expect(label.exists()).toBeFalsy()

            const inputField = component.find("input")
            expect(inputField.exists()).toBeTruthy()
            expect(inputField.classes()).toStrictEqual(["cs-input-field"])
        })

        test("It is editable", async () => {
            // Given
            const component = mount(CsInputField, {
                props: {
                    'onUpdate:modelValue': (newValue) => component.setProps({ modelValue: newValue })
                }
            })

            // When
            await component.find("input").setValue("666")

            // Then
            expect(component.props("modelValue")).toBe("666")
        })
    })

    describe("With label", () => {
        test("It renders with a label", () => {
            // When
            const component = mount(CsInputField, {
                props: {
                    label: "First name"
                }
            })

            // Then
            expect(component).not.toBeNull()

            const label = component.findComponent(CsInputLabel)
            expect(label.exists()).toBeTruthy()
            expect(label.classes()).toStrictEqual(["cs-input-label"])
            expect(label.text()).toBe("First name")
        })
    })

    describe("Stacked", () => {
        test("It renders", () => {
            // When
            const component = mount(CsInputField, { props: { stacked: true } })

            // Then
            expect(component).not.toBeNull()
            expect(component.props("modelValue")).toBeUndefined()
            expect(component.props("stacked")).toBeTruthy()
            expect(component.classes()).toStrictEqual(["cs-input", "stacked"])
        })
    })
})