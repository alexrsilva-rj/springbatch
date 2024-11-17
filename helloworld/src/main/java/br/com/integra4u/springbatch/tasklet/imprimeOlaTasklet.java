package br.com.integra4u.springbatch.tasklet;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class imprimeOlaTaskletConfig {

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
