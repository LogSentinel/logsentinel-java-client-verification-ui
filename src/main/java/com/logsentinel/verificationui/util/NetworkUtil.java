package com.logsentinel.verificationui.util;

import com.google.gson.reflect.TypeToken;
import com.logsentinel.ApiClient;
import com.logsentinel.ApiException;
import com.logsentinel.ApiResponse;
import com.logsentinel.verificationui.model.EtherTransactionsResponse;
import com.squareup.okhttp.Call;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NetworkUtil {
    private static final String etherAddress = "0xdc1e2824e04341d4B387c607fFfF52Bb2887183E";

    public static ApiResponse<EtherTransactionsResponse> getEtherscanTransactions(String etherscanApiKey)
            throws ApiException {
        String apiUrl = "api?module=account&action=txlist&address=" + etherAddress +
                "&startblock=0&endblock=99999999&sort=desc&" +
                "apikey=" + etherscanApiKey;

        Map<String, String> localVarHeaderParams = new HashMap<>();

        localVarHeaderParams.put("Accept", "application/json");

        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath("http://api-ropsten.etherscan.io/");
        Call etherCall = apiClient.buildCall(apiUrl, "GET", new ArrayList<>(), null,
                localVarHeaderParams, new HashMap<>(), new String[0], null);

        Type localVarReturnType = new TypeToken<EtherTransactionsResponse>(){}.getType();

        return apiClient.execute(etherCall, localVarReturnType);
    }
}
