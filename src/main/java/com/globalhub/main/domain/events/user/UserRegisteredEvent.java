package com.globalhub.main.domain.events.user;

import com.globalhub.main.domain.user.User;

public record UserRegisteredEvent(User user) {
}
