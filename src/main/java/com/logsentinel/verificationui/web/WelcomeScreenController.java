package com.logsentinel.verificationui.web;

import com.logsentinel.LogSentinelClient;
import com.logsentinel.LogSentinelClientBuilder;
import com.logsentinel.client.model.TreeHead;
import com.logsentinel.verificationui.LogSentinelClientUiApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
public class WelcomeScreenController {
    private static final Logger logger = LoggerFactory.getLogger(LogSentinelClientUiApplication.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String welcome(@RequestParam("organizationId") String organizationId,
                          @RequestParam("secret") String secret,
                          Map<String, Object> model) {
        try {
            if (organizationId != null && secret != null) {
                LogSentinelClientBuilder builder = LogSentinelClientBuilder
                        .create(null, organizationId, secret);
                builder.setBasePath("http://localhost:8080");

                LogSentinelClient client = builder.build();

                List<String> applicationsString = new ArrayList<>();
                List<UUID> applications = client.getApplicationActions().getApplications();

                for (UUID application : applications) {
                    logger.info("Error: " + application.toString());
                    applicationsString.add(application.toString());
                }

                if (applications.size() > 0) {
                    model.put("applications", applicationsString);
                    model.put("applicationId", applicationsString.get(0));

                    TreeHead treeHead = client.getVerificationActions().getLatestTreeHead(applicationsString.get(0));

                    model.put("treeSize", treeHead.getTreeSize());
                    model.put("rootHash", treeHead.getRootHash());
                }


            }
        }
        catch (Exception e){
            logger.error("Error: " + e.getMessage());
        }

        return "welcomeScreen";
    }
}
