package com.khoros.twitter;

import twitter4j.Status;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TwitterLabCache {

    private List<Status> cache;
    private Date lastUpdated;

    private final long TIME_TO_LIVE = 60000; // in milliseconds = 1 min

    public TwitterLabCache() {
        this.cache = new ArrayList<Status>();
        this.lastUpdated = new Date(0L);
    }

    public boolean add(Status status) {
        if (status == null) {
            return false;
        }
        return cache.add(status);
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

    public void updateCache(List<Status> statuses) {
        if (statuses == null) {
            return;
        }
        cache.clear();
        cache.addAll(statuses);
        lastUpdated = new Date();
    }

    public boolean canUpdateCache() {
        return (new Date().getTime() - lastUpdated.getTime()) > TIME_TO_LIVE;
    }

}
