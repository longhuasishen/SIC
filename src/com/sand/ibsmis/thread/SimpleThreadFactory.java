package com.sand.ibsmis.thread;

import java.util.concurrent.ThreadFactory;

public class SimpleThreadFactory implements ThreadFactory {

	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setPriority(Thread.MAX_PRIORITY);
		return t;
	}

}
