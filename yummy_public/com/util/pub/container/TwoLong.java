package com.util.pub.container;

public class TwoLong {
	private long l1;
	private long l2;
	
	public TwoLong() {
		
	}
	
	//���췽�� ������ǿתΪ������
	public TwoLong(int i, int j) {
		l1 = i;
		l2 = j;
	}

	public long getL1() {
		return l1;
	}

	public void setL1(long l1) {
		this.l1 = l1;
	}

	public long getL2() {
		return l2;
	}

	public void setL2(long l2) {
		this.l2 = l2;
	}
	
	//����
	public void superimposingL1(int i) {
		l1 += i;
	}
	
	public void superimposingL2(int j) {
		l2 += j;
	}
}
