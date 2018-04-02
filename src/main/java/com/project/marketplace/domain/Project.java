package com.project.marketplace.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneOffset;


@Entity
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Basic(optional = false)
    private long sellerId;

    @Basic(optional = false)
    private long maxBudget;

    @Basic(optional = false)
    private String description;

    @Basic(optional = false)
    private Timestamp projectBiddingDeadline;

    public Project()
    {

    }

    public Project(long sellerId, String description, long maxBudget, Timestamp projectBiddingDeadline)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

        this.sellerId = sellerId;
        this.description = description + "  Project Deadline  " + sdf.format(projectBiddingDeadline).toString();
        this.maxBudget = maxBudget;
        this.projectBiddingDeadline = projectBiddingDeadline;

        System.out.println("Project added         -sellerId   -    " + sellerId + "   -Description       " +
                description + "   -MaxBudget       " + maxBudget + "   -ProjectBiddingDeadline       " +
                sdf.format(projectBiddingDeadline).toString() + " PDT \n");
    }

    public long getId()
    {
        return id;
    }

    public long getSellerId()
    {
        return sellerId;
    }

    public long getMaxBudget()
    {
        return maxBudget;
    }

    public Timestamp getProjectBiddingDeadline()
    {
        return projectBiddingDeadline;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
}
