import { defineConfig } from 'vitest/config'
import tsconfigPaths from "vite-tsconfig-paths"

export default defineConfig({
    plugins: [tsconfigPaths()],
    test: {
        include: ['test\/**\/*.{test,spec}.?(c|m)[jt]s?(x)'],
        passWithNoTests: true,
        reporters: 'verbose',

        coverage: {
            provider: 'v8',

            reportOnFailure: true,
            watermarks: {
                statements: [80, 100],
                branches: [80, 100],
                functions: [80, 100],
                lines: [80, 100]
            }
        }
    }
})