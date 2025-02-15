/**
 * Returns the provided string with the first character in uppercase.
 * @param {string=} value The string to capitalize.
 * @returns {string} The capitalized string.
 */
export function capitalize(value?: string): string {
  if (!value && typeof value != 'string') {
    return ''
  }

  return value.charAt(0).toUpperCase() + value.slice(1)
}
