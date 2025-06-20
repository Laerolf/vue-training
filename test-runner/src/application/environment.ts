import dotenv from 'dotenv'

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
}

dotenv.config()

const { HOST,PORT } = process.env

/**
 * The properties of the current environment.
 */
export const properties: Environment = {
  host: HOST || "localhost",
  port: Number.parseInt(PORT || '8000')
}
