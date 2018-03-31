package matchmaker;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class MatchMakerConfig {
    @Bean
    public BlockingQueue getBlockingQueue(){ return new LinkedBlockingQueue<>();}

    @Bean
    public OkHttpClient getClient(){ return new OkHttpClient(); }
}
