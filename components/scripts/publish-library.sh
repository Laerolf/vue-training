#!/bin/sh

set -e

echo "registry=http://verdaccio:4873" > .npmrc
echo "//verdaccio:4873/:_authToken=fake" > .npmrc

echo "Installing the dependencies of the component library..."

# Configure pnpm to use Verdaccio
pnpm config set registry http://verdaccio:4873

# Install dependencies and build
pnpm install

echo "✅ The component library dependencies were installed successfully"
echo "Building the component library..."
pnpm build

echo "✅ The component library dependencies was built successfully"
echo "Publishing component library to Verdaccio..."

# Publish without prompt (assumes public access, no auth)
pnpm publish --registry http://verdaccio:4873

echo "✅ Component library published successfully to Verdaccio"
