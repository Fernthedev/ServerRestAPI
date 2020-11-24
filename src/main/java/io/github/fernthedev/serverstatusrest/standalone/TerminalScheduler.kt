package io.github.fernthedev.serverstatusrest.standalone

import com.github.fernthedev.fernapi.universal.handlers.IScheduler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lombok.Getter
import lombok.RequiredArgsConstructor
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@RequiredArgsConstructor
class TerminalScheduler : IScheduler<TerminalScheduleTask, UUID> {

    @Getter
    private val timer = Timer()

    @Getter
    private val timerTaskMap: MutableMap<UUID, TerminalScheduleTask> = HashMap()

    /**
     * Schedules a task to be executed asynchronously after the specified delay
     * is up.
     *
     * @param task  the task to run
     * @param delay the delay before this task will be executed
     * @param unit  the unit in which the delay will be measured
     */
    override fun runSchedule(task: Runnable, delay: Long, unit: TimeUnit): TerminalScheduleTask? {
        val completableFuture = CompletableFuture<Void?>()

        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                task.run()
                completableFuture.complete(null)
            }
        }

        timer.schedule(timerTask, unit.toMillis(delay))
        val taskSchedule = TerminalScheduleTask(task, timerTask, completableFuture)

        timerTaskMap[taskSchedule.id] = taskSchedule
        return taskSchedule
    }

    /**
     * Schedules a task to be executed asynchronously after the specified delay
     * is up. The scheduled task will continue running at the specified
     * interval. The interval will not begin to count down until the last task
     * invocation is complete.
     *
     * @param task   the task to run
     * @param delay  the delay before this task will be executed
     * @param period the interval before subsequent executions of this task
     * @param unit   the unit in which the delay and period will be measured
     */
    override fun runSchedule(task: Runnable, delay: Long, period: Long, unit: TimeUnit): TerminalScheduleTask? {
        val completableFuture = CompletableFuture<Void?>()

        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                task.run()
                completableFuture.complete(null)
            }
        }

        timer.schedule(timerTask, unit.toMillis(delay), unit.toMillis(period))
        val taskSchedule = TerminalScheduleTask(task, timerTask, completableFuture)

        timerTaskMap[taskSchedule.id] = taskSchedule
        return taskSchedule
    }

    override fun cancelAllTasks() {
        for ((_, task) in timerTaskMap) {
            task.cancel();
        }
        timer.cancel()
        timer.purge()
    }

    override fun cancelTask(id: UUID?) {
        timerTaskMap[id]!!.cancel()
    }

    /**
     * Runs the given runnable in async
     * @param runnable the runnable
     */
    override fun runAsync(runnable: Runnable?): TerminalScheduleTask {
        val completableFuture = CompletableFuture<Void?>()
        val newTask = Runnable {
            runnable!!.run()
            completableFuture.complete(null)
        }

        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                newTask.run()
                completableFuture.complete(null)
            }
        }

        GlobalScope.launch {
            newTask.run()
        }


        val taskSchedule = TerminalScheduleTask(newTask, timerTask, completableFuture)

        timerTaskMap[taskSchedule.id] = taskSchedule
        return taskSchedule
    }

}