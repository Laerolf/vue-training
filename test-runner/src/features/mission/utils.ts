import { utils } from 'src/features/command'

const { verify } = utils

/**
 * Verifies the provided mission.
 * @param missionId The mission to verify.
 * @returns The result of the provided mission's verification.
 */
export async function verifyMission(missionId: string) {
  try {
    return verify(missionId)
  } catch (error) {
    throw new Error(`Failed to test a mission: ${(error as Error).message}`)
  }
}