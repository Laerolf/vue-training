import { build } from 'esbuild'
import { rmSync, cpSync, copyFileSync } from 'node:fs'

rmSync('dist', { recursive: true, force: true })
cpSync('lib', 'dist/lib', { recursive: true })
copyFileSync('reporter.ts', 'dist/reporter.ts')

build({
    entryPoints: ['src/main.ts', 'playwright.config.ts'],
    outdir: 'dist',
    bundle: true,
    platform: 'node',
    target: 'node22',
    format: 'cjs',
    sourcemap: true,
    packages: 'bundle',
    external: ['chromium-bidi', 'playwright']
}).catch(() => process.exit(1))
