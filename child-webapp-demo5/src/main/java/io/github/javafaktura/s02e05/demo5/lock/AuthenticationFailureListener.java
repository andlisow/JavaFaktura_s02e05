package io.github.javafaktura.s02e05.demo5.lock;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

@Component
class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {

    private final LoginAttemptsCounter loginAttemptsCounter;

    AuthenticationFailureListener(LoginAttemptsCounter loginAttemptsCounter) {
        this.loginAttemptsCounter = loginAttemptsCounter;
    }

    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent e) {
        String name = e.getAuthentication().getName();
        loginAttemptsCounter.loginFail(name);
    }
}
