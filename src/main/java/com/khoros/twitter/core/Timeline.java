package com.khoros.twitter.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import twitter4j.Status;
import java.util.List;

public class Timeline {

    private List<Status> timeline;

    public Timeline() {
        // Jackson deserialization
    }

    public Timeline(List<Status> timeline) {
        this.timeline = timeline;
    }

    @JsonProperty
    public List<Status> getTimeline() {
        return timeline;
    }

}
