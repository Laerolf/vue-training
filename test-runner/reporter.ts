import type { FullResult, Reporter, TestCase, TestError, TestResult, TestStep } from '@playwright/test/reporter'

export type ParsedError = {
    message?: string
    expected?: string
    actual?: string
}

export type StepReport = {
    title: string
    pass: boolean
    error?: ParsedError
}

export type Report = {
    id: string
    title: string
    pass: boolean
    steps?: StepReport[]
    errors?: ParsedError[] | undefined
}

export type TestRunReport = {
    tests: Report[]
}


export default class StepReporter implements Reporter {

    #report: TestRunReport = { tests: [] }

    onTestBegin({ id, title }: TestCase, result: TestResult): void {
        this.#report.tests.push({ id, title, pass: false })
    }

    onStepBegin(test: TestCase, result: TestResult, step: TestStep): void {
        if (step.category != "test.step") {
            return
        }

        const testReport = this.#report.tests.find(t => t.id === test.id)

        if (!testReport) {
            return
        }

        if (!testReport.steps) {
            testReport.steps = []
        }

        testReport.steps.push({ title: step.title, pass: false })
    }

    onStepEnd(test: TestCase, result: TestResult, step: TestStep): void {
        const testReport = this.#report.tests.find(t => t.id === test.id)
        const stepReport = testReport?.steps?.find(s => s.title === step.title)

        if (!stepReport) {
            return
        }

        stepReport.pass = !step.error
        stepReport.error = this.#parseError(step.error)
    }

    onTestEnd(test: TestCase, result: TestResult): void {
        const testReport = this.#report.tests.find(t => t.id === test.id)

        if (!testReport) {
            return
        }

        testReport.pass = result.status === 'passed'
        testReport.errors = result.errors.length ? result.errors.map(error => this.#parseError(error)).filter(parsedError => parsedError) as ParsedError[] : undefined
    }

    onEnd(result: FullResult): Promise<{ status?: FullResult['status'] } | undefined | void> | void {
        console.info(JSON.stringify(this.#report))
    }

    #parseError(error?: TestError): ParsedError | undefined {
        if (!error || !error.message) {
            return
        }

        const ansiRegex = /\u001b\[[0-9;]*m/g;
        const cleaned = error.message.replace(ansiRegex, '');

        const lines = cleaned.split('\n').map(line => line.trim()).filter(Boolean);

        const parsedError: ParsedError = {};

        for (let i = 0; i < lines.length; i++) {
            const line = lines[i];

            if (line.startsWith('Error:')) {
                parsedError.message = line.replace('Error:', '').trim();
            } else if (line.startsWith('Expected pattern:')) {
                parsedError.expected = line.replace('Expected pattern:', '').trim();
            } else if (line.startsWith('Received string:')) {
                parsedError.actual = line.replace('Received string:', '').replace(/^"|"$/g, '').trim();
            }
        }

        return parsedError;
    }

}