package com.project.marketplace;

import com.project.marketplace.message.BidRequest;
import com.project.marketplace.message.ProjectRequest;
import com.project.marketplace.service.MarketplaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/marketplace")
public class MarketplaceController
{

    @Autowired
    private MarketplaceService marketplaceService;

    /**
     * Register a Bid in the marketplace.
     *
     * @throws Exception
     */
    @RequestMapping(value = "/bids", method = RequestMethod.POST)
    ResponseEntity registerBid(@RequestBody @Valid BidRequest request) throws Exception
    {
        try
        {
            marketplaceService.registerBid(request.getProjectId(), request.getBuyerId(),
                    request.getOfferPrice());
        }
        catch (NullPointerException e)
        {
            return new ResponseEntity<>("Please bid for an existing project.", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Register a Project in the marketplace. Date & Time follows ISO 8601  YYYY-MM-DD
     */
    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    ResponseEntity registerProject(@RequestBody @Valid ProjectRequest request) throws Exception
    {
        try
        {
            marketplaceService.registerProject(request.getSellerId(), request.getDescription(), request.getMaxBudget(),
                    request.getProjectBiddingDeadline());
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Unable to register the  project.", HttpStatus.BAD_REQUEST);

        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Returns project details along with lowest bid so far. else would return the Finished order.
     *
     * @throws Exception
     */
    @RequestMapping(value = "/projects/{projectId}", method = RequestMethod.GET)
    @ResponseBody
    ResponseEntity getProject(@PathVariable long projectId) throws Exception
    {
        try
        {
            return new ResponseEntity<>(marketplaceService.getProject(projectId), HttpStatus.OK);
        }
        catch (NullPointerException e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Returns all the projects
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/projects/all", method = RequestMethod.GET)
    ResponseEntity getAllOpenProjects() throws Exception
    {
        try
        {
            return new ResponseEntity<>(marketplaceService.getAllOpenProjects(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Unable to get the  projects.", HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Return all the orders(processed projects)
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/orders/all", method = RequestMethod.GET)
    ResponseEntity getAllOrders() throws Exception
    {
        try
        {
            return new ResponseEntity<>(marketplaceService.getAllOrders(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Unable to get the  orders.", HttpStatus.BAD_REQUEST);

        }
    }

    /**
     * Return all the bids
     *
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/bids/all", method = RequestMethod.GET)
    ResponseEntity getAllBids() throws Exception
    {
        try
        {
            return new ResponseEntity<>(marketplaceService.getAllBids(), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Unable to get the  Bids.", HttpStatus.BAD_REQUEST);
        }
    }
}
