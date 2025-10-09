package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.time.LocalDate;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.payment.Amount;
import seedu.address.model.payment.Payment;

/**
 * Adds a payment to a person identified by the index number in the displayed list.
 */
public class AddPaymentCommand extends Command {

    public static final String COMMAND_WORD = "addpayment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a payment to the person at the given index.\n"
            + "Parameters: INDEX a/AMOUNT d/DATE [r/REMARKS]\n"
            + "Example: " + COMMAND_WORD + " 1 a/23.50 d/2025-10-09 r/taxi home";

    private final Index index;
    private final Payment payment;

    public AddPaymentCommand(Index index, Amount amount, LocalDate date, String remarks) {
        this.index = index;
        this.payment = new Payment(amount, date, remarks);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        var list = model.getFilteredPersonList();

        if (index.getZeroBased() >= list.size()) {
            throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person target = list.get(index.getZeroBased());
        Person updated = target.withAddedPayment(payment);

        model.setPerson(target, updated);

        return new CommandResult(String.format("Added payment %s to %s", payment, updated.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || (other instanceof AddPaymentCommand
                && index.equals(((AddPaymentCommand) other).index)
                && payment.equals(((AddPaymentCommand) other).payment));
    }
}
