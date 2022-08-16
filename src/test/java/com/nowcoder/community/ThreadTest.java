package com.nowcoder.community;

public class ThreadTest {
    public static void main(String[] args) {
        System.out.println("Main Thread start");

        Thread threadA=new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("ThreadA start");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ThreadA run finished");
            }
        });
        threadA.start();
        System.out.println("Main Thread join before");
        try {
            threadA.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main Thread run finished");
    }

//    public static void main(String[] args) {
//        System.out.println("Main Thread start");
//
//        Thread threadA=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("ThreadA start");
//                try {
//                    Thread.sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                System.out.println("ThreadA run finished");
//            }
//        });
//        threadA.start();
//        System.out.println("Main Thread join before");
//        System.out.println("Main Thread run finished");
//    }
}
