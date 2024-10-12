package com.nileshk.jobms.dto;

import com.nileshk.jobms.job.Job;
import com.nileshk.jobms.job.external.Company;

public class JobWithCompanyDTO {
    private Job job;
    private Company company;
    public Job getJob(){
        return job;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public void setJob(Job job){
        this.job=job;
    }

}
