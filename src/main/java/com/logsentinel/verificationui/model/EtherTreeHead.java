package com.logsentinel.verificationui.model;

public class EtherTreeHead {
    private String hash;
    private String timestamp;
    private String blockNumber;

    public EtherTreeHead(String hash, String timestamp, String blockNumber) {
        this.hash = hash;
        this.timestamp = timestamp;
        this.blockNumber = blockNumber;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }
}
