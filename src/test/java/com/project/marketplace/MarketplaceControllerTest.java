package com.project.marketplace;

import com.project.marketplace.domain.Project;
import com.project.marketplace.message.BidRequest;
import com.project.marketplace.message.ProjectRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MarketPlaceApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MarketplaceControllerTest
{
    @LocalServerPort
    private int port;

    private RestTemplate restTemplate = new RestTemplate();


    //a. Create a Project.
    @Test
    public void testRegisterProject() throws Exception
    {
        String baseUrl = "http://localhost:" + port + "/marketplace";

        final ProjectRequest request = new ProjectRequest(1l, "hello", 5l, "2020/03/28 23:40:10");
        final ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl + "/projects", request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    //b. Get a Project by ID. Returned fields should include the lowest bid amount.
    @Test
    public void testGetProject() throws Exception
    {
        String baseUrl = "http://localhost:" + port + "/marketplace";

        final ProjectRequest request1 = new ProjectRequest(1l, "hello", 5l, "2020/03/28 23:40:10");
        final ResponseEntity<Void> response1 = restTemplate.postForEntity(baseUrl + "/projects", request1, Void.class);

        final BidRequest request = new BidRequest(1, 1, 2);
        final ResponseEntity<Void> response2 = restTemplate.postForEntity(baseUrl + "/bids", request, Void.class);


        ResponseEntity<String> response3 = restTemplate.getForEntity(baseUrl + "/projects/{projectId}",
                String.class, request.getProjectId());


        assertEquals(HttpStatus.OK, response3.getStatusCode());
    }


    //c. API to Bid for a Project
    @Test
    public void testRegisterBid() throws Exception
    {
        String baseUrl = "http://localhost:" + port + "/marketplace";

        final ProjectRequest request1 = new ProjectRequest(1l, "hello", 5l, "2020/03/28 23:40:10");
        final ResponseEntity<Void> response1 = restTemplate.postForEntity(baseUrl + "/projects", request1, Void.class);

        final BidRequest request = new BidRequest(1, 1, 2);
        final ResponseEntity<Void> response = restTemplate.postForEntity(baseUrl + "/bids", request, Void.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    //d. API to Query for all Open Projects.
    @Test
    public void testQueryAllOpenProjects() throws Exception
    {
        String baseUrl = "http://localhost:" + port + "/marketplace";

        final ProjectRequest request = new ProjectRequest(1l, "hello", 5l, "2020/03/28 23:40:10");

        restTemplate.postForEntity(baseUrl + "/projects", request, Void.class);
        restTemplate.postForEntity(baseUrl + "/projects", request, Void.class);


        final ParameterizedTypeReference<List<Project>> responseType = new ParameterizedTypeReference<List<Project>>()
        {
        };
        final ResponseEntity<List<Project>> response = restTemplate.exchange(baseUrl + "/projects/all",
                HttpMethod.GET, null, responseType, request.getDescription());

        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
