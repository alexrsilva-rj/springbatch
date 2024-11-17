package br.com.integra4u.springbatch.step;

import br.com.integra4u.springbatch.repository.BatchRepository;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class imprimeOlaStepConfig {


    @Autowired
    private BatchRepository batchRepository;

    @Bean
    public Step imprimeOlaStep(Tasklet imprimeOlaTasklet) throws Exception {
        return new StepBuilder("imprimeOlaStep", batchRepository.jobRepository())
                .tasklet(imprimeOlaTasklet, batchRepository.transactionManager())
                .build();
    }


}
