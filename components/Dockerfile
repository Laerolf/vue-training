FROM node:22-slim AS builder

ENV VERDACCIO_HOST=verdaccio
ENV VERDACCIO_PORT=4873
ENV VERDACCIO_AUTHENTICATION_TOKEN=fake

WORKDIR /app

# Install pnpm globally
RUN corepack enable && corepack prepare pnpm@latest --activate

COPY . .

# Create the .npmrc file
RUN echo "registry=http://${VERDACCIO_HOST}:${VERDACCIO_PORT}" > .npmrc
RUN echo "//${VERDACCIO_HOST}:${VERDACCIO_PORT}/:_authToken=${VERDACCIO_AUTHENTICATION_TOKEN}" > .npmrc

# Install the dependencies
RUN pnpm install

# Build the component library
RUN pnpm build

# Stage 2: Publish
FROM node:20-slim AS production

# Install pnpm globally
RUN corepack enable && corepack prepare pnpm@latest --activate

# Copy the package.json file from the builder stage
COPY --from=builder /app/package.json package.json

# Copy the .npmrc file from the builder stage
COPY --from=builder /app/.npmrc .npmrc

# Copy the built component library files from the builder stage
COPY --from=builder /app/dist /dist

ENTRYPOINT [ "pnpm", "publish" ]