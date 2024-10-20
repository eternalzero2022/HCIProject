package com.njuse.battlerankbackend.util;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Utility class for managing asynchronous and synchronous task execution.
 * This class provides methods to execute tasks either asynchronously using Spring's
 * @Async annotation or synchronously.
 */
@Service
public class AsyncUtil {

    @Async
    public void executeTaskAsync(Runnable task) {
        task.run();
    }

    public void executeTaskSync(Runnable task) {
        task.run();
    }

    /**
     * Executes the specified task based on the provided flag.
     *
     * @param async Whether to execute the task asynchronously.
     * @param task  The task to be executed.
     */
    public void executeTask(boolean async, Runnable task) {
        if (async) {
            executeTaskAsync(task);
        } else {
            executeTaskSync(task);
        }
    }
}
