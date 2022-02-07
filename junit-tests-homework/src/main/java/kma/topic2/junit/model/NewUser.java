package kma.topic2.junit.model;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class NewUser {
    String login;
    String password;
    String fullName;
}
