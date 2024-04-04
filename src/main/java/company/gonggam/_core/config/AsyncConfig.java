package company.gonggam._core.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig extends AsyncConfigurerSupport {

    @Override
    public Executor getAsyncExecutor() {

        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 기본 실행 대기하는 Thread 수
        executor.setCorePoolSize(5);
        // 동시 동작하는 최대 Thread 수
        executor.setMaxPoolSize(30);
        // MaxPoolSize 초과 요청에서 Thread 생성 요청 시,
        // 해당 요청 Queue에 저장하는데 이 때 최대 수용 가능한 Queue의 size
        // Queue에 저장되어 있다가 Thread에 자리 생기면 하나씩 빠져나가 동작
        executor.setQueueCapacity(50);
        // 생성되는 Thread 접두사 지정
        executor.setThreadNamePrefix("GONGGAM-ASYNC-");
        // 작업 Queue와 모든 Thread가 활성 상태일 때 추가적인 작업 요청 시,
        // 해당 작업을 시도한 Thread(Client Thread)에서 직접 해당 작업 실행
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        executor.initialize();

        return executor;
    }
}
