import { defineConfig, devices } from '@playwright/test'

import { targetAddress } from './src/application/environment'

/**
 * See https://playwright.dev/docs/test-configuration.
 */
export default defineConfig({
    testDir: './lib/tests',
    fullyParallel: true,
    reporter: './reporter.ts',
    maxFailures: 1,

    projects: [
        {
            name: 'chromium',
            use: { ...devices['Desktop Chrome'] }
        }
    ],

    use: {
        baseURL: targetAddress
    }
})
