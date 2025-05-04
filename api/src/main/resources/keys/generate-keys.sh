#!/bin/bash

# Use the directory where the script is located
SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
KEY_DIR="$SCRIPT_DIR"

mkdir -p "$KEY_DIR"
echo "🔐 Generating RSA 2048-bit key pair in '$KEY_DIR'..."

# Generate private and public keys
openssl genpkey -algorithm RSA -pkeyopt rsa_keygen_bits:2048 -out "$KEY_DIR/private_key.pem" -quiet
openssl rsa -pubout -in "$KEY_DIR/private_key.pem" -out "$KEY_DIR/public_key.pem"

echo "✅  Keys saved in '$KEY_DIR'!"
