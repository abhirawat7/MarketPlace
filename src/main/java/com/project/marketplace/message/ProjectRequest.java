package com.project.marketplace.message;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.text.ParseException;

/**
 * Encapsulates request to register an offer or a bid in the system.
 */
public class ProjectRequest
{

    @NotNull
    @JsonProperty
    private long sellerId;

    @NotNull
    @JsonProperty
    private String description;

    @NotNull
    @Min(1)
    @JsonProperty
    private long maxBudget;


    @JsonProperty
    private String projectBiddingDeadline;

    public ProjectRequest()
    {

    }

    public ProjectRequest(long sellerId, String description, long maxBudget, String projectBiddingDeadline) throws ParseException
    {
        this.sellerId = sellerId;
        this.description = description;
        this.maxBudget = maxBudget;

        this.projectBiddingDeadline = projectBiddingDeadline;
    }

    public long getSellerId()
    {
        return sellerId;
    }

    public String getDescription()
    {
        return description;
    }

    public long getMaxBudget()
    {
        return maxBudget;
    }

    public String getProjectBiddingDeadline()
    {
        return projectBiddingDeadline;
    }
}
