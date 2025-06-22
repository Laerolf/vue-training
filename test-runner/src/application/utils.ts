import Koa from 'koa'
import KoaCors from '@koa/cors'
import KoaBodyParser from 'koa-bodyparser'

import type Router from '@koa/router'
import type { Server } from 'http'

import { properties } from './environment'

/**
 * Adds listeners to the provided server.
 * @param server The server to add the listeners to.
 * @param router The router of the server.
 * @returns {Server} The provided server with listeners.
 */
function addListeners(server: Server, router: Router): Server {
    server.on('listening', () => {
        const baseUrl = getBaseUrl(server)

        console.info(
            `The server is listening on the following address: ${baseUrl}`
        )
        console.info('\nThe following end-points are available:')

        router.stack.forEach(({ methods, path }) =>
            console.info(
                `- ${methods.map((method) => `[${method}]`).join('')} ${[baseUrl, path].join('')}`
            )
        )
    })

    return server.on('error', (error) => console.error(error))
}

/**
 * Creates a new server based on the provided paramaters.
 * @param router The router of the server.
 * @returns {Server} A new server.
 */
export function createServer(router: Router): Server {
    const server = new Koa()
        .use(
            KoaBodyParser({
                enableTypes: ['text']
            })
        )
        .use(KoaCors())
        .use(router.routes())
        .use(router.allowedMethods())
        .listen(properties.port, properties.host)

    return addListeners(server, router)
}

/**
 * Returns the base url of this application.
 * @returns {string} A base url.
 */
function getBaseUrl(app: Server): string {
    const appInformation = app.address() || {
        address: 'localhost',
        port: properties.port
    }

    if (typeof appInformation == 'string') {
        return appInformation
    } else {
        let { address } = appInformation
        const { port } = appInformation

        if (address === '::1') {
            address = 'http://localhost'
        }

        return [address, port].join(':')
    }
}
