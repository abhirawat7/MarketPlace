package com.project.marketplace.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Bid
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic(optional = false)
    private long projectId;

    @Basic(optional = false)
    private long buyerId;

    @Basic(optional = false)
    private long offerPrice;


    Bid()
    {
    }

    public Bid(long projectId, long buyerId, long biddingPrice)
    {
        this.projectId = projectId;
        this.buyerId = buyerId;
        this.offerPrice = biddingPrice;

        if (buyerId != -1)
        {
            System.out.println("Bid added         -BuyerId  " + buyerId + "   ProjectId   -    " + projectId + "   -BiddingPrice" + biddingPrice + "\n");
        }
    }

    public long getId()
    {
        return id;
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
}
