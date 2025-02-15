import Router from '@koa/router'
import { exec, ExecException } from 'node:child_process'

enum Command {
  TEST= "npx playwright test"
}

type ExecAsyncResult = {
  error: ExecException | null
  stdout: string
  stderr: string
}

function execAsync(command: Parameters<typeof exec>[0]): Promise<ExecAsyncResult> {
  return new Promise((resolve, reject) =>
     exec(command, (error, stdout, stderr) => {
      if (error) {
         reject({error, stdout, stderr})
      } else {
        resolve({error, stdout, stderr})
      }
    })
  )
}

function toTestResult(output: string) {
  try {
    const {errors} = JSON.parse(output)

    return {
      passed: !errors.length,
      errors
    }
  } catch(error) {
    throw new Error(`Failed to produce a test result: ${(error as Error).message}`)
  }
}

async function testMission(missionName: string) {
  try {
    const result = await execAsync(Command.TEST)
    return toTestResult(result.stdout)
  } catch(error) {
    throw new Error(`Failed to test a mission: ${(error as Error).message}`)
  }
}

const router = new Router({ prefix: '/api' })

router.post('/validate', async (context) => {
  const { body: missionName } = context.request

  try {
    const result = await testMission(missionName as string)

    context.body = result
  } catch(error) {
    // const {stdout} = error as ExecAsyncResult
    // const errorMessage = stdout.split(/\n/g)[0]
    context.status = 500
    context.body = (error as Error).message
  }
})

export default router
