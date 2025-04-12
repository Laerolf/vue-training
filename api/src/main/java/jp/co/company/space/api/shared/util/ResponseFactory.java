package jp.co.company.space.api.shared.util;

import jakarta.ws.rs.core.Response;

/**
 * Utility methods creating additional {@link Response} instances.
 */
public class ResponseFactory {

    /**
     * Creates a new Response with the {@link Response.Status} NOT_FOUND status.
     * @return a new Response with the {@link Response.Status} NOT_FOUND status.
     */
    public static Response createNotFoundResponse() {
        return Response.status(Response.Status.NOT_FOUND).build();
    }

    private ResponseFactory() {
    }
}
