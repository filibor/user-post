package cz.openwise.userpost.core.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * Domain object of users Post.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@JsonSerialize(as = ImmutablePostDO.class)
@JsonDeserialize(as = ImmutablePostDO.class)
@Value.Immutable
public abstract class PostDO {
    public abstract long getId();
    public abstract String getTitle();
}
