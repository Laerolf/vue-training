import type {
    FullConfig,
    FullResult,
    Reporter,
    Suite,
    TestCase,
    TestError,
    TestResult,
    TestStep
} from '@playwright/test/reporter'

export type ParsedError = {
    /**
     * The error message.
     */
    message?: string

    /**
     * The expected value of the test.
     */
    expected?: string

    /**
     * The actual value of the test.
     */
    actual?: string
}

export type StepReport = {
    /**
     * The title of the test step.
     */
    title: string

    /**
     * Whether the test step passed or not.
     */
    pass: boolean

    /**
     * The error that occurred during the execution of the test step.
     */
    error?: ParsedError
}

export type Report = {
    /**
     * The ID of the test.
     */
    id: string

    /**
     * The title of the test.
     */
    title: string

    /**
     * Whether the test passed or not.
     */
    pass?: boolean

    /**
     * The steps of the test.
     */
    steps?: StepReport[]

    /**
     * The errors that occurred during the execution of the test.
     */
    errors?: ParsedError[] | undefined
}

export type TestRunReport = {
    /**
     * The timestamp when the report was created.
     */
    createdAt?: Date

    /**
     * The reports of each ran test.
     */
    tests: Report[]
}

/**
 * A custom reporter that takes steps into account, producing a {@link TestRunReport}.
 */
export default class StepReporter implements Reporter {
    #report: TestRunReport = { tests: [] }

    onBegin(_config: FullConfig, suite: Suite): void {
        this.#report = {
            tests: suite
                .allTests()
                .map((test) => ({ id: test.id, title: test.title }))
        }
    }

    onStepBegin(test: TestCase, _result: TestResult, step: TestStep): void {
        if (step.category != 'test.step') {
            return
        }

        const testReport = this.#report.tests.find(({ id }) => id === test.id)

        if (!testReport) {
            return
        }

        if (!testReport.steps) {
            testReport.steps = []
        }

        testReport.steps.push({ title: step.title, pass: false })
    }

    onStepEnd(test: TestCase, _result: TestResult, step: TestStep): void {
        const testReport = this.#report.tests.find(({ id }) => id === test.id)
        const stepReport = testReport?.steps?.find(
            ({ title }) => title === step.title
        )

        if (!stepReport) {
            return
        }

        stepReport.pass = !step.error
        stepReport.error = this.#parseError(step.error)
    }

    onTestEnd(test: TestCase, result: TestResult): void {
        const testReport = this.#report.tests.find(({ id }) => id === test.id)

        if (!testReport) {
            return
        }

        testReport.pass = result.status === 'passed'

        if (result.errors.length) {
            testReport.errors = result.errors
                .map((error) => this.#parseError(error))
                .filter((parsedError) => parsedError) as ParsedError[]
        }
    }

    onEnd(
        _result: FullResult
    ): Promise<{ status?: FullResult['status'] } | undefined | void> | void {
        this.#report.createdAt = new Date()
        console.info(JSON.stringify(this.#report))
    }

    /**
     * Parses a {@link TestError} into a {@link ParsedError} if possible.
     * @param error The error to parse.
     * @returns A {@link ParsedError} or `undefined`.
     */
    #parseError(error?: TestError): ParsedError | undefined {
        if (!error || !error.message) {
            return
        }

        const ansiRegex = /\u001b\[[0-9;]*m/g
        const cleaned = error.message.replace(ansiRegex, '')

        const lines = cleaned
            .split('\n')
            .map((line) => line.trim())
            .filter(Boolean)

        const parsedError: ParsedError = {}

        for (let i = 0; i < lines.length; i++) {
            const line = lines[i]

            if (line.startsWith('Error:')) {
                parsedError.message = line.replace('Error:', '').trim()
            } else if (line.startsWith('Expected pattern:')) {
                parsedError.expected = line
                    .replace('Expected pattern:', '')
                    .trim()
            } else if (line.startsWith('Received string:')) {
                parsedError.actual = line
                    .replace('Received string:', '')
                    .replace(/^"|"$/g, '')
                    .trim()
            }
        }

        return parsedError
    }
}
