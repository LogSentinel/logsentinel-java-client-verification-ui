package com.logsentinel.verificationui.web;

import com.logsentinel.ApiException;
import com.logsentinel.LogSentinelClient;
import com.logsentinel.LogSentinelClientBuilder;
import com.logsentinel.verificationui.LogSentinelClientUiApplication;
import com.logsentinel.verificationui.data.ConsistencyProofData;
import com.logsentinel.verificationui.data.InclusionProofData;
import com.logsentinel.verificationui.data.MthData;
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
                          @RequestParam("hash") Optional<String> hash,
                          @RequestParam("mth") Optional<String> mth,
                          Map<String, Object> model) {
        try {
            model.put("inclusionProof", false);
            model.put("consistencyProof", false);
            model.put("leafIndex", -1);

            if (!organizationId.isPresent() || !secret.isPresent()) {
                model.put("signedIn", false);
                model.put("authorized", false);
            }
            else {
                model.put("signedIn", true);

                model.put("organizationId", organizationId.get());
                model.put("secret", secret.get());

                Boolean authorized = false;

                etherscanApiKey.ifPresent(s -> model.put("etherscanApiKey", s));

                LogSentinelClientBuilder builder = LogSentinelClientBuilder
                        .create(null, organizationId.get(), secret.get());
                builder.setBasePath("http://localhost:8080");

                LogSentinelClient client = builder.build();

                List<UUID> applications = new ArrayList<>();

                try {
                    applications = client.getApplicationActions().getApplications();
                    authorized = true;
                } catch (ApiException e) {
                    if (e.getCode() == 401) {
                        model.put("wrongCredentials", true);
                    }
                }

                model.put("authorized", authorized);

                if (authorized) {
                    String selectedApplicationId = "";

                    if (applications.size() > 0) {
                        model.put("applications", applications);
                    }

                    if (applicationId.isPresent()) {
                        selectedApplicationId = applicationId.get();
                    } else {
                        if (applications.size() > 0) {
                            selectedApplicationId = applications.get(0).toString();
                        }
                    }

                    model.put("applicationId", selectedApplicationId);

                    if (!selectedApplicationId.equals("")) {
                        MthData.getLatestMth(client, selectedApplicationId, model);

                        if (hash.isPresent()) {
                            if (hash.get().length() > 0) {
                                model.replace("inclusionProof", true);

                                InclusionProofData.getInclusionProof(client, hash.get(), selectedApplicationId, model);
                            }
                        }

                        if (mth.isPresent()) {
                            if (mth.get().length() > 0) {
                                model.replace("consistencyProof", true);

                                ConsistencyProofData.getConsistencyProof(client, mth.get(),
                                        "xOiKaHY62bvYLgn1csGuph5UQprE8UVBboZsvKjS6YU",
                                        selectedApplicationId, model);
                            }
                        }
                    }
                }
            }
        }
        catch (Exception e){
            if (e.getMessage() != null) {
                if (e.getMessage().equals("Unauthorized")) {
                    model.put("authorizedReason", "wrongCredentials");
                }
            }

            model.put("signedIn", false);
            model.put("authorized", false);

            model.put("inclusionProof", false);
            model.put("consistencyProof", false);
            model.put("leafIndex", -1);
        }

        return "welcomeScreen";
    }
}
