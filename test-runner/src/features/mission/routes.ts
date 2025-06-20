import type Router from '@koa/router'
import { verifyMission } from './utils'

const baseUrl = "missions"

/**
 * Adds mission routes to a {@link Router}.
 * @param router The router to add the routes to.
 * @returns {Router} The provided router with the mission routes.
 */
export function registerRoutes(router: Router): Router {
  return router.get(`/${baseUrl}/:missionId/verify`, async (context) => {
    const { missionId } = context.params

    try {
      const result = await verifyMission(missionId)

      context.body = result
    } catch (error) {
      // const {stdout} = error as ExecAsyncResult
      // const errorMessage = stdout.split(/\n/g)[0]
      context.status = 500
      context.body = (error as Error).message
    }
  })
}