package com.dianping.validatecode.utils;

import java.io.Serializable;

public class Pair<T, K> implements Serializable {
    private static final long serialVersionUID = -1706902574261049725L;
    private T                 tValue;
    private K                 kValue;

    public Pair() {
    }

    public Pair(T tValue, K kValue) {
        this.tValue = tValue;
        this.kValue = kValue;
    }

    public T gettValue() {
        return tValue;
    }

    public void settValue(T tValue) {
        this.tValue = tValue;
    }

    public K getkValue() {
        return kValue;
    }

    public void setkValue(K kValue) {
        this.kValue = kValue;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((kValue == null) ? 0 : kValue.hashCode());
        result = prime * result + ((tValue == null) ? 0 : tValue.hashCode());
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Pair other = (Pair) obj;
        if (kValue == null) {
            if (other.kValue != null)
                return false;
        } else if (!kValue.equals(other.kValue))
            return false;
        if (tValue == null) {
            if (other.tValue != null)
                return false;
        } else if (!tValue.equals(other.tValue))
            return false;
        return true;
    }

}