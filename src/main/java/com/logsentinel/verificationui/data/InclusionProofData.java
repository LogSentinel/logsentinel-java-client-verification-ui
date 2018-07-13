package com.logsentinel.verificationui.data;

import com.logsentinel.ApiException;
import com.logsentinel.LogSentinelClient;
import com.logsentinel.client.model.AuditLogEntry;
import com.logsentinel.client.model.InclusionProof;
import com.logsentinel.merkletree.utils.ArrayUtils;
import com.logsentinel.merkletree.utils.CryptoUtils;
import com.logsentinel.merkletree.verification.InclusionProofVerifier;
import com.logsentinel.util.StringUtil;

import java.util.*;

public class InclusionProofData {
    public static void getInclusionProof(LogSentinelClient client, String inclusionProofInput, String applicationId,
                                         Map<String, Object> model) {
        try {
            int inclusionProofInputType;

            switch(inclusionProofInput.length()) {
                case 36:
                    inclusionProofInputType = 1;
                    break;
                case 88:
                    inclusionProofInputType = 2;
                    break;
                default:
                    inclusionProofInputType = 3;
                    break;
            }

            String standaloneHash = "";

            if (inclusionProofInputType == 1) {
                standaloneHash  = client.getHashActions().getHash(applicationId, UUID.fromString(inclusionProofInput));

                model.put("entryId", inclusionProofInput);
            } else if (inclusionProofInputType == 2) {
                List<AuditLogEntry> logEntries = client.getVerificationActions().getEntriesBetweenHashes(
                        inclusionProofInput, inclusionProofInput, applicationId);

                if (logEntries.size() > 0) {
                    standaloneHash = client.getHashActions().getHash(applicationId,
                            UUID.fromString(logEntries.get(0).getId()));

                    model.put("entryId", logEntries.get(0).getId());
                    model.put("entryHash", logEntries.get(0).getHash());
                }
            } else if (inclusionProofInputType == 3) {
                List<AuditLogEntry> logEntries = client.getVerificationActions().getEntriesBetweenHashes(
                        StringUtil.base64StringAddPadding(inclusionProofInput),
                        StringUtil.base64StringAddPadding(inclusionProofInput), applicationId);

                if (logEntries.size() > 0) {
                    standaloneHash = client.getHashActions().getHash(applicationId,
                            UUID.fromString(logEntries.get(0).getId()));

                    model.put("entryId", logEntries.get(0).getId());
                    model.put("entryHash", logEntries.get(0).getHash());
                }
            }

            model.put("standaloneHash", standaloneHash);

            InclusionProof inclusionProof = client.getVerificationActions().getInclusionProof(standaloneHash,
                    applicationId);

            model.put("inclusionProofPath", inclusionProof.getPath());
            model.replace("leafIndex", inclusionProof.getIndex());

            List<byte[]> inclusionProofPath = new ArrayList<>();
            for (String pathEntry : inclusionProof.getPath()) {
                inclusionProofPath.add(Base64.getUrlDecoder().decode(pathEntry));
            }

            // Compute the Merkle leaf hash of the given audit log entry
            int index = inclusionProof.getIndex();
            byte[] hashToVerify = CryptoUtils.hash(ArrayUtils.addByteToArray(
                    Base64.getUrlDecoder().decode(standaloneHash),
                    (byte)0x00));

            // Verify the inclusion of the given audit log entry using the retrieved Merkle inclusion proof data and
            // MTH
            Boolean inclusionProofVerificationResult = InclusionProofVerifier.verify(inclusionProofPath,
                    hashToVerify, index, inclusionProof.getTreeSize(),
                    Base64.getUrlDecoder().decode(inclusionProof.getRootHash()));

            model.put("inclusionProofVerification", inclusionProofVerificationResult);

            if (!inclusionProofVerificationResult) {
                model.put("error", "Provided inclusion proof path is not valid for the specified audit log entry.");
            }

        } catch (ApiException e) {
            model.put("leafIndex", -1);
            model.put("error", "Audit log entry not found");
            model.put("inclusionProofVerification", false);
        }
    }
}
