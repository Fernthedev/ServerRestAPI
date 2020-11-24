package io.github.fernthedev.serverstatusrest.standalone;

import com.github.fernthedev.fernapi.universal.data.ScheduleTaskWrapper;

import java.util.TimerTask;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class TerminalScheduleTask extends ScheduleTaskWrapper<TimerTask, UUID> {
    private final UUID uuid;
    private final Runnable runnable;

    public TerminalScheduleTask(Runnable runnable, TimerTask task, CompletableFuture<Void> completableFuture) {
        super(task, completableFuture);
        this.uuid = UUID.randomUUID();
        this.runnable = runnable;
    }

    /**
     * Gets the unique ID of this task.
     *
     * @return this tasks ID
     */
    @Override
    public UUID getId() {
        return uuid;
    }

    /**
     * Get the actual method which will be executed by this task.
     *
     * @return the {@link Runnable} behind this task
     */
    @Override
    public Runnable getTask() {
        return runnable;
    }

    /**
     * Cancel this task to suppress subsequent executions.
     */
    @Override
    public void cancel() {
        scheduleTask.cancel();
    }


}
