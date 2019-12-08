package io.github.javafaktura.s02e05.demo5.lock;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
class AuthenticationSuccessListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final LoginAttemptsCounter loginAttemptsCounter;

    AuthenticationSuccessListener(LoginAttemptsCounter loginAttemptsCounter) {
        this.loginAttemptsCounter = loginAttemptsCounter;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent authenticationSuccessEvent) {
        String name = authenticationSuccessEvent.getAuthentication().getName();
        loginAttemptsCounter.loginSuccess(name);
    }
}
