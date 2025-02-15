import dotenv from 'dotenv'

type Environment = {
  /**
   * The host the mock API will be available on.
   */
  host: string
  /**
   * The port the mock API will be available on.
   */
  port: number
}

dotenv.config()

const { HOST,PORT } = process.env

export default {
  host: HOST || "localhost",
  port: Number.parseInt(PORT || '8000')
} satisfies Environment
