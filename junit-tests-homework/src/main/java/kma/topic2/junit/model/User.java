package kma.topic2.junit.model;

import lombok.Builder;
import lombok.Value;

@Value(staticConstructor = "of")
@Builder
public class User {
    String login;
    String password;
    String fullName;
}
