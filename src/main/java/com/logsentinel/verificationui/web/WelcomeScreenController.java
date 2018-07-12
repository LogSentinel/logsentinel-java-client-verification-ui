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
    public String welcome(@RequestParam("organizationId") Optional<String> organizationId,
                          @RequestParam("secret") Optional<String> secret,
                          @RequestParam("applicationId") Optional<String> applicationId,
                          @RequestParam("etherscanApiKey") Optional<String> etherscanApiKey,
                          Map<String, Object> model) {
        try {
            if (organizationId.isPresent() && secret.isPresent()) {
                model.put("organizationId", organizationId.get());
                model.put("secret", secret.get());

                if (etherscanApiKey.isPresent()) {
                    model.put("etherscanApiKey", etherscanApiKey.get());
                    model.put("etherScanApiKeyCheckbox", true);
                }

                LogSentinelClientBuilder builder = LogSentinelClientBuilder
                        .create(null, organizationId.get(), secret.get());
                builder.setBasePath("http://localhost:8080");

                LogSentinelClient client = builder.build();

                List<String> applicationsString = new ArrayList<>();
                List<UUID> applications = client.getApplicationActions().getApplications();

                for (UUID application : applications) {
                    applicationsString.add(application.toString());
                }

                if (applications.size() > 0) {
                    model.put("applications", applicationsString);
                }

                if (applicationId.isPresent()) {
                    model.put("applicationId", applicationId.get());
                    TreeHead treeHead = client.getVerificationActions().getLatestTreeHead(applicationId.get());

                    model.put("treeSize", treeHead.getTreeSize());

                    if (treeHead.getTreeSize() > 0) {
                        model.put("rootHash", treeHead.getRootHash());
                    }
                    else {
                        model.put("rootHash", "N/A");
                    }

                }
                else if (applications.size() > 0) {
                    TreeHead treeHead = client.getVerificationActions().getLatestTreeHead(applicationsString.get(0));

                    if (treeHead.getTreeSize() > 0) {
                        model.put("rootHash", treeHead.getRootHash());
                    }
                    else {
                        model.put("rootHash", "N/A");
                    }
                }

                model.put("authorized", true);
            }
            else {
                model.put("authorized", false);
            }
        }
        catch (Exception e){
            if (e.getMessage() != null) {
                if (e.getMessage().equals("Unauthorized")) {
                    model.put("authorizedReason", "wrongCredentials");
                }
            }

            model.put("authorized", false);
        }

        return "welcomeScreen";
    }
}
