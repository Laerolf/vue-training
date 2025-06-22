import Router from '@koa/router'

import { registerRoutes as registerHealthCheckRoute } from '@features/healthCheck/routes'
import { registerRoutes as registerMissionRoutes } from '@features/mission/routes'

/**
 * Creates a new {@link Router}.
 * @returns {Router} A router.
 */
export function createRouter(): Router {
    const router = new Router({ prefix: '/api' })
    registerHealthCheckRoute(router)
    return registerMissionRoutes(router)
}
