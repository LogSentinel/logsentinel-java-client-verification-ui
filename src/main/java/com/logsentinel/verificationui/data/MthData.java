package com.logsentinel.verificationui.data;

import com.logsentinel.ApiException;
import com.logsentinel.LogSentinelClient;
import com.logsentinel.client.model.TreeHead;

import java.util.Map;

public class MthData {
    public static void getLatestMth(LogSentinelClient client, String applicationId, Map<String, Object> model) {
        try {
            TreeHead treeHead = client.getVerificationActions().getLatestTreeHead(applicationId);

            if (treeHead.getTreeSize() > 0) {
                model.put("rootHash", treeHead.getRootHash());
            }

            model.put("treeSize", treeHead.getTreeSize());
        } catch (ApiException e) {
            model.put("error", "applicationNotFound");
        }
    }
}
