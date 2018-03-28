package ru.atom.chat;

import org.javatuples.Triplet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Pattern;

@SpringBootApplication
public class ChatApplication {


    private static final Logger log = LoggerFactory.getLogger(ChatApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ChatApplication.class, args);
    }

    @Bean
    public Map<String, String> setUsersOnlineBean() {
        Map<String,String> users = new ConcurrentHashMap<>();
        users.put("admin","hsl(0,100%,50%)");
        return users;
    }

    @Bean
    public Pattern setUrlPattern() {
        return Pattern.compile("@^(http\\:\\/\\/|https\\:\\/\\/)?([a-z0-9][a-z0-9\\-]*\\.)+[a-z0-9][a-z0-9\\-]*$@i");
    }

    @Bean
    public Queue<Triplet<String, Date, String>> loadHistory() {
        Queue<Triplet<String, Date, String>> messages = new ConcurrentLinkedQueue<>();
        String date = "";
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(ChatApplication.class.getClassLoader()
                .getResource("history.txt").getPath()))) {
            String s;
            while((s = bufferedReader.readLine()) != null)
            {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
                date = s.split(" ")[0];
                String[] other = s.substring(9).split(":");
                String msg = "";
                for(int i = 1; i<other.length; i++) msg = msg.concat(other[i]);
                messages.add(new Triplet<>(other[0], simpleDateFormat.parse(date), msg));
            }
        } catch(IOException e){
            log.error(e.getLocalizedMessage());
            return messages;
        } catch(ParseException e) {
            log.error("error reading date " + date);
            return messages;
        }catch(NullPointerException e) {
            log.error("can`t load 'history.txt'");
            return messages;
        }
        return messages;
    }

}
