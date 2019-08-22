package com.khoros.twitter;

import com.khoros.twitter.resources.TwitterLabResource;
import dagger.Component;

@Component (modules = TwitterLabModule.class)
public interface TwitterLabComponent {

    TwitterLabResource buildTwitterLabResource();

}
