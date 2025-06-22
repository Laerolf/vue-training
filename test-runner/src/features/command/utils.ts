import { exec } from 'node:child_process'

import { Command } from './constants'

import type { ExecException } from 'node:child_process'
import type { TestRunReport } from '../../../reporter'

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
 * Prepares a command before it is executed.
 * @param command The command to run.
 * @param parameters The parameters of the command to run.
 * @returns {string} A prepared command as a `string`.
 */
function prepareCommand(command: Command, parameters?: string[]): string {
    if (!parameters || parameters.length === 0) {
        return command
    }

    return `${command} ${parameters.join(' ')}`
}

/**
 * Prepares a {@link Command} to be run and runs it.
 * @param command The command to run.
 * @param parameters The paramaters of the command to run.
 * @returns {Promise<ProcessResult>} A command result.
 */
function performCommand(
    command: Command,
    parameters?: string[]
): Promise<ProcessResult> {
    try {
        const preparedCommand = prepareCommand(command, parameters)

        return new Promise((resolve, reject) =>
            exec(preparedCommand, (error, stdout, stderr) => {
                if (stderr && !stdout) {
                    reject({ error, stdout, stderr })
                } else {
                    resolve({ stdout, stderr })
                }
            })
        )
    } catch (error) {
        console.error('Failed to perform a command', error)
        throw new Error(
            `Failed to perform a command: ${(error as Error).message}`
        )
    }
}

/**
 * Parses the output of a command to a JSON object.
 * @param output The result of the ran command.
 * @returns A JSON object.
 * @throws When the provided command result is not valid.
 */
function parseJsonOutput<T>(output: ProcessResult): T {
    try {
        return JSON.parse(output.stdout) as T
    } catch (error) {
        console.error('Failed to parse JSON output', error)
        throw new Error(`JSON parse error: ${(error as Error).message}`)
    }
}

/**
 * Verifies the provided test.
 * @param parameters The parameters of the test to verify.
 * @returns {Promise<TestRunReport>} A report.
 */
export async function verify(...parameters: string[]): Promise<TestRunReport> {
    const result = await performCommand(Command.TEST, parameters)
    return parseJsonOutput<TestRunReport>(result)
}

/**
 * Lists all tests of a provided test.
 * @param parameters The parameters of the test to get list.
 * @returns {Promise<TestRunReport>} A report.
 */
export async function getAllTestRequirements(
    ...parameters: string[]
): Promise<TestRunReport> {
    const result = await performCommand(Command.LIST, parameters)
    return parseJsonOutput<TestRunReport>(result)
}
