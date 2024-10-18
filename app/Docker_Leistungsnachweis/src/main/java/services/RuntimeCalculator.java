package services;

import controller.Dataset;
import controller.Event;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

/**
 * The {@code RuntimeCalculator} class calculates the total runtime for each customer
 * based on events in a {@code Dataset}. Events represent workloads that have start and stop times.
 * This class processes the events to compute the total time a workload has been running for each customer.
 */
public class RuntimeCalculator {

    /**
     * Calculates the total runtime for each customer based on the events in the dataset.
     * The method processes both "start" and "stop" events to calculate the time intervals.
     * If a "stop" event occurs without a corresponding "start" event, it is temporarily stored
     * and processed once a matching "start" event is found.
     *
     * @param dataset the {@code Dataset} containing the list of events to process.
     * @return a {@code Map} where the keys are customer IDs and the values are total runtimes in milliseconds.
     */
    public Map<String, Long> calculateRuntimes(Dataset dataset) {
        Map<String, Long> startTimes = new HashMap<>();
        Map<String, Long> totalRuntimeByCustomerId = new HashMap<>();
        List<Event> pendingStopEvents = new ArrayList<>();

        for (Event event : dataset.getEvents()) {
            String workloadId = event.getWorkloadId();
            String customerId = event.getCustomerId();
            long timestamp = event.getTimestamp();
            String eventType = event.getEventType();

            String key = customerId + "-" + workloadId;

            if ("start".equals(eventType)) {
                startTimes.put(key, timestamp);
                processPendingStopEvents(pendingStopEvents, startTimes, totalRuntimeByCustomerId);

            } else if ("stop".equals(eventType)) {
                Long startTime = startTimes.get(key);
                if (startTime != null) {
                    long runtime = timestamp - startTime;
                    totalRuntimeByCustomerId.merge(customerId, runtime, Long::sum);
                    startTimes.remove(key);
                } else {
                    pendingStopEvents.add(event);
                }
            }
        }

        processPendingStopEvents(pendingStopEvents, startTimes, totalRuntimeByCustomerId);

        return totalRuntimeByCustomerId;
    }

    /**
     * Processes any pending "stop" events that did not have a matching "start" event
     * at the time of processing. If a matching "start" event is found later, the runtime
     * is calculated and added to the total runtime for the customer.
     *
     * @param pendingStopEvents a list of "stop" events waiting for corresponding "start" events.
     * @param startTimes a map storing the start times of events by customer and workload ID.
     * @param totalRuntimeByCustomerId a map that stores the total runtime per customer.
     */
    private void processPendingStopEvents(List<Event> pendingStopEvents, Map<String, Long> startTimes, Map<String, Long> totalRuntimeByCustomerId) {
        for (int i = 0; i < pendingStopEvents.size(); ) {
            Event stopEvent = pendingStopEvents.get(i);
            String workloadId = stopEvent.getWorkloadId();
            String customerId = stopEvent.getCustomerId();
            long timestamp = stopEvent.getTimestamp();
            String key = customerId + "-" + workloadId;

            Long startTime = startTimes.get(key);
            if (startTime != null) {
                long runtime = timestamp - startTime;
                totalRuntimeByCustomerId.merge(customerId, runtime, Long::sum);
                startTimes.remove(key);
                pendingStopEvents.remove(i);
            } else {
                i++;
            }
        }
    }
}
