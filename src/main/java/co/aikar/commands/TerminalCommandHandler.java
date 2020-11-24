package co.aikar.commands;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TerminalCommandHandler extends CommandManager {
    @Override
    public CommandContexts<?> getCommandContexts() {
        return new CommandContexts<>(this);
    }

    @Override
    public CommandCompletions<?> getCommandCompletions() {
        CommandCompletions<CommandCompletionContext> completions = new CommandCompletions<>(this);
        completions.registerCompletion("players", (CommandCompletions.AsyncCommandCompletionHandler<CommandCompletionContext>) context -> Collections.EMPTY_LIST);
        return completions;
    }

    @Override
    public void registerCommand(BaseCommand command) {

    }

    @Override
    public boolean hasRegisteredCommands() {
        return false;
    }

    @Override
    public CommandIssuer getCommandIssuer(Object issuer) {
        return null;
    }

    @Override
    public RootCommand createRootCommand(String cmd) {
        return null;
    }

    @Override
    public Locales getLocales() {
        return new Locales(this);
    }

    @Override
    public CommandCompletionContext createCompletionContext(RegisteredCommand command, CommandIssuer sender, String input, String config, String[] args) {
        return null;
    }

    @Override
    public void log(LogLevel level, String message, Throwable throwable) {

    }

    @Override
    public Collection<RootCommand> getRegisteredRootCommands() {
        return null;
    }

    @Override
    public CommandExecutionContext createCommandContext(RegisteredCommand command, CommandParameter parameter, CommandIssuer sender, List args, int i, Map passedArgs) {
        return null;
    }

    @Override
    public boolean isCommandIssuer(Class type) {
        return false;
    }
}
