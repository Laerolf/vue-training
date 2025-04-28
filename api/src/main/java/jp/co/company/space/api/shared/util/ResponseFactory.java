package jp.co.company.space.api.shared.util;

import jakarta.ws.rs.core.Response;

/**
 * Utility methods creating additional {@link Response.ResponseBuilder} instances.
 */
public class ResponseFactory {

    /**
     * Creates a new {@link Response.ResponseBuilder} with the {@link Response.Status} NOT_FOUND status.
     *
     * @return a {@link Response.ResponseBuilder}.
     */
    public static Response.ResponseBuilder notFound() {
        return Response.status(Response.Status.NOT_FOUND);
    }

    protected ResponseFactory() {
    }
}
