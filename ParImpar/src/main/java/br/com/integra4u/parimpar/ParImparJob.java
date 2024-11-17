package br.com.integra4u.parimpar;

import br.com.integra4u.parimpar.repository.BatchRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.function.FunctionItemProcessor;
import org.springframework.batch.item.support.IteratorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration

public class ParImparJob {

        @Autowired
        private BatchRepository batchRepository;
        @Bean
        public Job imprimeParImparJob() throws Exception {
            return new JobBuilder("imprimeParImparJob", batchRepository.jobRepository())
                    .start(imprimeParImparStep())
                    .incrementer(new RunIdIncrementer())
                    .build();

        }

        @Bean
        public Step imprimeParImparStep() throws Exception {
            return new StepBuilder("imprimeParImparStep", batchRepository.jobRepository())
                    .<Integer,String> chunk(10, batchRepository.transactionManager())
                    .reader(contaAteDezReader())
                    .processor(parOuImparProcessor())
                    .writer(imprimeWriter())
                    .build();
        }

        private ItemWriter<String> imprimeWriter() {
            return itens -> itens.forEach(System.out::println);
        }

        private FunctionItemProcessor<Integer, String> parOuImparProcessor() {
            return new FunctionItemProcessor<Integer,String>
                    (item -> item % 2 == 0 ? String.format("Item %s é Par", item) : String.format("Item %s é Ímpar", item));
        }

        private IteratorItemReader<Integer> contaAteDezReader() {
            List<Integer> numeros = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
            return new IteratorItemReader<Integer>(numeros.iterator());
        }
    }

