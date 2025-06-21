import { createRouter } from '../../src/application/router'
import { createServer } from '../../src/application/utils'

import type { Server } from 'http'

/**
 * Creates a new test server.
 * @returns {Server} A test server.
 */
export function createTestApi(): Server {
    return createServer(createRouter())
}