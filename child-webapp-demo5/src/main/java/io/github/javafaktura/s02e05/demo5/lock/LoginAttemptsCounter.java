package io.github.javafaktura.s02e05.demo5.lock;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class LoginAttemptsCounter {

    private ConcurrentHashMap<String, Integer> failedAttemptsPerLogin = new ConcurrentHashMap<>();

    private final int maxFailedAttemptsAllowed;

    public LoginAttemptsCounter(@Value("${max.failed.attempts.count}") int maxFailedAttemptsAllowed) {
        this.maxFailedAttemptsAllowed = maxFailedAttemptsAllowed;
    }

    void loginFail(String login) {
        Integer count = failedAttemptsPerLogin.get(login);
        if (count == null) {
            failedAttemptsPerLogin.put(login, 1);
        } else {
            failedAttemptsPerLogin.put(login, ++count);
        }
    }

    void loginSuccess(String login) {
        failedAttemptsPerLogin.put(login, 0);
    }

    public boolean isBlocked(String name) {
        Integer attempts = this.failedAttemptsPerLogin.get(name);
        return attempts != null && attempts >= maxFailedAttemptsAllowed;
    }
}
