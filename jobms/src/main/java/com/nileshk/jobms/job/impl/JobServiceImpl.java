package com.nileshk.jobms.job.impl;


import com.nileshk.jobms.dto.JobWithCompanyDTO;
import com.nileshk.jobms.job.Job;
import com.nileshk.jobms.job.JobRepository;
import com.nileshk.jobms.job.JobService;
import com.nileshk.jobms.job.external.Company;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

   JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    private long nextId=1L;
    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs=jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs=new ArrayList<>();

        return jobs.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    private JobWithCompanyDTO convertToDto(Job job){
        JobWithCompanyDTO jobWithCompanyDTO=new JobWithCompanyDTO();
        jobWithCompanyDTO.setJob(job);
        RestTemplate restTemplate=new RestTemplate();
        Company company=restTemplate.getForObject("http://localhost:8081/companies/"+job.getCompanyId(),Company.class);
        jobWithCompanyDTO.setCompany(company);
        return jobWithCompanyDTO;

    }

    @Override
    public void createJob(Job job) {
        job.setId(nextId++);
        jobRepository.save(job);

    }

    @Override
    public Job getJobById(Long id) {


        return jobRepository.findById(id).orElse(null);

    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;

        }catch (Exception e){
            return false;
        }

    }

    @Override
    public boolean updateJob(Long id, Job updateJob) {
        Optional<Job> jobOptional=jobRepository.findById(id);
        if(jobOptional.isPresent()){
            Job job=jobOptional.get();
            job.setTitle(updateJob.getTitle());
            job.setDescription(updateJob.getDescription());
            job.setMinSalary(updateJob.getMinSalary());
            job.setMaxSalary(updateJob.getMaxSalary());
            job.setLocation(updateJob.getLocation());
            jobRepository.save(job);
            return true;
        }


        return false;
    }
}
