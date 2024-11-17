package br.com.integra4u.springbatch;


import br.com.integra4u.springbatch.repository.BatchRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfig {

    @Autowired
    private BatchRepository batchRepository;


    @Bean
    public Job imprimeOlaJob() throws Exception {
        return new JobBuilder("imprimeOlaJob", batchRepository.jobRepository())
                .start(imprimeOlaStep())
                .incrementer(new RunIdIncrementer())
                .build();

    }

    @Bean
    public Step imprimeOlaStep(Step imprimeOlaStep) throws Exception {
        return new StepBuilder("imprimeOlaStep", batchRepository.jobRepository())
                .tasklet(imprimeOlaTasklet(null), batchRepository.transactionManager())
                .build();
    }

    @Bean
    @StepScope
    public Tasklet imprimeOlaTasklet(@Value("#{jobParameters['nome']}") String nome) {
        return new Tasklet() {

            @Override
            public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
                System.out.println(String.format("Hello %s!", nome));
                return RepeatStatus.FINISHED;
            }

        };
    }
}
