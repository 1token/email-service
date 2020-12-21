package org.acme.emailservice.email;

public class GmailClient implements Runnable {

    public static boolean die = false;

    public void run() {

        while (!die) {
            try {
                Thread.sleep(0);
            } catch (InterruptedException e) {
                System.out.println("Interrupted");
                return;
            }
            continue;
        }
        System.out.println("GmailClient thread exited gracefully");
    }
    
}
