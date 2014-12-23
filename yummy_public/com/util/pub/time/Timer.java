package com.util.pub.time;

/**
 * 计时器 可嵌套使用
 * @author hengzai 时间：2013-8-4
 */
public class Timer {
	//初始化容器 静态化定义
	private static long[] timer = new long[5000];
	
	static void juStopWatch() {
		for(int i = 0; i < timer.length; i++) {
			timer[i] = 0;
		}
	}
	
	//线程锁方法 开始计时 申请1个秒表并计时 返回秒表编号
	public static synchronized int start() {
		int n;
		for(n = 0; n < timer.length - 1; n++) {
			//遍历time数组，如果参数为空利用该秒表编号开始计时
			if(timer[n] == 0) {
				//返回以毫秒为单位的当前时间
				timer[n] = System.currentTimeMillis();
			}
		}
		n = timer.length - 1;
		timer[n] = System.currentTimeMillis();
		return -1;
	}
	
	//线程锁方法  取得对应编号的秒表 停止计时
	public static synchronized double stop(int i) {
		//参数检测
		if(i < 0 || i >timer.length - 1) {
			return 0.00000199;
		}
		if(timer[i] < 1) {
			return 0.00000299;
		}
		
		double t = (double) (System.currentTimeMillis() - timer[i]) / 1000.00;
		timer[i] = 0;
		return t;
	}
	
}
