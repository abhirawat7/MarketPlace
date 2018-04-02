package com.project.marketplace.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;


@Entity
public class FinishedOrder
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic(optional = false)
    private long sellerId;

    @Basic(optional = false)
    private long buyerId;

    @Basic(optional = false)
    private long offerPrice;

    @Basic(optional = false)
    private long maxBudget;

    @Basic(optional = false)
    private long projectId;

    @Basic(optional = false)
    private String description;

    @Basic(optional = false)
    private Timestamp projectBiddingDeadline;

    public FinishedOrder()
    {

    }

    public FinishedOrder(Bid minBid, Project project)
    {
        this.projectId = project.getId();
        this.sellerId = project.getSellerId();
        this.maxBudget = project.getMaxBudget();
        this.projectBiddingDeadline = project.getProjectBiddingDeadline();
        this.description = project.getDescription();

        if (minBid.getBuyerId() != -1)
        {
            this.buyerId = minBid.getBuyerId();
            this.offerPrice = minBid.getOfferPrice();
            System.out.println("Order details         -buyerId " + buyerId + "   -Description    " +
                    description + "   -Offer Price       " + offerPrice

                    + "\n");
        }
        else
        {
            System.out.println("Order details        -projectId " + projectId + "    -Description  No bid found  ");
        }
    }


    public long getId()
    {
        return id;
    }

    public long getSellerId()
    {
        return sellerId;
    }

    public long getBuyerId()
    {
        return buyerId;
    }

    public long getOfferPrice()
    {
        return offerPrice;
    }

    public long getMaxBudget()
    {
        return maxBudget;
    }

    public long getProjectId()
    {
        return projectId;
    }

    public String getDescription()
    {
        return description;
    }

    public Timestamp getProjectBiddingDeadline()
    {
        return projectBiddingDeadline;
    }
}
