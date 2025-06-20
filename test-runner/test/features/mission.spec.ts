import { describe, expect, test } from 'vitest'
import supertest from 'supertest'

import { createServer } from '../../src/application/utils'
import { createRouter } from '../../src/application/router'
import { Server } from 'http'
import { TestRunReport } from '../../reporter'

/**
 * Creates a new server.
 * @returns {Server} A server.
 */
const createApi = (): Server => createServer(createRouter())

describe("features/missions", () => {

    test("It should be possible to verify a mission", { timeout: 60 * 1000 }, async () => {
        // Given
        const missionId = "example"
        const expectedPassingTestId = "a30a6eba6312f6b87ea5-73299983c20d138d5a6e"
        const expectedFailingTestId = "a30a6eba6312f6b87ea5-cf9d7bd3d231719b49f2"
        const expectedFailingStepTitle = "Test the page title"

        // When
        const response = await supertest(createApi()).get(`/api/missions/${missionId}/verify`).set('Accept', 'application/json')

        // Then
        expect(response.status).toBe(200)
        expect(response.body).not.toBeNull()

        const report: TestRunReport = response.body.report

        expect(report).not.toBeNull()
        expect(report.tests).not.toBeNull()
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
        const response = await supertest(createApi()).get(`/api/missions/verify`).set('Accept', 'application/json')

        // Then
        expect(response.status).toBe(404)
        expect(response.body).toMatchObject({})
        expect(response.status).toBe(404)
    })
})