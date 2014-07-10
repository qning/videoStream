/*
 * Copyright (C) 2009,2010,2011,2012 Samuel Audet
 *
 * This file is part of JavaCV.
 *
 * JavaCV is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version (subject to the "Classpath" exception
 * as provided in the LICENSE.txt file that accompanied this code).
 *
 * JavaCV is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with JavaCV.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.example.javacv.stream.test2;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.nio.Buffer;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import com.googlecode.javacv.Frame;

import static com.googlecode.javacv.cpp.opencv_core.*;

/**
 *
 * @author Samuel Audet
 */
public abstract class MyFrameRecorder {

    public static final List<String> list = new LinkedList<String>(Arrays.asList(new String[] { "FFmpeg", "OpenCV" }));
    public static void init() {
        for (String name : list) {
            try {
                Class<? extends MyFrameRecorder> c = get(name);
                c.getMethod("tryLoad").invoke(null);
            } catch (Throwable t) { }
        }
    }
    public static Class<? extends MyFrameRecorder> getDefault() {
        // select first frame recorder that can load..
        for (String name : list) {
            try {
                Class<? extends MyFrameRecorder> c = get(name);
                c.getMethod("tryLoad").invoke(null);
                return c;
            } catch (Throwable t) { }
        }
        return null;
    }
    public static Class<? extends MyFrameRecorder> get(String className) throws Exception {
        className = MyFrameRecorder.class.getPackage().getName() + "." + className;
        try {
            return Class.forName(className).asSubclass(MyFrameRecorder.class);
        } catch (ClassNotFoundException e) {
            String className2 = className + "FrameRecorder";
            try {
                return Class.forName(className2).asSubclass(MyFrameRecorder.class);
            } catch (ClassNotFoundException ex) {
                throw new Exception("Could not get FrameRecorder class for " + className + " or " + className2, e);
            }
        }
    }

    public static MyFrameRecorder create(Class<? extends MyFrameRecorder> c, Class p, Object o, int w, int h) throws Exception {
        Throwable cause = null;
        try {
            return c.getConstructor(p, int.class, int.class).newInstance(o, w, h);
        } catch (InstantiationException ex) {
            cause = ex;
        } catch (IllegalAccessException ex) {
            cause = ex;
        } catch (IllegalArgumentException ex) {
            cause = ex;
        } catch (NoSuchMethodException ex) {
            cause = ex;
        } catch (InvocationTargetException ex) {
            cause = ex.getCause();
        }
        throw new Exception("Could not create new " + c.getSimpleName() + "(" + o + ", " + w + ", " + h + ")", cause);
    }

    public static MyFrameRecorder createDefault(File file, int width, int height) throws Exception {
        return create(getDefault(), File.class, file, width, height);
    }
    public static MyFrameRecorder createDefault(String filename, int width, int height) throws Exception {
        return create(getDefault(), String.class, filename, width, height);
    }

    public static MyFrameRecorder create(String className, File file, int width, int height) throws Exception {
        return create(get(className), File.class, file, width, height);
    }
    public static MyFrameRecorder create(String className, String filename, int width, int height) throws Exception {
        return create(get(className), String.class, filename, width, height);
    }

    protected String format, profile, preset;
    protected int imageWidth, imageHeight, audioChannels;
    protected int pixelFormat, videoCodec, videoBitrate;
    protected double frameRate;
    protected int sampleFormat, audioCodec, audioBitrate, sampleRate;
    protected boolean interleaved;
    protected int frameNumber = 0;
    protected long timestamp = 0;

    public String getFormat() {
        return format;
    }
    public void setFormat(String format) {
        this.format = format;
    }

    public String getProfile() {
        return profile;
    }
    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getPreset() {
        return preset;
    }
    public void setPreset(String preset) {
        this.preset = preset;
    }

    public int getImageWidth() {
        return imageWidth;
    }
    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }
    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getAudioChannels() {
        return audioChannels;
    }
    public void setAudioChannels(int audioChannels) {
        this.audioChannels = audioChannels;
    }

    public int getPixelFormat() {
        return pixelFormat;
    }
    public void setPixelFormat(int pixelFormat) {
        this.pixelFormat = pixelFormat;
    }

    public int getVideoCodec() {
        return videoCodec;
    }
    public void setVideoCodec(int videoCodec) {
        this.videoCodec = videoCodec;
    }

    public int getVideoBitrate() {
        return videoBitrate;
    }
    public void setVideoBitrate(int videoBitrate) {
        this.videoBitrate = videoBitrate;
    }

    public double getFrameRate() {
        return frameRate;
    }
    public void setFrameRate(double frameRate) {
        this.frameRate = frameRate;
    }

    public int getSampleFormat() {
        return sampleFormat;
    }
    public void setSampleFormat(int sampleFormat) {
        this.sampleFormat = sampleFormat;
    }

    public int getAudioCodec() {
        return audioCodec;
    }
    public void setAudioCodec(int audioCodec) {
        this.audioCodec = audioCodec;
    }

    public int getAudioBitrate() {
        return audioBitrate;
    }
    public void setAudioBitrate(int audioBitrate) {
        this.audioBitrate = audioBitrate;
    }

    public int getSampleRate() {
        return sampleRate;
    }
    public void setSampleRate(int sampleRate) {
        this.sampleRate = sampleRate;
    }

    public boolean isInterleaved() {
        return interleaved;
    }
    public void setInterleaved(boolean interleaved) {
        this.interleaved = interleaved;
    }

    public int getFrameNumber() {
        return frameNumber;
    }
    public void setFrameNumber(int frameNumber) {
        this.frameNumber = frameNumber;
    }

    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static class Exception extends java.lang.Exception {
        public Exception(String message) { super(message); }
        public Exception(String message, Throwable cause) { super(message, cause); }
    }

    public abstract void start() throws Exception;
    public abstract void stop() throws Exception;
    public abstract void record(IplImage image) throws Exception;
    public void record(Buffer samples) throws Exception {
        throw new UnsupportedOperationException("This FrameRecorder does not support audio.");
    }
    public void record(Frame frame) throws Exception {
        if (frame == null || (frame.image == null && frame.samples == null)) {
            record((IplImage)null);
        } else {
            if (frame.image != null) {
                record(frame.image);
            }
            if (frame.samples != null) {
                record(frame.samples);
            }
        }
    }
    public abstract void release() throws Exception;
}
