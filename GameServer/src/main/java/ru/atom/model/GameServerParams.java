package ru.atom.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.InputStream;
import java.util.Properties;


public class GameServerParams {

    private InputStream inputStream;
    private static final GameServerParams instance = new GameServerParams();
    private static final Logger log = LogManager.getLogger(GameServerParams.class);

    public GameServerParams() {
        Properties prop = new Properties();
        String propFileName = "application.properties";
        inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);

        if (inputStream != null) {
            try {
                prop.load(inputStream);
                bonusSize = Integer.parseInt(prop.getProperty("bonusSize"));
                tileMapHeight = Integer.parseInt(prop.getProperty("tileMapHeight"));
                tileMapWidth = Integer.parseInt(prop.getProperty("tileMapWidth"));
                tileSize = Integer.parseInt(prop.getProperty("tileSize"));
                boxSize = Integer.parseInt(prop.getProperty("boxSize"));
                bombSize = Integer.parseInt(prop.getProperty("bombSize"));
                bonusFactor = Integer.parseInt(prop.getProperty("bonusFactor"));
                fireSize = Integer.parseInt(prop.getProperty("fireSize"));
                maxExplosRadius = Integer.parseInt(prop.getProperty("maxExplosRadius"));
                startBombAmount = Integer.parseInt(prop.getProperty("startBombAmount"));
                startBombAmount = Integer.parseInt(prop.getProperty("startBombAmount"));
                startExplosRadius = Integer.parseInt(prop.getProperty("startExplosRadius"));
                girlSize = Integer.parseInt(prop.getProperty("girlSize"));
                explosDelay = Integer.parseInt(prop.getProperty("explosDelay"));
                cornerHelpFactor = Integer.parseInt(prop.getProperty("cornerHelpFactor"));
                speedModifier = Float.parseFloat(prop.getProperty("speedModifier"));
                matchMakerUrl = prop.getProperty("matchMakerUrl");
                timeOut = Integer.parseInt(prop.getProperty("timeOut"));
                inputStream.close();
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        } else {
            log.error("property file '" + propFileName + "' not found in the classpath");
        }
    }

    public static GameServerParams getInstance() {
        return instance;
    }

    private int timeOut;
    private int explosDelay;
    private int cornerHelpFactor;
    private int tileMapHeight;
    private int tileMapWidth;
    private int tileSize;
    private int boxSize;
    private int girlSize;
    private int bombSize;
    private int bonusSize;
    private int fireSize;
    private int bonusFactor;
    private int startBombAmount;
    private int startExplosRadius;
    private int maxExplosRadius;
    private float speedModifier;
    private String matchMakerUrl;

    public int getTimeOut() {
        return this.timeOut;
    }

    public String getMatchMakerUrl() {
        return this.matchMakerUrl;
    }

    public float getSpeedModifier() {
        return this.speedModifier;
    }

    public int getCornerHelpFactor() {
        return cornerHelpFactor;
    }

    public int getGirlSize() {
        return this.girlSize;
    }

    public int getBombSize() {
        return this.bombSize;
    }

    public int getTileMapHeight() {
        return this.tileMapHeight;
    }

    public int getBonusSize() {
        return this.bonusSize;
    }

    public int getExplosDelay() {
        return this.explosDelay;
    }

    public int getBonusFactor() {
        return this.bonusFactor;
    }

    public int getBoxSize() {
        return this.boxSize;
    }

    public int getFireSize() {
        return this.fireSize;
    }

    public int getMaxExplosRadius() {
        return this.maxExplosRadius;
    }

    public int getStartBombAmount() {
        return this.startBombAmount;
    }

    public int getTileSize() {
        return this.tileSize;
    }


    public int getStartExplosRadius() {
        return this.startExplosRadius;
    }

    public int getTileMapWidth() {
        return this.tileMapWidth;
    }

}
