package com.xxfbpt.test;

public class ThreadTest {
	
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		OneRunner one = new OneRunner();
		for(int i = 0; i < 50; i++){
			Thread t1 = new Thread(one, "线程"+i);
			t1.start();
//			try {
//				t1.sleep(1000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}

}
