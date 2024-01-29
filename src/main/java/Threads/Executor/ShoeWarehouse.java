package Threads.Executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShoeWarehouse {
    private List<Order> shippingItems;

    private final ExecutorService fullfillmentService;
    public final static String[] PRODUCT_LIST =
            {"Running Shoes", "Sandals", "Boots", "Slippers", "High Tops"};

    public ShoeWarehouse() {
        this.fullfillmentService = Executors.newFixedThreadPool(3);
        this.shippingItems = new ArrayList<>();
    }

    public  void shutDown(){
        fullfillmentService.shutdown();
    }

    public synchronized void receiveOrder(Order item) {

        while (shippingItems.size() > 20) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        shippingItems.add(item);
        System.out.println(Thread.currentThread().getName()+" --Incoming thread: --"+item);
        fullfillmentService.submit(this::fulfillOrder);

        notifyAll();
    }

    public synchronized Order fulfillOrder() {

        while (shippingItems.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

        Order item = shippingItems.remove(0);
        System.out.println(Thread.currentThread().getName() + " Fulfilled: " + item);
        notifyAll();
        return item;
    }
}
