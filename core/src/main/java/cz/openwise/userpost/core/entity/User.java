package cz.openwise.userpost.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.immutables.value.Value;

/**
 * Entity represents User in master system.
 *
 * @author <a href="mailto:filip.borovec@openwise.cz">Filip Borovec</a>
 */
@JsonSerialize(as = ImmutableUser.class)
@JsonDeserialize(as = ImmutableUser.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@Value.Immutable
public abstract class User {
    public abstract long getId();
    public abstract String getName();
    public abstract String getUsername();
    public abstract String getEmail();
}
