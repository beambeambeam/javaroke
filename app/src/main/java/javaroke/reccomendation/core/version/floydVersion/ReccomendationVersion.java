package javaroke.reccomendation.core.version.floydVersion;

import java.util.Queue;
import javafx.util.Pair;

public abstract class ReccomendationVersion<T> {
    public abstract String getVersion();

    public abstract String getVersionName();

    public abstract void process(T graph);

    public abstract void shortUpdate(T graph, Queue<Pair<String, String>> queue);

}
