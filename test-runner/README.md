# ✅ test-runner

A API built with [Koa](https://koajs.com/) to manage and verify mission-related data.

## 🚀 Overview

This API exposes the following endpoints:

| Method    | Endpoint                                | Description                           |
| --------- | --------------------------------------- | ------------------------------------- |
| HEAD, GET | `/api/health-check`                     | Check if the API is up and healthy.   |
| HEAD, GET | `/api/missions/:missionId/verify`       | Verify if a mission meets conditions. |
| HEAD, GET | `/api/missions/:missionId/requirements` | Get the requirements of a mission.    |

## 🛠️ Getting started

### Prerequisites

- [Node.js](https://nodejs.org/en/about) (v22+ recommended)
- [npm](https://docs.npmjs.com/about-npm) or [pnpm](https://pnpm.io/) or [yarn](https://yarnpkg.com/getting-started)

### Install

```bash
pnpm install
# or
npm install
# or
yarn install
```

### Run the server

```bash
pnpm start
# or
npm start
# or
yarn start
```
