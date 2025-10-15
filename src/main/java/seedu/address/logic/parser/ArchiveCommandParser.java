package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ArchiveCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ArchiveCommandParser implements Parser<ArchiveCommand> {
    @Override
    public ArchiveCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();
            if (trimmedArgs.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));
            }

            String[] tokens = trimmedArgs.split(",");
            List<Index> indexes = new ArrayList<>();

            for (String token : tokens) {
                if (!token.isBlank()) {
                    indexes.add(ParserUtil.parseIndex(token.trim()));
                }
            }

            if (indexes.isEmpty()) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE));
            }

            return new ArchiveCommand(indexes);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ArchiveCommand.MESSAGE_USAGE), pe);
        }
    }
}
