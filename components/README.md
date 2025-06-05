# 🪐 company-space

**[English]**

**company-space** is a <a href="https://vuejs.org/" target="_blank">Vue</a> 3 component library designed for the **Company Space** project.  
Built with <a href="https://vite.dev/" target="_blank">Vite</a>, unit-tested using <a href="https://vitest.dev/" target="_blank">Vitest</a> and bundled with <a href="https://rollupjs.org/" target="_blank">Rollup</a>.

**[日本語]**

**company-space** は、Company Space プロジェクトのために作られた <a href="https://ja.vuejs.org/" target="_blank">Vue</a> 3 のコンポーネントライブラリです。
<a href="https://ja.vite.dev/" target="_blank">Vite</a> で作られ、<a href="https://vitest.dev/" target="_blank">Vitest</a> で単体テストを行い、<a href="https://rollupjs.org/" target="_blank">Rollup</a> でまとめられています。

## 📦 Installation / インストール

```bash
npm install company-space
# or / または
pnpm add company-space
# or / または
yarn add company-space
```

## 🚀 Usage / 使い方

**[English]**

You can either register the components globally in your Vue application or import them one by one.

**[日本語]**

コンポーネントは、Vue アプリでグローバルに登録するか、必要なものだけ個別にインポートして使うことができます。

1. Register globally using the plugin / プラグインを使ってグローバル登録する

```ts
import { createApp } from 'vue'

import * as components from "company-space/components"
import { addCompanySpace } from "company-space"

import App from './App.vue'

createApp(App).use(addCompanySpace(components)).mount('#app')
```

2. Import individual components / コンポーネントを個別にインポートする

```html
<script setup lang="ts">
import { CsButton } from '@company/components';
</script>

<template>
  <CsButton label="Click me!" />
</template>
```

## 📃 License / ライセンス

**[English]**

This is free and unencumbered software released into the public domain.

Anyone is free to copy, modify, publish, use, compile, sell, or
distribute this software, either in source code form or as a compiled
binary, for any purpose, commercial or non-commercial, and by any
means.

In jurisdictions that recognize copyright laws, the author or authors
of this software dedicate any and all copyright interest in the
software to the public domain. We make this dedication for the benefit
of the public at large and to the detriment of our heirs and
successors. We intend this dedication to be an overt act of
relinquishment in perpetuity of all present and future rights to this
software under copyright law.

**THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
IN NO EVENT SHALL THE AUTHORS BE LIABLE FOR ANY CLAIM, DAMAGES OR
OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
OTHER DEALINGS IN THE SOFTWARE.**

For more information, please refer to <a href="https://unlicense.org" target="_blank">https://unlicense.org<a/>

**[<a href="https://licenses.opensource.jp/Unlicense/Unlicense.html" target="_blank">日本語</a>]**
