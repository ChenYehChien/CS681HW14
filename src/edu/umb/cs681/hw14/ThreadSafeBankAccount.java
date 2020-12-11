package edu.umb.cs681.hw14; 

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeBankAccount implements BankAccount{
	private double balance = 0;
	private ReentrantLock lock = new ReentrantLock();
	//conditions
	Condition sufficientFundsCondition = lock.newCondition();
	Condition belowUpperLimitFundsCondition = lock.newCondition();

	public void withdraw(double amount){
		lock.lock();
		while (balance <= 0) {
			try {
				sufficientFundsCondition.await();
			}
			catch (InterruptedException e) {
				return; 
			}
		}
		balance -= amount;
		belowUpperLimitFundsCondition.signalAll();
		lock.unlock();
	}
	
	public void deposit(double amount){
		lock.lock();
		while (balance >= 300) {
			try {
				belowUpperLimitFundsCondition.await();
			}
			catch (InterruptedException e) {
				return; 
			}
		}
		balance += amount;
		sufficientFundsCondition.signalAll();
		lock.unlock();
	}
		
	public static void main(String[] args){
		ThreadSafeBankAccount bankAccount = new ThreadSafeBankAccount();		
		new Thread( new DepositRunnable(bankAccount) ).start();
		new Thread( new WithdrawRunnable(bankAccount) ).start();
	}
}
