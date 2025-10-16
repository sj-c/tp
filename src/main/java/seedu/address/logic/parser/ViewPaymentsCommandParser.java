package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewPaymentsCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ViewPaymentsCommand} object.
 */
public class ViewPaymentsCommandParser implements Parser<ViewPaymentsCommand> {
    @Override
    public ViewPaymentsCommand parse(String args) throws ParseException {
        String s = args.trim();
        if (s.equalsIgnoreCase("all")) {
            return ViewPaymentsCommand.forAll();
        }
        try {
            Index index = ParserUtil.parseIndex(s);
            return ViewPaymentsCommand.forIndex(index);
        } catch (ParseException pe) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewPaymentsCommand.MESSAGE_USAGE), pe);
        }
    }
}
