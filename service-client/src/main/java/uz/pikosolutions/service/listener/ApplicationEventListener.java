package uz.pikosolutions.service.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@RequiredArgsConstructor
public class ApplicationEventListener implements ApplicationListener<ApplicationReadyEvent> {

    private final Environment environment;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        log.info("\u001B[33m" + "Server port = " + environment.getProperty("server.port") + "\u001B[0m");
        try {
            InetAddress IP = InetAddress.getLocalHost();
            log.info("\u001B[33m" + "Server address = " + IP.toString() + "\u001B[0m");
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }
}