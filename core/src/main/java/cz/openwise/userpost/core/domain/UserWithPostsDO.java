package cz.openwise.userpost.core.domain;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * Domain object of User and his posts.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@JsonSerialize(as = ImmutableUserWithPostsDO.class)
@JsonDeserialize(as = ImmutableUserWithPostsDO.class)
@Value.Immutable
public abstract class UserWithPostsDO {
    public abstract String getName();
    public abstract String getUsername();
    public abstract String getEmail();
    public abstract List<ImmutablePostDO> getPosts();
}
