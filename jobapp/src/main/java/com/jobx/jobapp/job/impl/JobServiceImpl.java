package com.jobx.jobapp.job.impl;

import com.jobx.jobapp.job.Job;
import com.jobx.jobapp.job.JobRepository;
import com.jobx.jobapp.job.JobService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {
   //data in array list now we will use hpa methods
   // private List<Job> jobs=new ArrayList<>();
   JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    private long nextId=1L;
    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
    }

    @Override
    public void createJob(Job job) {
        job.setId(nextId++);
        jobRepository.save(job);

    }

    @Override
    public Job getJobById(Long id) {

//        for(Job job:jobs){
//            if(job.getId().equals(id)){
//                return job;
//            }
//        }
        return jobRepository.findById(id).orElse(null);

    }

    @Override
    public boolean deleteJobById(Long id) {
        //        Iterator<Job> iterator= jobs.iterator();
        //        while (iterator.hasNext()){
        //            Job job=iterator.next();
        //            if(job.getId().equals(id)){
        //                iterator.remove();
        //                return true;
        //            }
        //        }
        //        return false;
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

//        for(Job job:jobs){
//            if(job.getId().equals(id)){
//                job.setTitle(updateJob.getTitle());
//                job.setDescription(updateJob.getDescription());
//                job.setMinSalary(updateJob.getMinSalary());
//                job.setMaxSalary(updateJob.getMaxSalary());
//                job.setLocation(updateJob.getLocation());
//                return true;
//
//
//
//            }
//        }
        return false;
    }
}
