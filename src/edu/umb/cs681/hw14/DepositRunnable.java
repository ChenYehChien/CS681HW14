package edu.umb.cs681.hw14;

import java.util.concurrent.atomic.AtomicBoolean;

public class DepositRunnable implements Runnable{
	private BankAccount account;
	public AtomicBoolean done = new AtomicBoolean(false);
	
	public void setDone() {
		done.getAndSet(true);
	}
	
	public DepositRunnable(BankAccount account) {
		this.account = account;
	}
	
	public void run(){
		while (true) {
			if(done.get()) {
    			System.out.println("Stopped depositing money from the account.");
    			break;
			}
			System.out.println("deposit!");
			account.deposit(100);
			
			try {
				Thread.sleep(1000);
			}catch(InterruptedException e) {
				System.out.println(e.toString());
				continue;
			}
		}
	}
}
