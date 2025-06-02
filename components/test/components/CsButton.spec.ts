import { describe, expect, suite, test } from 'vitest'
import { mount } from '@vue/test-utils'

import CsButton from "@components/CsButton.vue"

suite("CsButton", () => {

    describe("Default", () => {
        test("It renders", () => {
            // When
            const component = mount(CsButton)

            // Then
            expect(component.exists()).toBeTruthy()
            expect(component.classes()).toStrictEqual(["cs-button"])
        })
    })
})