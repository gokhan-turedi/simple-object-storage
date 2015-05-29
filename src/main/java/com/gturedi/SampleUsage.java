package com.gturedi;

import java.io.IOException;

public class SampleUsage {

    public static void main(String[] args)
            throws IOException, ClassNotFoundException {

        // synchronized usage
        Car car = new Car(1, "title1");
        String path = "sample.dat";
        final ObjectStorageManager<Car> manager = new ObjectStorageManager<>(path);
        manager.write(car);
        Car copy = manager.read();
        System.out.println(copy.title);

        // asynchronized usage
        car.title = "title2";
        manager.writeAsync(car, new WriteCallback() {
            @Override
            public void done(Exception e) {
                if (e != null) return;
                manager.readAsync(new ReadCallback<Car>() {
                    @Override
                    public void done(Exception e, Car result) {
                        if (e != null) return;
                        System.out.println(result.title);
                    }
                });
            }
        });
    }

}
