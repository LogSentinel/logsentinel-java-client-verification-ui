package com.logsentinel.verificationui.data;

import com.logsentinel.ApiException;
import com.logsentinel.LogSentinelClient;
import com.logsentinel.client.model.ConsistencyProof;
import com.logsentinel.merkletree.verification.ConsistencyProofVerifier;
import com.logsentinel.util.StringUtil;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

public class ConsistencyProofData {
    public static void getConsistencyProof(LogSentinelClient client, String hash1, String hash2, String applicationId,
                                         Map<String, Object> model) {
        try {
            ConsistencyProof consistencyProof = client.getVerificationActions().getConsistencyProof(
                    StringUtil.base64StringAddPadding(hash1), applicationId,
                    StringUtil.base64StringAddPadding(hash2));

            if (consistencyProof.getPath() != null) {

                List<byte[]> consistencyProofPath = new ArrayList<>();
                for (String pathEntry : consistencyProof.getPath()) {
                    consistencyProofPath.add(Base64.getUrlDecoder().decode(pathEntry));
                }

                // Verify the consistency between two historical Merkle Tree Heads (MTHs) or between a historical MTH and
                // the latest MTH
                Boolean consistencyProofVerificationResult = ConsistencyProofVerifier.verify(consistencyProofPath,
                        Base64.getUrlDecoder().decode(consistencyProof.getFirstHash()),
                        consistencyProof.getFirstTreeSize(),
                        Base64.getUrlDecoder().decode(consistencyProof.getSecondHash()),
                        consistencyProof.getSecondTreeSize());

                model.put("firstTreeSize", consistencyProof.getFirstTreeSize());
                model.put("firstTreeHash", consistencyProof.getFirstHash());

                model.put("secondTreeSize", consistencyProof.getSecondTreeSize());
                model.put("secondTreeHash", consistencyProof.getSecondHash());

                model.put("consistencyProofVerification", consistencyProofVerificationResult);
            }
            else {
                model.put("consistencyProofVerification", false);
                model.put("errorMessage", "MTH not found");
            }
        }
        catch (ApiException e) {
            model.put("consistencyProofVerification", false);
            model.put("errorMessage", "MTH not found");
        }
    }
}
