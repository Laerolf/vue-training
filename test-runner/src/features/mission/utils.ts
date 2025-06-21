import { utils } from 'src/features/command'

const { verify, getAllTestRequirements } = utils

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

/**
 * Lists all requirements of the provided mission.
 * @param parameters The mission ID to get all requirements for.
 * @returns {Promise<VerificationResult>} A verification result.
 */
export async function getAllMissionRequirements(missionId: string) {
  try {
    return getAllTestRequirements(missionId)
  } catch (error) {
    throw new Error(`Failed to get a mission's requirements: ${(error as Error).message}`)
  }
}