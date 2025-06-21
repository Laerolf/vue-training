import { verifyMission, getAllMissionRequirements } from './utils'

import type * as Router from '@koa/router'
import type { RouterContext } from '@koa/router'

/**
 * Handles the request to verify a mission.
 * @param context The context of the request. 
 */
async function handleVerifyMission(context: RouterContext<{ missionId: string }>): Promise<void> {
  const { missionId } = context.params

  try {
    context.body = await verifyMission(missionId)
  } catch (error) {
    handleError(context, error)
  }

}

/**
 * Handles the request to get the requirements of a mission.
 * @param context The context of the request. 
 */
async function handleGetMissionRequirements(context: RouterContext<{ missionId: string }>): Promise<void> {
  const { missionId } = context.params

  try {
    context.body = await getAllMissionRequirements(missionId)
  } catch (error) {
    handleError(context, error)
  }
}

/**
 * Handles request errors by setting error properties to the provided {@link RouterContext}.
 * @param context The context of the request.
 * @param error The error that occurred.
 */
function handleError(context: RouterContext, error: unknown): void {
  context.status = 500
  context.body = (error as Error).message
}

/**
 * Adds mission routes to a {@link Router}.
 * @param router The router to add the routes to.
 * @returns {Router} The provided router with the mission routes.
 */
export function registerRoutes(router: Router): Router {
  const baseUrl = "missions"

  return router
    .get(`/${baseUrl}/:missionId/verify`, (context: RouterContext<{ missionId: string }>) => handleVerifyMission(context))
    .get(`/${baseUrl}/:missionId/requirements`, (context: RouterContext<{ missionId: string }>) => handleGetMissionRequirements(context))
}