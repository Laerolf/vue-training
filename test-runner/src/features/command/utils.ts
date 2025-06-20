import { exec, ExecException } from 'node:child_process'

import { Command } from './constants'

import { JSONReport } from '@playwright/test/reporter'

/**
 * Represents the result of a ran process.
 */
type ProcessResult = {
    /**
     * The {@link ExecException|error} of the ran process, if any.
     */
    error?: ExecException

    /**
     * The output of the ran process.
     */
    stdout: string

    /**
     * The output of the ran process's error, if any.
     */
    stderr?: string
}

/**
 * Represents a verification result.
 */
type VerificationResult = {
    /**
     * The status of the verification: `true` when passed, `false` when the verification failed.
     */
    passed: boolean
    /**
     * The errors of the verification, if any.
     */
    errors?: string[]

    report?: JSONReport
}

/**
 * Prepares a command before it is executed.
 * @param command The command to run.
 * @param parameters The parameters of the command to run.
 * @returns {string} A prepared command as a `string`.
 */
function prepareCommand(command: Command, parameters?: string[]): string {
    if (!parameters || (parameters && parameters.length == 0)) {
        return command;
    }

    return `${command} ${parameters.join(" ")}`;
}

/**
 * Prepares the a {@link Command} to be run and runs it.
 * @param command The command to run.
 * @param parameters The paramaters of the command to run.
 * @returns {Promise<ProcessResult>} A command result.
 */
function performCommand(command: Command, parameters?: string[]): Promise<ProcessResult> {
    try {
        const preparedCommand = prepareCommand(command, parameters);

        return new Promise((resolve, reject) =>
            exec(preparedCommand, (error, stdout, stderr) => {
                if (stderr) {
                    reject({ error, stdout, stderr })
                } else {
                    resolve({ error: error ?? undefined, stdout, stderr })
                }
            })
        )
    } catch (error) {
        console.error("Failed to perform a command", error)
        throw new Error(`Failed to perform a command: ${(error as Error).message}`)
    }
}

/**
 * Creates a {@link VerificationResult} based on the result of a command.
 * @param output The result of the ran command.
 * @returns {VerificationResult} A result of a ran command.
 * @throws When the provided command result is not valid.
 */
function createVerificationResult(output: ProcessResult): VerificationResult {
    try {
        const { error, stdout } = output

        if (error && !stdout) {
            return {
                passed: false,
                errors: [error.message]
            }
        }

        const report: JSONReport = JSON.parse(stdout)

        return {
            passed: !error,
            errors: undefined,
            report
        }
    } catch (error) {
        console.error("Failed to create a verification result", error)
        throw new Error(`Failed to create a verification result: ${(error as Error).message}`)
    }
}

/**
 * Verifies the provided test.
 * @param parameters The parameters of the test to verify.
 * @returns {Promise<VerificationResult>} A verification result.
 */
export async function verify(...parameters: string[]): Promise<VerificationResult> {
    const result = await performCommand(Command.TEST, parameters);
    return createVerificationResult(result)
}
