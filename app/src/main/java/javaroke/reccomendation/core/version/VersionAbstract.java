package javaroke.reccomendation.core.version;

public abstract class VersionAbstract<T> {
    public abstract String getVersion();

    public abstract String getVersionName();

    public abstract void process(T graph);
}
