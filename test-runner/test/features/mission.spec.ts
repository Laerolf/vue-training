import { describe, expect, test } from 'vitest'
import supertest from 'supertest'

import { createTestApi } from '@unit-tests/shared/utils'

import type { TestRunReport } from '../../reporter'
import { timeouts } from '@unit-tests/shared/constants'

describe("features/missions", () => {

    describe("/requirements", () => {
        test("It should be possible to get the requirements of a mission", { timeout: timeouts.WAIT_FOR_PLAYWRIGHT }, async () => {
            // Given
            const missionId = "example"

            // When
            const response = await supertest(createTestApi()).get(`/api/missions/${missionId}/requirements`).set('Accept', 'application/json')

            // Then
            expect(response.status).toBe(200)
            expect(response.body).not.toBeNull()

            const report: TestRunReport = response.body

            expect(report).not.toBeNull()
            expect(report.createdAt).not.toBeNull()
            expect(report.tests).not.toBeNull()
            expect(report.tests).toHaveLength(3)

            report.tests.forEach(test => {
                expect(test).toBeDefined()
                expect(test.id).toBeDefined()
                expect(test.title).toBeDefined()
                expect(test.pass).toBeUndefined()
                expect(test.steps).toBeUndefined()
                expect(test.errors).toBeUndefined()
            })
        })
    })

    describe("/verify", () => {
        test("It should be possible to verify a mission", { timeout: timeouts.WAIT_FOR_PLAYWRIGHT }, async () => {
            // Given
            const missionId = "example"
            const expectedPassingTestId = "a30a6eba6312f6b87ea5-73299983c20d138d5a6e"
            const expectedFailingTestId = "a30a6eba6312f6b87ea5-cf9d7bd3d231719b49f2"
            const expectedFailingStepTitle = "Test the page title"

            // When
            const response = await supertest(createTestApi()).get(`/api/missions/${missionId}/verify`).set('Accept', 'application/json')

            // Then
            expect(response.status).toBe(200)
            expect(response.body).not.toBeNull()
            expect(response.body).toBeDefined()

            const report: TestRunReport = response.body

            expect(report).toBeDefined()
            expect(report.createdAt).toBeDefined()
            expect(report.tests).toBeDefined()
            expect(report.tests).toHaveLength(3)

            const expectedFailingTest = report.tests.find(test => test.id === expectedFailingTestId)

            expect(expectedFailingTest).toBeDefined()
            expect(expectedFailingTest!.pass).toBeFalsy()
            expect(expectedFailingTest?.steps).toBeDefined()
            expect(expectedFailingTest?.steps).toHaveLength(2)
            expect(expectedFailingTest?.errors).toBeDefined()
            expect(expectedFailingTest?.errors).toHaveLength(1)

            expectedFailingTest?.steps?.forEach(step => {
                expect(step.title).toBeDefined()

                if (step.title == expectedFailingStepTitle) {
                    expect(step.pass).toBeFalsy()
                    expect(step.error).toBeDefined()

                    expect(step.error?.message).toBeDefined()
                    expect(step.error?.expected).toBeDefined()
                    expect(step.error?.actual).toBeDefined()
                } else {
                    expect(step.pass).toBeTruthy()
                    expect(step.error).not.toBeDefined()
                }
            })

            const expectedPassingTest = report.tests.find(test => test.id === expectedPassingTestId)

            expect(expectedPassingTest).toBeDefined()
            expect(expectedPassingTest!.pass).toBeTruthy()
            expect(expectedPassingTest?.steps).toBeDefined()
            expect(expectedPassingTest?.steps).toHaveLength(2)
            expect(expectedPassingTest?.errors).not.toBeDefined()

            expectedPassingTest?.steps?.forEach(step => {
                expect(step.title).toBeDefined()
                expect(step.pass).toBeTruthy()
                expect(step.error).not.toBeDefined()
            })
        })

        test("It should not be possible to verify a mission with an id", async () => {
            // When
            const response = await supertest(createTestApi()).get(`/api/missions/verify`).set('Accept', 'application/json')

            // Then
            expect(response.status).toBe(404)
            expect(response.body).toMatchObject({})
            expect(response.status).toBe(404)
        })
    })
})