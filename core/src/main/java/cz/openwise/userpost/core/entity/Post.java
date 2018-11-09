package cz.openwise.userpost.core.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * Entity represents post in master system.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@JsonSerialize(as = ImmutablePost.class)
@JsonDeserialize(as = ImmutablePost.class)
@Value.Immutable
public abstract class Post {
    public abstract long getId();
    public abstract long getUserId();
    public abstract String getTitle();
    public abstract String getBody();
}
