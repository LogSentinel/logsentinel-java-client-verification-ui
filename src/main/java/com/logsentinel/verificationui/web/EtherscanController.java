package com.logsentinel.verificationui.web;

import com.logsentinel.ApiException;
import com.logsentinel.ApiResponse;
import com.logsentinel.verificationui.model.EtherTransaction;
import com.logsentinel.verificationui.model.EtherTransactionsResponse;
import com.logsentinel.verificationui.model.EtherTreeHead;
import com.logsentinel.verificationui.util.NetworkUtil;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class EtherscanController {
    @RequestMapping(value = "/ether", method = RequestMethod.GET)
    public List<EtherTreeHead> getMthsFromEthereum(@RequestParam("organizationId") String organizationId,
                                      @RequestParam("etherscanApiKey") String etherscanApiKey)
            throws ApiException, DecoderException, UnsupportedEncodingException {

        String searchPattern = Hex.encodeHexString(organizationId.getBytes()) + "3a4d45524b4c455f524f4f543a";

        ApiResponse<EtherTransactionsResponse> response = NetworkUtil.getEtherscanTransactions(etherscanApiKey);

        List<EtherTreeHead> foundTreeHeads = new ArrayList<>();

        if (response.getData().getMessage().equals("OK")) {
            List<EtherTransaction> etherTransactions = response.getData().getResult().stream()
                    .filter(item -> item.getInput().contains(searchPattern))
                    .collect(Collectors.toList());

            for (EtherTransaction transaction : etherTransactions) {
                String hash = transaction.getInput().substring(100, transaction.getInput().length() - 2);

                foundTreeHeads.add(new EtherTreeHead(new String(Hex.decodeHex(hash.toCharArray()), "UTF-8"),
                        transaction.getTimeStamp(), transaction.getBlockNumber()));
            }

            return foundTreeHeads;
        }
        else {
            return new ArrayList<>();
        }
    }
}
