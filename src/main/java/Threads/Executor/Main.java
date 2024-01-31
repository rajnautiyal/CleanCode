package Threads.Executor;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


record Order(long orderId, String item, int qty) { };
public class Main {

    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        ShoeWarehouse warehouse = new ShoeWarehouse();


        ExecutorService orderServicing= Executors.newCachedThreadPool();
        Callable<Order> orderingTask=()->{
            Order order=generatorOder();
            try{
                Thread.sleep(random.nextInt(502));
                warehouse.receiveOrder(order);
            }catch (InterruptedException ex){
                throw new RuntimeException();
            }
            return  order;
        };
       // List<Callable<Order>> tasks= Collections.nCopies(15,orderingTask);

        try{
            for(int i=0;i<15;i++){
                Thread.sleep(random.nextInt(500,2000));
                orderServicing.submit(()->warehouse.receiveOrder(generatorOder()));
            }
        }catch (RuntimeException ex){
            throw new RuntimeException(ex);
        }
       // orderServicing.invokeAll(tasks);
        orderServicing.awaitTermination(6, TimeUnit.SECONDS);
        warehouse.shutDown();

    }

    private static Order generatorOder() {
        return new Order(
                random.nextLong(1000000, 9999999),
                ShoeWarehouse.PRODUCT_LIST[random.nextInt(0, 5)],
                random.nextInt(1, 4));
        }
    }
