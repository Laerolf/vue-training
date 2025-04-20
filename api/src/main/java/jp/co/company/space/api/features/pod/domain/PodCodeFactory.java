package jp.co.company.space.api.features.pod.domain;

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

    public PodCodeFactory(String prefix, int deck, int number) {
        if (prefix == null) {
            throw new IllegalArgumentException("The prefix for the pod code is missing.");
        } else if (deck <= 0) {
            throw new IllegalArgumentException("The deck for the pod code is invalid.");
        } else if (number <= 0) {
            throw new IllegalArgumentException("The number for the pod code is invalid.");
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
    public String create() {
        return String.format("%s%d%05d", prefix, deck, number);
    }
}
