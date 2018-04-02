package com.project.marketplace.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Encapsulates request to register an offer or a bid in the system.
 */
public class BidRequest
{

    @NotNull
    @JsonProperty
    private long projectId;

    @NotNull
    @Min(1)
    @JsonProperty
    private long buyerId;

    @NotNull
    @Min(0)
    @JsonProperty
    private long offerPrice;

    public BidRequest()
    {
    }

    public long getProjectId()
    {
        return projectId;
    }

    public long getBuyerId()
    {
        return buyerId;
    }

    public long getOfferPrice()
    {
        return offerPrice;
    }

    public BidRequest( long projectId, long buyerId, long offerPrice)
    {
        this.projectId = projectId;
        this.buyerId = buyerId;
        this.offerPrice = offerPrice;
    }


}
