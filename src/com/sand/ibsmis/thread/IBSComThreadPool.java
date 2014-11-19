package com.sand.ibsmis.thread;

import org.apache.commons.logging.Log;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.LogFactory;


/**
 * @author Nano
 * 
 * 适配服务器线程池模型
 *
 */
public class IBSComThreadPool {
	
	private static Log log = LogFactory.getLog(IBSComThreadPool.class);
	
	private ExecutorService executor;
	
	public IBSComThreadPool(int corePoolSize){
		 int arg0 = corePoolSize;
    	 int arg1 = corePoolSize+50;
    	 long arg2 = 5;
    	 TimeUnit arg3 = TimeUnit.MINUTES;
    	 BlockingQueue<Runnable> arg4 = new  LinkedBlockingQueue<Runnable>();
    	 ThreadFactory arg5 = new SimpleThreadFactory();
    	 RejectedExecutionHandler arg6 = new ThreadPoolExecutor.AbortPolicy();
    	 executor = new ThreadPoolExecutor(arg0, arg1, arg2, arg3, arg4,arg5, arg6);
	}

	public IBSComThreadPool(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue){
		executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	public IBSComThreadPool(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,RejectedExecutionHandler handler){	
		executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, handler);
	}
	
	public IBSComThreadPool(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory){
		executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory);
	}
	
	public IBSComThreadPool(int corePoolSize,int maximumPoolSize,long keepAliveTime,TimeUnit unit,BlockingQueue<Runnable> workQueue,ThreadFactory threadFactory,RejectedExecutionHandler handler){
		executor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, handler);
	}
	
	
	public void execute(Runnable command){
		if(executor != null && command != null){
			log.info("begin execute new command["+command.getClass()+"]");
			executor.execute(command);
		}
	}
	
	public void shutdown(){
		if(executor != null){
			executor.shutdown();
			log.info("shutdown execute pool ok ");
		}
	}
	
	public Future<?> submit(Runnable task) {
		   if(executor != null){
			   return executor.submit(task);
		   }
		   return null;
	}
	
	public <T> Future<T> submit(Callable<T> task){
		if(executor != null){
			return executor.submit(task);
		}
		return null;
	}
	
	public <T> Future<T> submit(Runnable task, T result){
		if(executor != null){
			return executor.submit(task,result);
		}
		   return null;
	}

}
