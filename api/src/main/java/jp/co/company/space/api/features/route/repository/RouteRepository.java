package jp.co.company.space.api.features.route.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TransactionRequiredException;
import jp.co.company.space.api.features.route.domain.Route;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * The class for {@link Route} DB actions.
 */
@ApplicationScoped
public class RouteRepository {

    @PersistenceContext(unitName = "domain")
    private EntityManager entityManager;

    protected RouteRepository() {}

    /**
     * Searches an {@link Optional} instance of the {@link Route} class by its ID.
     *
     * @param id The ID of the route to search for.
     * @return An {@link Optional} {@link Route}.
     */
    public Optional<Route> findById(String id) {
        return Optional.ofNullable(entityManager.find(Route.class, id));
    }

    /**
     * Gets all the saved {@link Route} instances.
     *
     * @return A {@link List} of {@link Route} instances.
     */
    public List<Route> getAll() {
        return entityManager.createNamedQuery("Route.selectAll", Route.class).getResultList();
    }

    /**
     * Saves a {@link Route} instance.
     *
     * @param route The {@link Route} instance to save.
     * @return The saved {@link Route} instance.
     */
    public Route save(Route route) {
        if (findById(route.getId()).isEmpty()) {
            try {
                entityManager.persist(route);
                return findById(route.getId()).orElseThrow();
            } catch (TransactionRequiredException | EntityExistsException | NoSuchElementException
                    | IllegalArgumentException exception) {
                throw new IllegalArgumentException("Failed to save a route instance.", exception);
            }
        } else {
            return merge(route);
        }
    }

    /**
     * Merges a persisted {@link Route} instance with the provided instance.
     *
     * @param route The {@link Route} instance to merge.
     * @return The merged {@link Route} instance.
     */
    public Route merge(Route route) {
        try {
            return entityManager.merge(route);
        } catch (TransactionRequiredException | IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to merge a route instance.", exception);
        }
    }

    /**
     * Saves a {@link List} of {@link Route} instances.
     *
     * @param routes The {@link List} of {@link Route} to save.
     * @return The {@link List} of saved {@link Route} instances.
     */
    public List<Route> save(List<Route> routes) {
        try {
            return routes.stream().map(this::save).toList();
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Failed to save a list of routes.", exception);
        }
    }
}
