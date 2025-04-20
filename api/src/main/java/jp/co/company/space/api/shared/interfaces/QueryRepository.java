package jp.co.company.space.api.shared.interfaces;

import java.util.List;
import java.util.Optional;

/**
 * An interface for repository classes that get persisted {@link T} entities.
 */
// TODO: DELETE
public interface QueryRepository<T> {
    /**
     * Searches an {@link Optional} instance of the {@link T} class by its ID.
     *
     * @param id The ID of the {@link T} instance to search for.
     * @return An {@link Optional} {@link T}.
     */
    Optional<T> findById(String id);

    /**
     * Gets all the saved {@link T} instances.
     *
     * @return A {@link List} of {@link T} instances.
     */
    List<T> getAll();
}
