package com.uitox.asapapp;

import java.util.List;

/**
 * Created by babyandy on 2015/8/24.
 */
public class GCMEntity {
    private int success, failure, canonical_ids;
    private double multicast_id;
    private List<GCMEntityResult> results;

    //{"multicast_id":6660102880917479615,"success":1,"failure":0,"canonical_ids":0,"results":[{"message_id":"0:1440393733821321%124a2af3f9fd7ecd"}]}
    //test
    public GCMEntity(List<GCMEntityResult> results, int success, int failure, int canonical_ids, double multicast_id) {
        this.results = results;
        this.success = success;
        this.failure = failure;
        this.canonical_ids = canonical_ids;
        this.multicast_id = multicast_id;
    }

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFailure() {
        return failure;
    }

    public void setFailure(int failure) {
        this.failure = failure;
    }

    public int getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(int canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

    public double getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(double multicast_id) {
        this.multicast_id = multicast_id;
    }

    public List<GCMEntityResult> getResults() {
        return results;
    }

    public void setResults(List<GCMEntityResult> results) {
        this.results = results;
    }
}
