package jp.co.company.space.api.features.pod.domain;

import jp.co.company.space.api.features.pod.exception.PodCodeError;
import jp.co.company.space.api.features.pod.exception.PodCodeException;

/**
 * A factoring creating a code representing the code of a pod.
 */
public class PodCodeFactory {

    /**
     * The prefix for the pod code.
     */
    private final String prefix;

    /**
     * The deck of the deck in a space shuttle layout.
     */
    private final int deck;

    /**
     * The number of the pod on a deck.
     */
    private final int number;

    public PodCodeFactory(String prefix, int deck, int number) throws PodCodeException {
        if (prefix == null) {
            throw new PodCodeException(PodCodeError.MISSING_CODE_PREFIX);
        } else if (deck <= 0) {
            throw new PodCodeException(PodCodeError.INVALID_DECK_NUMBER);
        } else if (number <= 0) {
            throw new PodCodeException(PodCodeError.INVALID_NUMBER);
        }

        this.prefix = prefix;
        this.deck = deck;
        this.number = number;
    }

    /**
     * Creates a pod code based on the provided pod number and deck.
     *
     * @return A pod code.
     */
    public String create() throws IllegalArgumentException {
        return String.format("%s%d%05d", prefix, deck, number);
    }
}
