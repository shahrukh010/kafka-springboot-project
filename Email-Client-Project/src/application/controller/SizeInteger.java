package application.controller;

public class SizeInteger implements Comparable<SizeInteger>{
	
	private int size;
	
	public SizeInteger(int size) {
		this.size = size;
	}
	
	public String toString() {
		
		if(size<=0) {
			return "0";
		}
		else if(size < 1024) {
			return size+"B";
		}
		else if(size < 104856) {
			return size / 1024+"KB";
		}
		else
			return size / 104856+"MB";
	}

	@Override
	public int compareTo(SizeInteger o) {
		
		if(this.size < o.size)return -1;
		else if(this.size > o.size)return 1;
		else return 0;
		
	}
	
}
