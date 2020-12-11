package edu.umb.cs681.hw14;

public class hw14 {
	public static void main(String[] args) {
		ThreadSafeBankAccount t1 = new ThreadSafeBankAccount();
		
		WithdrawRunnable w1 = new WithdrawRunnable(t1);
		WithdrawRunnable w2 = new WithdrawRunnable(t1);
		DepositRunnable d1 = new DepositRunnable(t1);
		DepositRunnable d2 = new DepositRunnable(t1);
		
		
		Thread thread_w1 = new Thread(w1);
		Thread thread_w2 = new Thread(w2);
		Thread thread_d1 = new Thread(d1);
		Thread thread_d2 = new Thread(d2);

		
		thread_w1.start();
		thread_w2.start();
		thread_d1.start();
		thread_d2.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		w1.setDone();
		w2.setDone();
		d1.setDone();
		d2.setDone();
		
		thread_w1.interrupt();
		thread_w2.interrupt();
		thread_d1.interrupt();
		thread_d2.interrupt();
		
		try {
			thread_w1.join();
			thread_w2.join();
			thread_d1.join();
			thread_d2.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
}