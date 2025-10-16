package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindMemberCommand;


public class FindMemberCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMemberCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        assertDoesNotThrow(() -> {
            FindMemberCommand cmd1 = parser.parse("Alice Bob");
            assertTrue(cmd1 instanceof FindMemberCommand);
        });

        assertDoesNotThrow(() -> {
            FindMemberCommand cmd2 = parser.parse(" \n Alice \n \t Bob  \t");
            assertTrue(cmd2 instanceof FindMemberCommand);
        });
    }
}
