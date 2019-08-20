package com.khoros.twitter.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.khoros.twitter.models.Post;
import java.util.List;

public class Timeline {

    private List<Post> timeline;

    public Timeline() {
        // Jackson deserialization
    }

    public Timeline(List<Post> timeline) {
        this.timeline = timeline;
    }

    @JsonProperty
    public List<Post> getTimeline() {
        return timeline;
    }

}
