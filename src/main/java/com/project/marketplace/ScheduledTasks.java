package com.project.marketplace;

import com.project.marketplace.domain.Bid;
import com.project.marketplace.domain.BidRepository;
import com.project.marketplace.domain.FinishedOrder;
import com.project.marketplace.domain.FinishedOrderRepository;
import com.project.marketplace.domain.Project;
import com.project.marketplace.domain.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;

@Component
public class ScheduledTasks
{


    @Autowired
    private FinishedOrderRepository orderRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BidRepository bidRepository;

    /**
     * This is a scheduled task which will run every second.
     * It checks for any order that has reached its project deadline.
     * In case there are no bids for a certain project it still processes it with buyer id -1 and price -1.
     */
    @Transactional
    @Scheduled(fixedRate = 1000)
    public void scheduleTaskWithFixedRate()
    {
        ZoneId zone1 = ZoneId.of("America/Los_Angeles");
        LocalDateTime ldt = LocalDateTime.now(zone1);
        Timestamp now = Timestamp.valueOf(ldt);
        System.out.println("checking for orders  " + ldt);
        List<Project> projectList = projectRepository.findAllByProjectBiddingDeadlineIsLessThanEqual(now);

        for (Project project : projectList)
        {
            System.out.println("Eligible Order/Orders Found ");

            try
            {
                Bid bid = bidRepository.getFirstByProjectIdOrderByOfferPriceAsc(project.getId());
                orderRepository.save(new FinishedOrder(bid, project));
            }
            // if no bid was found create a null bid and process the Project.
            catch (NullPointerException e)
            {
                Bid bid = new Bid(project.getId(), -1, -1);
                orderRepository.save(new FinishedOrder(bid, project));
            }
            // Once processed its removed from the Project repository.
            projectRepository.removeProjectByIdEquals(project.getId());
        }

    }


}
