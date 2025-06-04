import type { Component, Plugin } from "vue";

import "./assets/styling/main.css";

export * as components from './components';

/**
 * Creates the Company plugin based on the provided parameters.
 * @param {Record<string, Component>=} components The components to register globally in a Vue project.
 * @returns {Plugin} A Vue plugin that can be used in the set-up of a Vue project.
 */
export function addCompanySpace(components?: Record<string, Component>): Plugin {
    return {
        install(app) {
            if (components) {
                Object.entries(components).forEach(([componentName, component]) => app.component(componentName, component))
            }
        }
    } satisfies Plugin
}