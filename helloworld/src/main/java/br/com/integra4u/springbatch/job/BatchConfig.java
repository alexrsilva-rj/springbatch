package br.com.integra4u.springbatch.job;


import br.com.integra4u.springbatch.repository.BatchRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

    @Autowired
    private BatchRepository batchRepository;


    @Bean
    public Job imprimeOlaJob(Step imprimeOlaStep) throws Exception {
        return new JobBuilder("imprimeOlaJob", batchRepository.jobRepository())
                .start(imprimeOlaStep)
                .incrementer(new RunIdIncrementer())
                .build();

    }


}
