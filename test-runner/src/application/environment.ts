import { config } from 'dotenv'

/**
 * Represents an environment's properties.
 */
type Environment = {
    /**
     * The host address that the server is available on.
     */
    host: string
    /**
     * The port that the server is available on.
     */
    port: number
    /**
     * The protocol to use for the target application's address.
     */
    targetProtocol: string
    /**
     * The host address that the target application is available on.
     */
    targetHost: string
    /**
     * The port that the target application is available on.
     */
    targetPort: number
}

config()

const { HOST, PORT, TARGET_PROTOCOL, TARGET_HOST, TARGET_PORT } = process.env

/**
 * The properties of the current environment.
 */
export const properties: Environment = {
    host: HOST || 'localhost',
    port: PORT ? Number.parseInt(PORT) : 8000,
    targetProtocol: TARGET_PROTOCOL || 'http',
    targetHost: TARGET_HOST || 'localhost',
    targetPort: TARGET_PORT ? Number.parseInt(TARGET_PORT) : 8080
}

/**
 * The url of the target application.
 */
export const targetAddress: string = `${properties.targetProtocol}://${properties.targetHost}:${properties.targetPort}`
