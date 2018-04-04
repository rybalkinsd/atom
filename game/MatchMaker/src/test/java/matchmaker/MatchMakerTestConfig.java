package matchmaker;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class MatchMakerTestConfig {

    @Bean
    public ConcurrentHashMap<Long,Integer> returnedRequests(){ return new ConcurrentHashMap<Long,Integer>(); }

}
