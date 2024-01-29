package Interface;


public class ExampleFunctionalInterface {
    public static void main(String[] args) {
        // Lambda expression to implement the functional interface
        MyFunctionInterface<Integer,String> myLambda = a-> doSomething(a);

        MyFunctionInterface<Integer,String> myLambda1 = a-> setRule(a);
        // Invoke the method using the lambda expression
        String str=myLambda.myMethod(2);
        myLambda1.myMethod(2);
        System.out.println(str);
    }

    private static String  doSomething(int a) {
        System.out.println("Doing something..."+a);
        return "rajendra";
    }

    private static String  setRule(int a) {
        System.out.println("Doing something..."+a);
        return "rajendra";
    }
}
