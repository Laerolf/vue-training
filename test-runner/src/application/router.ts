import Router from '@koa/router'

import { registerRoutes } from '@features/mission/routes'

/**
 * Creates a new {@link Router}.
 * @returns {Router} A router.
 */
export function createRouter(): Router {
    const router = new Router({ prefix: '/api' })
    return registerRoutes(router)
}