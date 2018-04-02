package com.project.marketplace.service;

import com.project.marketplace.domain.Bid;
import com.project.marketplace.domain.FinishedOrder;
import com.project.marketplace.domain.Project;

import java.util.HashMap;
import java.util.List;

public interface MarketplaceService
{

    /**
     * This function creates a new project which would be available for bidding.
     *
     * @param sellerId
     * @param description
     * @param maxBudget
     * @param projectBiddingDeadline
     * @throws Exception
     */
    void registerProject(long sellerId, String description, long maxBudget, String projectBiddingDeadline) throws Exception;

    /**
     * This function registers bid for a particular project throws exception if Project does not exists or has reached deadline.
     *
     * @param projectId
     * @param buyerId
     * @param offerPrice
     * @throws Exception
     */
    void registerBid(long projectId, long buyerId, long offerPrice) throws Exception;

    /**
     * This function returns Project details along with lowest bid for the given project Id
     *
     * @param projectId
     * @return
     * @throws Exception
     */
    Bid getLowestBid(long projectId) throws Exception;

    /**
     * This function returns project using project id.
     *
     * @param projectId
     * @return
     * @throws Exception
     */
    HashMap<String,String> getProject(long projectId) throws Exception;


    /**
     * This function returns all open projects
     *
     * @return
     * @throws Exception
     */
    List<Project> getAllOpenProjects() throws Exception;

    /**
     * This function returns all processed projects /orders
     *
     * @return
     * @throws Exception
     */
    List<FinishedOrder> getAllOrders() throws Exception;

    /**
     * This function returns all bids.
     *
     * @return
     * @throws Exception
     */
    List<Bid> getAllBids() throws Exception;
}
