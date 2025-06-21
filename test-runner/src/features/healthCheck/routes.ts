import { handleError } from '@features/shared/errorHandling'

import type * as Router from '@koa/router'
import type { RouterContext } from '@koa/router'

/**
 * Handles the request to verify a mission.
 * @param context The context of the request. 
 */
async function handleHealthCheck(context: RouterContext): Promise<void> {
  try {
    context.body = "OK"
  } catch (error) {
    handleError(context, error)
  }
}

/**
 * Add a health check route to a {@link Router}.
 * @param router The router to add the route to.
 * @returns {Router} The provided router with the health check route.
 */
export function registerRoutes(router: Router): Router {
  return router
    .get(`/health-check`, (context: RouterContext) => handleHealthCheck(context))
}