package com.util.pub.time;

/**
 * ��ʱ�� ��Ƕ��ʹ��
 * @author hengzai ʱ�䣺2013-8-4
 */
public class Timer {
	//��ʼ������ ��̬������
	private static long[] timer = new long[5000];
	
	static void juStopWatch() {
		for(int i = 0; i < timer.length; i++) {
			timer[i] = 0;
		}
	}
	
	//�߳������� ��ʼ��ʱ ����1�������ʱ ���������
	public static synchronized int start() {
		int n;
		for(n = 0; n < timer.length - 1; n++) {
			//����time���飬�������Ϊ�����ø�����ſ�ʼ��ʱ
			if(timer[n] == 0) {
				//�����Ժ���Ϊ��λ�ĵ�ǰʱ��
				timer[n] = System.currentTimeMillis();
			}
		}
		n = timer.length - 1;
		timer[n] = System.currentTimeMillis();
		return -1;
	}
	
	//�߳�������  ȡ�ö�Ӧ��ŵ���� ֹͣ��ʱ
	public static synchronized double stop(int i) {
		//�������
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
