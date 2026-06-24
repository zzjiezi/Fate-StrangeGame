package com.game.rpg.resource;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class AudioManager {
    private static final Logger logger = LoggerFactory.getLogger(AudioManager.class);
    
    private MediaPlayer bgmPlayer;
    private AudioClip currentSfx;
    
    private double bgmVolume;
    private double sfxVolume;
    
    private String currentBgmPath;
    
    public AudioManager() {
        this.bgmVolume = 0.5;
        this.sfxVolume = 0.7;
        this.currentBgmPath = null;
    }
    
    public void playBgm(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("BGM path cannot be null or empty");
        }
        
        try {
            if (bgmPlayer != null) {
                bgmPlayer.stop();
            }
            
            File file = new File(path);
            Media media;
            if (file.exists()) {
                media = new Media(file.toURI().toString());
            } else {
                media = new Media(getClass().getResource(path).toExternalForm());
            }
            
            bgmPlayer = new MediaPlayer(media);
            bgmPlayer.setVolume(bgmVolume);
            bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            bgmPlayer.play();
            
            currentBgmPath = path;
            logger.info("Playing BGM: {}", path);
        } catch (Exception e) {
            logger.error("Failed to play BGM: {}", path, e);
        }
    }
    
    public void stopBgm() {
        if (bgmPlayer != null) {
            bgmPlayer.stop();
            currentBgmPath = null;
            logger.info("BGM stopped");
        }
    }
    
    public void pauseBgm() {
        if (bgmPlayer != null) {
            bgmPlayer.pause();
            logger.info("BGM paused");
        }
    }
    
    public void resumeBgm() {
        if (bgmPlayer != null) {
            bgmPlayer.play();
            logger.info("BGM resumed");
        }
    }
    
    public void playSfx(String path) {
        if (path == null || path.trim().isEmpty()) {
            throw new IllegalArgumentException("SFX path cannot be null or empty");
        }
        
        try {
            File file = new File(path);
            AudioClip audioClip;
            if (file.exists()) {
                audioClip = new AudioClip(file.toURI().toString());
            } else {
                audioClip = new AudioClip(getClass().getResource(path).toExternalForm());
            }
            
            audioClip.setVolume(sfxVolume);
            audioClip.play();
            
            currentSfx = audioClip;
            logger.debug("Playing SFX: {}", path);
        } catch (Exception e) {
            logger.error("Failed to play SFX: {}", path, e);
        }
    }
    
    public void setBgmVolume(double volume) {
        if (volume < 0.0 || volume > 1.0) {
            throw new IllegalArgumentException("Volume must be between 0.0 and 1.0");
        }
        this.bgmVolume = volume;
        if (bgmPlayer != null) {
            bgmPlayer.setVolume(volume);
        }
        logger.debug("BGM volume set to: {}", volume);
    }
    
    public void setSfxVolume(double volume) {
        if (volume < 0.0 || volume > 1.0) {
            throw new IllegalArgumentException("Volume must be between 0.0 and 1.0");
        }
        this.sfxVolume = volume;
        logger.debug("SFX volume set to: {}", volume);
    }
    
    public double getBgmVolume() {
        return bgmVolume;
    }
    
    public double getSfxVolume() {
        return sfxVolume;
    }
    
    public String getCurrentBgmPath() {
        return currentBgmPath;
    }
    
    public boolean isBgmPlaying() {
        return bgmPlayer != null && bgmPlayer.getStatus() == MediaPlayer.Status.PLAYING;
    }
}