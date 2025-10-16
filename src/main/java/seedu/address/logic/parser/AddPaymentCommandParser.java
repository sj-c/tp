package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddPaymentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.payment.Amount;

/**
 * Parses input arguments into an AddPaymentCommand.
 */
public class AddPaymentCommandParser implements Parser<AddPaymentCommand> {

    // If you already centralize prefixes in CliSyntax, you can import from there instead.
    private static final Prefix PREFIX_AMOUNT = new Prefix("a/");
    private static final Prefix PREFIX_DATE = new Prefix("d/");
    private static final Prefix PREFIX_REMARKS = new Prefix("r/");

    @Override
    public AddPaymentCommand parse(String args) throws ParseException {
        ArgumentMultimap map = ArgumentTokenizer.tokenize(args, PREFIX_AMOUNT, PREFIX_DATE, PREFIX_REMARKS);

        // require: index preamble + a/ + d/
        if (map.getPreamble().isBlank() || !arePrefixesPresent(map, PREFIX_AMOUNT, PREFIX_DATE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPaymentCommand.MESSAGE_USAGE));
        }

        try {
            String[] indexTokens = map.getPreamble().split(","); // split the comma separated indexes
            List<Index> indexes = new ArrayList<>(); // a List of Index objects

            for (String token : indexTokens) {
                token = token.trim();
                if (!token.isEmpty()) {
                    indexes.add(ParserUtil.parseIndex(token));
                }
            }

            if (indexes.isEmpty()) {
                throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, AddPaymentCommand.MESSAGE_USAGE));
            }

            String amountStr = map.getValue(PREFIX_AMOUNT).get();
            String dateStr = map.getValue(PREFIX_DATE).get();
            String remarks = map.getValue(PREFIX_REMARKS).orElse(null);

            Amount amount = Amount.parse(amountStr);
            LocalDate date = LocalDate.parse(dateStr);

            return new AddPaymentCommand(indexes, amount, date, remarks);
        } catch (ParseException pe) {
            // rethrow parse errors for index
            throw pe;
        } catch (Exception e) {
            // Amount.parse / LocalDate.parse etc.
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPaymentCommand.MESSAGE_USAGE), e);
        }
    }

    /**
     * Returns true if none of the prefixes are missing their values in the given {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
