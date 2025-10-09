package seedu.address.model.payment;

import java.math.BigDecimal;

/**
 * Immutable value object representing a monetary amount.
 * <p>
 * Enforces domain rules: strictly positive and at most 2 decimal places.
 */
public final class Amount {
    /** Message used when construction fails validation. */
    public static final String MESSAGE_CONSTRAINTS =
            "Invalid amount (must be positive, up to 2 decimal places).";

    /** Fixed scale (number of decimal places). */
    private static final int SCALE = 2;

    /** Normalized decimal value at scale {@link #SCALE}. */
    public final BigDecimal value;

    /**
     * Constructs an {@code Amount} from a raw string.
     *
     * @param rawString decimal string (e.g., {@code "12.34"})
     * @throws IllegalArgumentException if not strictly positive or exceeds 2 dp
     * @throws NumberFormatException if the string cannot be parsed as a decimal
     */
    public Amount(String rawString) {
        this.value = parse(rawString);
        validate(); // ensure it's positive
    }

    /**
     * Constructs an {@code Amount} from a {@link BigDecimal}.
     *
     * @param value decimal value
     * @throws IllegalArgumentException if not strictly positive or exceeds 2 dp
     * @throws ArithmeticException if scaling is not exact
     */
    public Amount(BigDecimal value) {
        this.value = value.setScale(SCALE, BigDecimal.ROUND_UNNECESSARY);
        validate();
    }

    private static BigDecimal parse(String raw) {
        BigDecimal v = new BigDecimal(raw);
        // May throw ArithmeticException if more than 2 dp
        return v.setScale(SCALE, BigDecimal.ROUND_UNNECESSARY);
    }

    private void validate() {
        if (value.signum() <= 0) {
            throw new IllegalArgumentException(MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Returns the plain string form (no scientific notation).
     */
    @Override
    public String toString() {
        return value.toPlainString();
    }

    /**
     * Value equality (same normalized BigDecimal value and scale).
     */
    @Override
    public boolean equals(Object o) {
        return (o instanceof Amount) && value.equals(((Amount) o).value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
