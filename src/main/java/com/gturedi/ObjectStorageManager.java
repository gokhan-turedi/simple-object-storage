package com.gturedi;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * simple manager class to write/read (a)synchronously data object to file
 *
 * @author gokhan turedi
 */
public class ObjectStorageManager<T extends Serializable> {

    private final String path;

    public ObjectStorageManager(String path) {
        this.path = path;
    }

    public T read()
            throws IOException, ClassNotFoundException {
        return readObject();
    }

    public void readAsync(final ReadCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    T result = read();
                    callback.done(null, result);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    callback.done(e, null);
                }
            }
        }).start();
    }

    public void write(final T object)
            throws IOException {
        writeObject(object);
    }

    public void writeAsync(final T object, final WriteCallback callback) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    write(object);
                    callback.done(null);
                } catch (IOException e) {
                    e.printStackTrace();
                    callback.done(e);
                }
            }
        }).start();
    }

    private synchronized void writeObject(T object)
            throws IOException {
        if (!new File(path).exists()) new File(path).createNewFile();
        FileOutputStream fos = new FileOutputStream(path);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(object);
        fos.close();
        os.close();
    }

    private synchronized T readObject()
            throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(path);
        ObjectInputStream is = new ObjectInputStream(fis);
        T result = (T) is.readObject();
        fis.close();
        is.close();
        return result;
    }

}