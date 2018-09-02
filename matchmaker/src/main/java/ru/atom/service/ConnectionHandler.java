package ru.atom.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ConnectionHandler implements Runnable {
    private static final Logger log = LogManager.getLogger(ru.atom.service.ConnectionHandler.class);
    private MatchMakerService matchMakerService;

    public ConnectionHandler(MatchMakerService matchMakerService) {
        this.matchMakerService = matchMakerService;
    }

    @Override
    public void run() {
        log.info("Started");
        List<Connection> candidates = new ArrayList<>(matchMakerService.getRequiredPlayerAmount());
        while (!Thread.currentThread().isInterrupted()) {
            try {
                candidates.add(
                        ConnectionQueue.getInstance().poll(10_000, TimeUnit.SECONDS)
                );
            } catch (InterruptedException e) {
                log.warn("Timeout reached");
            }

            if (candidates.size() == matchMakerService.getRequiredPlayerAmount()) {
                Thread thread = new Thread(new GameIdBroker(new ArrayList<>(candidates),
                        matchMakerService));
                thread.start();
                candidates.clear();


            }
        }

    }
}