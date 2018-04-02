package com.project.marketplace.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface FinishedOrderRepository extends CrudRepository<FinishedOrder, Long>
{

   FinishedOrder getOrderByProjectId(long id);

   List<FinishedOrder> findAll();
}
