FROM node:20-slim

WORKDIR /app

# Install pnpm globally
RUN corepack enable && corepack prepare pnpm@latest --activate

COPY . .

# Make the script executable
RUN chmod +x ./scripts/publish-library.sh