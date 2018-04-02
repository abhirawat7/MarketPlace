package com.project.marketplace.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface BidRepository extends CrudRepository<Bid, Long>
{
    Bid getFirstByProjectIdOrderByOfferPriceAsc(long projectId);

    List<Bid> findAll();
}
