package com.vaadin.server;

/**
 * Utility class to attach resources to any using protected method {@link AbstractClientConnector#setResource(String, Resource)}
 */
public final class ClientConnectorResources {
    private ClientConnectorResources() {
    }

    public static void setResource(AbstractClientConnector component, String key, Resource resource) {
        component.setResource(key, resource);
    }
}