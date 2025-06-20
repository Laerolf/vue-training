import { test, expect } from '@playwright/test'

test.describe("Example page", () => {

  test('get started link', async ({ page }) => {
    await page.goto('https://playwright.dev/')

    // Click the get started link.
    await page.getByRole('link', { name: 'Get started' }).click()

    // Expects page to have a heading with the name of Installation.
    await expect(
      page.getByRole('heading', { name: 'Installation' })
    ).toBeVisible()
  })

  test('has Playwright in its title', async ({ page }) => {
    await test.step('Navigate to the https://playwright.dev/', async () =>
      await page.goto('https://playwright.dev/')
    )
    
    await test.step('Test the page title', async () =>
      await expect(page, "The page title is wrong.").toHaveTitle(new RegExp(/.*Playwright/))
    )
  })

  test('has Playwight in its title', async ({ page }) => {
    await test.step('Navigate to the https://playwright.dev/', async () =>
      await page.goto('https://playwright.dev/')
    )
    
    await test.step('Test the page title', async () =>
      await expect(page, "The page title is wrong.").toHaveTitle(new RegExp(/.*Playwight/))
    )
  })
})


