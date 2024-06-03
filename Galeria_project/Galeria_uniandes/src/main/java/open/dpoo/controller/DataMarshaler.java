package main.java.open.dpoo.controller;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DataMarshaler {
    private static final Logger logger = Logger.getLogger(DataMarshaler.class.getName());
    private final String folderPath;

    public DataMarshaler() {
        URL resource = getClass().getClassLoader().getResource("persistence");
        if (resource != null) {
            folderPath = Paths.get(resource.getPath()).toString();
        } else {
            logger.log(Level.SEVERE, "[DataMarshaler::new] Persistence folder not found in resources");
            throw new IllegalArgumentException("Persistence folder not found in resources");
        }
    }

    public void serialize(String fileName, Object object) throws IOException {
        if (object == null) {
            logger.log(Level.WARNING, "[DataMarshaler::serialize] Cannot serialize null object");
            throw new IllegalArgumentException("Cannot serialize null object");
        }
        byte[] data = serialize0(object);
        Path filePath = Paths.get(folderPath, fileName);
        Files.write(filePath, data);
        logger.log(Level.INFO, "Serialized object of type " +  object.getClass().getName() + " to file: " + filePath);
    }

    public <T> T deserialize(String fileName, Class<T> expectedClass) throws IOException, ClassNotFoundException {
        Path filePath = Paths.get(folderPath, fileName);
        if (!Files.exists(filePath)) {
            logger.log(Level.WARNING, "[DataMarshaler::deserialize] File not found: " + filePath);
            throw new FileNotFoundException("File not found: " + filePath);
        }
        byte[] data = Files.readAllBytes(filePath);
        T deserializedObject = deserialize0(data, expectedClass);
        logger.log(Level.INFO, "Deserialized object of type " + expectedClass.getName() + " from file: " + filePath);
        return deserializedObject;
    }

    private byte[] serialize0(Object object) throws IOException {
        if (object == null) {
            logger.log(Level.WARNING, "[DataMarshaler::serialize0] Cannot serialize null object");
            throw new IllegalArgumentException("Cannot serialize null object");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(object);
        out.flush();
        return baos.toByteArray();
    }

    @SuppressWarnings("unchecked")
    private <T> T deserialize0(byte[] data, Class<T> expectedClass) throws IOException, ClassNotFoundException {
        if (data == null || data.length == 0) {
            logger.log(Level.WARNING, "[DataMarshaler::deserialize0] Cannot deserialize empty data");
            throw new IllegalArgumentException("Cannot deserialize empty data");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        ObjectInputStream in = new ObjectInputStream(bais);
        Object obj = in.readObject();
        if (expectedClass.isInstance(obj)) {
            return (T) obj;
        }
        throw new ClassCastException("Deserialized object type mismatch");
    }
}
