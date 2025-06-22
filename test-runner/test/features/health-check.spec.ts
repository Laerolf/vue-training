import { describe, test, expect } from 'vitest'
import supertest from 'supertest'

import { createTestApi } from '@unit-tests/shared/utils'

describe("features/healthCheck", () => {

    test("It should be possible to get a positive health check result", async () => {
            // When
            const response = await supertest(createTestApi()).get('/api/health-check')

            // Then
            expect(response.status).toBe(200)
        })

})