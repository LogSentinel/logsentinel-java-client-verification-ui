package com.logsentinel.verificationui.data;

import com.logsentinel.ApiException;
import com.logsentinel.LogSentinelClient;
import com.logsentinel.client.model.AuditLogEntry;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.*;

public class HashChainData {
    public static void verifyHashChain(LogSentinelClient client, String startHash, String endHash, String applicationId,
                                       Map<String, Object> model) {
        try {
            List<AuditLogEntry> logEntries = client.getVerificationActions().getEntriesBetweenHashes(endHash,
                    startHash, applicationId);

            // Perform a verification of the hash chain using the retrieved entries
            boolean logChainingVerificationError = false;

            for (int i = 0; i <= logEntries.size() - 1; i++) {
                String prevEntryHash = i > 0 ? logEntries.get(i - 1).getHash() : "";

                // Encode the given audit log entry using a specific format
                String expectedEntryStandaloneData = logEntries.get(i).getHashableContent();

                // Calculate the expected standalone hash of the given audit log entry (and not the hash that includes
                // the hash of the previuos audit log entry)
                String expectedEntryStandaloneHash = Base64.getUrlEncoder().encodeToString(
                        DigestUtils.sha512(expectedEntryStandaloneData));

                // Calculate the expected hash of the given audit log entry as part of the hash chain
                String expectedEntryHash = Base64.getUrlEncoder().encodeToString(DigestUtils.sha512(
                        expectedEntryStandaloneData + prevEntryHash));

                // Retrieve the standalone hash of the given audit log entry from the server
                String entryStandaloneHash = client.getHashActions().getHash(applicationId,
                        UUID.fromString(logEntries.get(i).getId()));

                // The calculated expected hash of the given audit log entry should match the retrieved one
                if (i > 0 && !expectedEntryHash.equals(logEntries.get(i).getHash())) {
                    logChainingVerificationError = true;
                }
            }

            model.put("hashChainVerification", !logChainingVerificationError);
        }
        catch (ApiException e) {
            model.put("hashChainVerification", false);
            model.put("errorMessage", "Hashes not found");
        }
    }
}
