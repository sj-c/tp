package seedu.address.logic.parser;

import seedu.address.logic.commands.ListArchivedCommand;

/** Parses input and returns a ListArchivedCommand (no args expected). */
public class ListArchivedCommandParser implements Parser<ListArchivedCommand> {
    @Override
    public ListArchivedCommand parse(String args) {
        // No arguments; ignore whitespace
        return new ListArchivedCommand();
    }
}
