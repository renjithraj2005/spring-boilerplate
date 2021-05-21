package com.boilerplate.demo.job;

import com.boilerplate.demo.config.ConfigProperties;
import com.boilerplate.demo.model.user.CustomUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private ConfigProperties configProperties;

    @Autowired
    private UserDetailsService userDetails;

    @Scheduled(cron = "${scheduler.demo.cron-cet-zone}", zone = "CET")
    public void schedulerDemo() throws Exception {
        run(() -> {
            logger.info("Job running ...");
        });
    }


    private void run(TheJob theJob) throws Exception {
        try {
            loginAsAdmin();
            theJob.run();
        } finally {
            logout();
        }
    }

    private void loginAsAdmin() {
        CustomUserDetails userDetails = (CustomUserDetails) this.userDetails.loadUserByUsername(
                configProperties.getAdminId());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails,
                userDetails.getPassword(),
                userDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        logger.info("Running scheduled job as admin user");
    }

    private void logout() {
        SecurityContextHolder.getContext().setAuthentication(null);
        logger.info("Finished and logged out");
    }

    @FunctionalInterface
    private interface TheJob {
        void run() throws Exception;
    }
}
