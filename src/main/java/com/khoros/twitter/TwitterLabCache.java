package com.khoros.twitter;

import twitter4j.Status;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TwitterLabCache {

    private List cache;
    private Date lastUpdated;

    private final long TIME_TO_LIVE = 60000; // in milliseconds = 1 min

    public TwitterLabCache() {
        this.cache = new ArrayList();
        this.lastUpdated = new Date(0L);
    }

    public boolean add(Object o) {
        if (o == null) {
            return false;
        }
        return cache.add(o);
    }

    public void removeAtIndex(int index) {
        cache.remove(index);
    }

    public void clear() {
        cache.clear();
    }

    public int size() {
        return cache.size();
    }

    public List<Status> getCache() {
        return cache;
    }

    public void updateCache(List list) {
        if (list == null) {
            return;
        }
        cache.clear();
        cache.addAll(list);
        lastUpdated = new Date();
    }

    public boolean canUpdateCache() {
        return (new Date().getTime() - lastUpdated.getTime()) > TIME_TO_LIVE;
    }

}
