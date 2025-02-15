package jp.co.company.space.shared;

import java.util.List;

/**
 * An interface for repository classes that persist {@link T} entities.
 */
public interface PersistenceRepository<T> {
    /**
     * Saves a {@link T} instance.
     * 
     * @param entity The {@link T} instance to save.
     * @return The saved {@link T} instance.
     */
    public T save(T entity);

    /**
     * Merges a persisted {@link T} instance with the provided instance.
     * 
     * @param entity The {@link T} instance to merge.
     * @return The merged {@link T} instance.
     */
    public T merge(T entity);

    /**
     * Saves a {@link List} of {@link T} instances.
     * 
     * @param entities The {@link List} of {@link T} to save.
     * @return The {@link List} of saved {@link T} instances.
     */
    public List<T> save(List<T> entities);
}
