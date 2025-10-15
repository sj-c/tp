package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_AMOUNT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PAYMENT_REMARKS;

import java.time.LocalDate;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditPaymentCommand;
import seedu.address.logic.commands.EditPaymentCommand.EditPaymentDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.payment.Amount;

public class EditPaymentCommandParser implements Parser<EditPaymentCommand> {

    @Override
    public EditPaymentCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(
                args, PREFIX_PAYMENT_INDEX, PREFIX_PAYMENT_AMOUNT, PREFIX_PAYMENT_DATE, PREFIX_PAYMENT_REMARKS);

        Index personIndex;
        try {
            personIndex = ParserUtil.parseIndex(map.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(EditPaymentCommand.MESSAGE_USAGE, pe);
        }

        int paymentOneBased = parseRequiredPaymentIndex(map);

        EditPaymentDescriptor d = new EditPaymentDescriptor();

        // Avoid lambdas so checked ParseException can propagate
        Optional<String> amtOpt = map.getValue(PREFIX_PAYMENT_AMOUNT);
        if (amtOpt.isPresent()) {
            d.setAmount(parseAmount(amtOpt.get()));
        }

        Optional<String> dateOpt = map.getValue(PREFIX_PAYMENT_DATE);
        if (dateOpt.isPresent()) {
            d.setDate(parseDate(dateOpt.get()));
        }

        map.getValue(PREFIX_PAYMENT_REMARKS).ifPresent(d::setRemarks);

        if (!d.isAnyFieldEdited()) {
            throw new ParseException(EditPaymentCommand.MESSAGE_NO_FIELDS);
        }

        return new EditPaymentCommand(personIndex, paymentOneBased, d);
    }

    private static int parseRequiredPaymentIndex(ArgumentMultimap map) throws ParseException {
        String raw = map.getValue(PREFIX_PAYMENT_INDEX)
                .orElseThrow(() -> new ParseException("Missing required prefix p/ for payment index"));
        try {
            int oneBased = Integer.parseInt(raw.trim());
            if (oneBased <= 0) throw new NumberFormatException();
            return oneBased;
        } catch (NumberFormatException e) {
            throw new ParseException("Payment index after p/ must be a positive integer (e.g. p/1)");
        }
    }

    private static Amount parseAmount(String s) throws ParseException {
        try {
            return Amount.parse(s.trim());
        } catch (IllegalArgumentException ex) {
            throw new ParseException("Invalid amount. Example: a/10.50");
        }
    }

    private static LocalDate parseDate(String s) throws ParseException {
        try {
            return LocalDate.parse(s.trim()); // yyyy-MM-dd
        } catch (Exception ex) {
            throw new ParseException("Invalid date. Use yyyy-MM-dd, e.g. d/2025-10-15");
        }
    }
}
