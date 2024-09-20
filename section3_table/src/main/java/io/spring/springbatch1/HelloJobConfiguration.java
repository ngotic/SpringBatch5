package io.spring.springbatch1;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;


@Configuration
public class HelloJobConfiguration {

    // JobBuilderFactory, StepBuilderFactory deprecated!
    @Bean
    public Job helloWorldJob(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new JobBuilder("job", jobRepository)
                .start(step1(jobRepository, transactionManager)) // Job안에는 step이 존재한다.
                .next(step2(jobRepository, transactionManager))
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager)  {
        return new StepBuilder("step1", jobRepository)
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    System.out.println(" =========================== ");
                    System.out.println(" >> Hello Spring Batch!!");
                    System.out.println(" =========================== ");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build()
                ;
    }

    @Bean
    public Step step2(JobRepository jobRepository, PlatformTransactionManager transactionManager)  {
        return new StepBuilder("step2", jobRepository)
                .tasklet((StepContribution contribution, ChunkContext chunkContext) -> {
                    System.out.println(" =========================== ");
                    System.out.println(" >> Step2 was executed!!");
                    System.out.println(" =========================== ");
                    return RepeatStatus.FINISHED;
                }, transactionManager)
                .build()
                ;
    }

}