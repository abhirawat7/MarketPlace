package com.project.marketplace.service;

import com.project.marketplace.domain.Bid;
import com.project.marketplace.domain.BidRepository;
import com.project.marketplace.domain.FinishedOrder;
import com.project.marketplace.domain.FinishedOrderRepository;
import com.project.marketplace.domain.Project;
import com.project.marketplace.domain.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


@Service
public class MarketplaceServiceImpl implements MarketplaceService
{

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BidRepository bidRepository;

    @Autowired
    private FinishedOrderRepository orderRepository;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerProject(long sellerId, String description, long maxBudget, String projectBiddingDeadline) throws Exception
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = sdf.parse(projectBiddingDeadline);
        long millis = date.getTime();

        Timestamp timestamp = new Timestamp(millis);

        projectRepository.save(new Project(sellerId, description, maxBudget, timestamp));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void registerBid(long projectId, long buyerId, long offerPrice) throws Exception
    {
        if (projectRepository.findProjectById(projectId) != null)
        {
            bidRepository.save(new Bid(projectId, buyerId, offerPrice));
        }
        else
        {
            throw new NullPointerException("Project does not exist.");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HashMap<String, String> getProject(long projectId) throws Exception
    {
        HashMap<String, String> projectDetails = new HashMap<>();

        Project project = projectRepository.findProjectById(projectId);
        if (project != null)
        {
            projectDetails.put("projectId", String.valueOf(project.getId()));
            projectDetails.put("sellerId", String.valueOf(project.getSellerId()));
            projectDetails.put("maxBudget", String.valueOf(project.getMaxBudget()));
            projectDetails.put("projectBiddingDeadline", String.valueOf(project.getProjectBiddingDeadline()));
            projectDetails.put("description", String.valueOf(project.getDescription()));
        }
        else
        {
            try
            {
                FinishedOrder order = orderRepository.getOrderByProjectId(projectId);

                projectDetails.put("projectId", String.valueOf(order.getProjectId()));
                projectDetails.put("sellerId", String.valueOf(order.getSellerId()));
                projectDetails.put("maxBudget", String.valueOf(order.getMaxBudget()));
                projectDetails.put("projectBiddingDeadline", String.valueOf(order.getProjectBiddingDeadline()));
                projectDetails.put("description", String.valueOf(order.getDescription()));
            }
            catch (NullPointerException e)
            {
                throw new NullPointerException("No project found");
            }
        }

        Bid bid = bidRepository.getFirstByProjectIdOrderByOfferPriceAsc(projectId);
        if (bid != null)
        {
            projectDetails.put("buyerId", String.valueOf(bid.getBuyerId()));
            projectDetails.put("offerPrice", String.valueOf(bid.getOfferPrice()));
        }
        else
        {
            projectDetails.put("buyerId", null);
            projectDetails.put("offerPrice", null);
        }
        return projectDetails;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Bid getLowestBid(long projectId) throws Exception
    {
        return bidRepository.getFirstByProjectIdOrderByOfferPriceAsc(projectId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Project> getAllOpenProjects() throws Exception
    {
        return projectRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<Bid> getAllBids() throws Exception
    {
        return bidRepository.findAll();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<FinishedOrder> getAllOrders() throws Exception
    {
        return orderRepository.findAll();
    }
}
