package javaroke.recommendation.core.models.items;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MyPair<A, B> {
    public A first;
    public B second;

    // Required for Jackson
    public MyPair() {}

    @JsonCreator
    public MyPair(@JsonProperty("first") A first, @JsonProperty("second") B second) {
        this.first = first;
        this.second = second;
    }

    // public MyPair(A first, B second) {
    // this.first = first;
    // this.second = second;
    // }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof MyPair))
            return false;
        MyPair<?, ?> p = (MyPair<?, ?>) o;
        return first.equals(p.first) && second.equals(p.second);
    }

    @Override
    public int hashCode() {
        return first.hashCode() ^ second.hashCode();
    }
}

