import type { RouterContext } from "@koa/router"

/**
 * Handles request errors by setting error properties to the provided {@link RouterContext}.
 * @param context The context of the request.
 * @param error The error that occurred.
 */
export function handleError(context: RouterContext, error: unknown): void {
  console.error(`Something went wrong`, error)
  
  context.status = 500
  context.body = (error as Error).message
}