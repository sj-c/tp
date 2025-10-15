package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ACTIVE_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

public class UnarchiveCommand extends Command {
    public static final String COMMAND_WORD = "unarchive";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unarchives the person at the given index.\n"
            + "Parameters: INDEX\nExample: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_NOT_ARCHIVED = "This person is not archived.";
    public static final String MESSAGE_SUCCESS = "Unarchived: %1$s";

    private final Index index;

    public UnarchiveCommand(Index index) { this.index = index; }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> list = model.getFilteredPersonList();

        if (index.getZeroBased() >= list.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person p = list.get(index.getZeroBased());
        if (!p.isArchived()) {
            throw new CommandException(MESSAGE_NOT_ARCHIVED);
        }

        Person unarchived = p.withArchived(false);
        model.setPerson(p, unarchived);
        model.updateFilteredPersonList(PREDICATE_SHOW_ACTIVE_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, unarchived.getName()));
    }

    @Override
    public boolean equals(Object o) {
        return o == this || (o instanceof UnarchiveCommand && index.equals(((UnarchiveCommand) o).index));
    }
}
