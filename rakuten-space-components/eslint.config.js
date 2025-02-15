import globals from "globals";
import pluginJs from "@eslint/js";
import tsEslint from "typescript-eslint";
import pluginVue from "eslint-plugin-vue";
import vueParser from "vue-eslint-parser";
import eslintConfigPrettier from "eslint-config-prettier";

export default [
    { languageOptions: { globals: { ...globals.browser, ...globals.node } } },
    pluginJs.configs.recommended,
    ...tsEslint.configs.recommended,
    ...pluginVue.configs["flat/essential"],
    {
        files: ["*.vue", "**/*.vue"],
        languageOptions: {
            parser: vueParser,
            parserOptions: { parser: tsEslint.parser, sourceType: "module" },
        },
    },
    eslintConfigPrettier,
];
