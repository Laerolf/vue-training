/**
 * Represents the commands that can be run.
 */
export enum Command {
  /**
   * Runs `npx playwright test`, running {@link https://playwright.dev/doc|Playwright} tests.
   * @see https://playwright.dev/docs/test-cli#introduction
   */
  TEST = "npx playwright test",

  /**
   * Runs `npx playwright test --list`, collecting all {@link https://playwright.dev/doc|Playwright} tests but do not run.
   * @see https://playwright.dev/docs/test-cli#all-options
   */
  LIST = "npx playwright test --list"
}