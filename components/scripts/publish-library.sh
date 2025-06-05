#!/bin/sh

set -e

echo "Creating the .npmrc file..."
echo "registry=http://verdaccio:4873" > .npmrc
echo "//verdaccio:4873/:_authToken=fake" > .npmrc
echo "✅  The .npmrc file has been created"

echo "Installing the dependencies of the component library..."
pnpm install
echo "✅  The component library dependencies were installed"

echo "Building the component library..."
pnpm build
echo "✅  The component library dependencies was built"

echo "Publishing component library to Verdaccio..."
pnpm publish
echo "✅  Component library published successfully to Verdaccio"
