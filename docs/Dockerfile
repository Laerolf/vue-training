# Build Stage 1
FROM node:22-slim AS build

WORKDIR /app

RUN corepack enable

# Copy package.json and your lockfile, here we add pnpm-lock.yaml for illustration
COPY package.json pnpm-lock.yaml pnpm-workspace.yaml ./

# Install dependencies
RUN pnpm install

# Copy the entire project
COPY . ./

# Build the project
RUN pnpm build

# Build Stage 2
FROM node:22-alpine
WORKDIR /app

# Only `.output` folder is needed from the build stage
COPY --from=build /app/.output/ ./

# Change the port and host
ENV PORT 80
ENV HOST 0.0.0.0

EXPOSE 80

ENTRYPOINT ["node", "/app/server/index.mjs"]