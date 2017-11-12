package com.messio.mini.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * Created by jpc on 10/29/14.
 */
@Component
@Scope("singleton") // TODO thread safe
public class AssetManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(AssetManager.class);
    private static final int BUFFER_SIZE = 1024 * 1024;
    private final byte[] buffer = new byte[BUFFER_SIZE];
    private final Map<String, String> uuids = new TreeMap<>();
    private final Map<String, Long> positions = new HashMap<>();
    private File file;

    @PostConstruct
    public void init() throws IOException {
        try{
            this.file = File.createTempFile("temporary", "bin");
            file.deleteOnExit();
        } catch (IOException e){
            LOGGER.error("Cannot initialize asset manager", e);
        }
    }

    public String saveData(Part part) throws IOException {
        return saveData(part.getContentType(), part.getSize(), part.getInputStream(), part.getName());
    }

    public String saveData(String contentType, File file) throws IOException {
        return saveData(contentType, file.length(), new FileInputStream(file), file.getName());
    }

    public String saveData(String contentType, long contentLength, InputStream is, String name) throws IOException {
        LOGGER.debug("writing data");
        final RandomAccessFile wFile = new RandomAccessFile(file, "rw");
        long position = wFile.length();
        wFile.seek(position);
        final String uuid = UUID.randomUUID().toString();
        wFile.writeUTF(uuid);
        wFile.writeUTF(name);
        wFile.writeUTF(contentType);
        wFile.writeLong(contentLength);
        LOGGER.debug("uuid: {}, name: {}, content type: {}, size: {}", uuid, name, contentType, contentLength);
        int read;
        while ((read = is.read(buffer)) != -1) wFile.write(buffer, 0, read);
        is.close();
        wFile.close();
        positions.put(uuid, position);
        uuids.put(uuid, String.format("%s %s %s %s", uuid, name, contentType, contentLength));
        LOGGER.debug("data written at position: {}", position);
        return uuid;
    }

    public void loadData(String uuid, HttpServletResponse res) throws IOException {
        Long position = positions.get(uuid);
        if (position == null) throw new IOException("data not found: " + uuid);
        LOGGER.debug("reading data at: {}", position);
        final RandomAccessFile rFile = new RandomAccessFile(file, "r");
        rFile.seek(position);
        final String uuid1 = rFile.readUTF();
        if (!uuid1.equals(uuid)) throw new IOException("uuid not matched: " + uuid);
        final String name = rFile.readUTF();
        final String contentType = rFile.readUTF();
        res.setContentType(contentType);
        long size = rFile.readLong();
        LOGGER.debug("uuid: {}, name: {}, content type: {}, size: {}", uuid, name, contentType, size);
        final byte[] buf = new byte[BUFFER_SIZE];
        long read = 0;
        while (read < size){
            int r = rFile.read(buf, 0, (int) size);
            res.getOutputStream().write(buf, 0, r);
            read += r;
        }
        rFile.close();
    }

    public Map<String, String> getUuids() {
        return uuids;
    }
}
