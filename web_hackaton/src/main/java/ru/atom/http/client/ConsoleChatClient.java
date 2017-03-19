package ru.atom.http.client;

import okhttp3.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ConsoleChatClient {

    public static void main(String[] args) throws IOException {
        if (args.length < 1 || args[0].isEmpty()) {
            System.out.println("Provide name as first argument");
            System.exit(-1);
        }

        final String name = args[0];
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    System.out.println(ChatClient.viewChat().body().string());

                    Thread.sleep(5_000);
                } catch (InterruptedException ignored) {
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        try (InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr)
        ) {
            while (true) {
                // your code here

                String line = br.readLine();
                Response resp = ChatClient.say(name, line);
                if (!resp.isSuccessful()) {
                    System.out.println("FAIL!");
                }
                System.out.println(ChatClient.viewChat().body().string());
            }
        }
    }
}