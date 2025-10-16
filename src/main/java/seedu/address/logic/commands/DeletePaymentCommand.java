package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.payment.Payment;
import seedu.address.model.person.Person;

/**
 * Deletes a payment from a person identified by the index number in the displayed list.
 */
public class DeletePaymentCommand extends Command {

    public static final String COMMAND_WORD = "deletepayment";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Deletes a payment from one or more persons identified by their indexes, "
        + "as shown in the displayed person list.\n"
        + "Parameters: PERSON_INDEX[,PERSON_INDEX]... p/PAYMENT_INDEX\n"
        + "Example: " + COMMAND_WORD + " 1 p/2";

    public static final String MESSAGE_SUCCESS = "Deleted payment #%d from %s";
    public static final String MESSAGE_INVALID_PAYMENT_INDEX = "Invalid payment index for person: %s";

    private final List<Index> personIndexes;
    private final Index paymentIndex; // index within the person's payment list

    /**
     * Deletes a payment from a person identified by the index number in the displayed list.
     */
    public DeletePaymentCommand(List<Index> personIndexes, Index paymentIndex) {
        requireNonNull(personIndexes);
        requireNonNull(paymentIndex);
        this.personIndexes = List.copyOf(personIndexes);
        this.paymentIndex = paymentIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        var list = model.getFilteredPersonList();

        List<String> updatedNames = new ArrayList<>();

        for (Index personIndex : personIndexes) {
            if (personIndex.getZeroBased() >= list.size()) {
                throw new CommandException(MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            Person target = list.get(personIndex.getZeroBased());
            List<Payment> payments = target.getPayments();

            if (paymentIndex.getZeroBased() >= payments.size()) {
                throw new CommandException(String.format(MESSAGE_INVALID_PAYMENT_INDEX, target.getName()));
            }

            Payment toDelete = payments.get(paymentIndex.getZeroBased());
            Person updated = target.withRemovedPayment(toDelete);

            model.setPerson(target, updated);
            updatedNames.add(updated.getName().toString());
        }

        String joinedNames = String.join(", ", updatedNames);
        String message = String.format(MESSAGE_SUCCESS, paymentIndex.getOneBased(), joinedNames);

        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        return other == this
            || (other instanceof DeletePaymentCommand
            && personIndexes.equals(((DeletePaymentCommand) other).personIndexes)
            && paymentIndex.equals(((DeletePaymentCommand) other).paymentIndex));
    }
}
