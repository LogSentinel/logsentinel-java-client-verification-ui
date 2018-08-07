package com.logsentinel.verificationui.web;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.logsentinel.ApiException;
import com.logsentinel.ApiResponse;
import com.logsentinel.verificationui.model.EtherTransaction;
import com.logsentinel.verificationui.model.EtherTransactionsResponse;
import com.logsentinel.verificationui.model.EtherTreeHead;
import com.logsentinel.verificationui.util.NetworkUtil;

@RestController
public class EtherscanController {
    
    private static final String MERKLE_ROOT_PREFIX_ENCODED = Hex.encodeHexString(":MERKLE_ROOT:".getBytes(StandardCharsets.UTF_8));
    
    @RequestMapping(value = "/ether", method = RequestMethod.GET)
    public List<EtherTreeHead> getMthsFromEthereum(@RequestParam("applicationId") String applicationId,
                                      @RequestParam("etherscanApiKey") String etherscanApiKey)
            throws ApiException, DecoderException, UnsupportedEncodingException {

        String searchPattern = Hex.encodeHexString(applicationId.getBytes()) + MERKLE_ROOT_PREFIX_ENCODED;

        ApiResponse<EtherTransactionsResponse> response = NetworkUtil.getEtherscanTransactions(etherscanApiKey);

        List<EtherTreeHead> foundTreeHeads = new ArrayList<>();

        if (response.getData().getMessage().equals("OK")) {
            List<EtherTransaction> etherTransactions = response.getData().getResult().stream()
                    .filter(item -> item.getInput().contains(searchPattern))
                    .collect(Collectors.toList());

            for (EtherTransaction transaction : etherTransactions) {
                String hash = transaction.getInput().substring(100, transaction.getInput().length() - 2);

                EtherTreeHead head = new EtherTreeHead(new String(Hex.decodeHex(hash.toCharArray()), "UTF-8"),
                        transaction.getTimeStamp(), transaction.getBlockNumber());
                if (!foundTreeHeads.contains(head)) {
                    foundTreeHeads.add(head);
                }
            }

            return foundTreeHeads;
        }
        else {
            return new ArrayList<>();
        }
    }
}
