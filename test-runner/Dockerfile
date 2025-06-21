# --- Build Stage ---
FROM node:22-slim AS build
WORKDIR /app

RUN corepack enable && corepack prepare pnpm@latest --activate
COPY package.json pnpm-lock.yaml pnpm-workspace.yaml ./
RUN pnpm install --frozen-lockfile

COPY . ./

RUN pnpm build

# --- Runtime Stage ---
FROM node:22-slim AS runtime

RUN apt-get update \
 && apt-get install -y curl \
 && rm -rf /var/lib/apt/lists/*

RUN corepack enable && corepack prepare pnpm@latest --activate

WORKDIR /app

COPY --from=build /app/dist /app

RUN pnpm add @playwright/test

ENV PORT=80
ENV HOST=0.0.0.0

EXPOSE 80

HEALTHCHECK --interval=30s --timeout=5s --start-period=10s --retries=3 \
CMD curl -f http://localhost:80/api/health-check || exit 1

CMD ["node", "src/main.js"]
