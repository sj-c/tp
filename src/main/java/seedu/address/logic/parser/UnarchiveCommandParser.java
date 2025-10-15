package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnarchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new UnarchiveCommand object.
 * Supports multiple comma-separated indexes (e.g., "unarchive 1,2,3").
 */
public class UnarchiveCommandParser implements Parser<UnarchiveCommand> {

    @Override
    public UnarchiveCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE));
            }

            String[] tokens = trimmedArgs.split(",");
            List<Index> indexes = new ArrayList<>();

            for (String token : tokens) {
                if (!token.isBlank()) {
                    indexes.add(ParserUtil.parseIndex(token.trim()));
                }
            }

            if (indexes.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE));
            }

            return new UnarchiveCommand(indexes);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnarchiveCommand.MESSAGE_USAGE), pe);
        }
    }
}
