package matchmaker;

import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class MatchMakerConfig {
    @Bean
    @Scope("prototype")
    public BlockingQueue<String> getBlockingQueue() {
        return new LinkedBlockingQueue<String>();
    }

    @Bean
    public OkHttpClient getClient() {
        return new OkHttpClient();
    }

    @Bean
    public ConcurrentHashMap<String,Long> getConcurrentHashMap() {
        return new ConcurrentHashMap<String,Long>();
    }
}
