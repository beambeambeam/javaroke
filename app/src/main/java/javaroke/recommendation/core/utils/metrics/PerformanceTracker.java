package javaroke.recommendation.core.utils.metrics;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Tracks and reports performance metrics for graph operations.
 */
public class PerformanceTracker {
    private static final Logger LOGGER = Logger.getLogger(PerformanceTracker.class.getName());

    private final String versionName;
    private final Map<MetricType, List<MetricEntry>> metrics;

    public enum MetricType {
        LOAD, UPDATE, PROCESS, GET_RECCOMMEND
    }

    public PerformanceTracker(String versionName) {
        this.versionName = versionName;
        this.metrics = new HashMap<>();
        for (MetricType type : MetricType.values()) {
            metrics.put(type, new ArrayList<>());
        }
        LOGGER.info("Created performance tracker for version " + versionName);
    }

    public void recordLoadTime(long timeMs) {
        metrics.get(MetricType.LOAD).add(new MetricEntry(timeMs, 1));
        LOGGER.fine("Recorded load time: " + timeMs + "ms");
    }

    public void recordUpdateTime(long timeMs, int updateCount) {
        metrics.get(MetricType.UPDATE).add(new MetricEntry(timeMs, updateCount));
        LOGGER.fine("Recorded update time: " + timeMs + "ms for " + updateCount + " updates");
    }

    public void recordProcessTime(long timeMs) {
        metrics.get(MetricType.PROCESS).add(new MetricEntry(timeMs, 1));
        LOGGER.fine("Recorded process time: " + timeMs + "ms");
    }

    public void recordGetReccommendList(long timeMs) {
        metrics.get(MetricType.GET_RECCOMMEND).add(new MetricEntry(timeMs, 1));
        LOGGER.fine("Recorded get recomend list time: " + timeMs + "ms ");
    }

    public Map<String, Object> getMetricSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("versionName", versionName);

        for (Map.Entry<MetricType, List<MetricEntry>> entry : metrics.entrySet()) {
            MetricType type = entry.getKey();
            List<MetricEntry> entries = entry.getValue();

            if (entries.isEmpty()) {
                continue;
            }

            // Calculate time statistics
            DoubleSummaryStatistics timeStats =
                    entries.stream().mapToDouble(MetricEntry::getTimeMs).summaryStatistics();

            // Calculate count statistics if applicable
            DoubleSummaryStatistics countStats =
                    entries.stream().mapToDouble(MetricEntry::getCount).summaryStatistics();

            // Calculate time per item where applicable
            List<Double> timePerItem = new ArrayList<>();
            for (MetricEntry metric : entries) {
                if (metric.getCount() > 0) {
                    timePerItem.add(metric.getTimeMs() / (double) metric.getCount());
                }
            }

            Map<String, Object> typeStats = new HashMap<>();
            typeStats.put("count", entries.size());
            typeStats.put("totalTimeMs", timeStats.getSum());
            typeStats.put("avgTimeMs", timeStats.getAverage());
            typeStats.put("minTimeMs", timeStats.getMin());
            typeStats.put("maxTimeMs", timeStats.getMax());

            if (type == MetricType.UPDATE || type == MetricType.GET_RECCOMMEND) {
                typeStats.put("totalItems", countStats.getSum());
                typeStats.put("avgItems", countStats.getAverage());

                if (!timePerItem.isEmpty()) {
                    double avgTimePerItem = timePerItem.stream().mapToDouble(Double::doubleValue)
                            .average().orElse(0);
                    typeStats.put("avgTimePerItemMs", avgTimePerItem);
                }
            }

            summary.put(type.name().toLowerCase(), typeStats);
        }

        return summary;
    }

    public String getVersionName() {
        return versionName;
    }

    /**
     * Internal class to store metric entries
     */
    private static class MetricEntry {
        private final long timeMs;
        private final int count;

        public MetricEntry(long timeMs, int count) {
            this.timeMs = timeMs;
            this.count = count;
        }

        public long getTimeMs() {
            return timeMs;
        }

        public int getCount() {
            return count;
        }
    }
}
