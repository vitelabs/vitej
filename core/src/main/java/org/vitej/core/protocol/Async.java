package org.vitej.core.protocol;

import java.util.concurrent.*;

public class Async {
    private static final ExecutorService executor = Executors.newCachedThreadPool();

    static {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(executor)));
    }

    public static <T> CompletableFuture<T> run(Callable<T> callable) {
        CompletableFuture<T> result = new CompletableFuture<>();
        CompletableFuture.runAsync(
                () -> {
                    try {
                        result.complete(callable.call());
                    } catch (Throwable e) {
                        result.completeExceptionally(e);
                    }
                },
                executor);
        return result;
    }

    private static int getCpuCount() {
        return Runtime.getRuntime().availableProcessors();
    }


    public static ScheduledExecutorService defaultExecutorService() {
        ScheduledExecutorService scheduledExecutorService =
                Executors.newScheduledThreadPool(getCpuCount());
        Runtime.getRuntime().addShutdownHook(new Thread(() -> shutdown(scheduledExecutorService)));
        return scheduledExecutorService;
    }

    private static void shutdown(ExecutorService executorService) {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
                if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                    System.err.println("Thread pool did not terminate");
                }
            }
        } catch (InterruptedException ie) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
