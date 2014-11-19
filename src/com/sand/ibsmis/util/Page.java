package com.sand.ibsmis.util;

import java.util.List;

public class Page<T> {
	private int totalCount;
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
	private List result;
	public Page(){
		
	}
	public Page(int totalCount,List result){
		this.totalCount=totalCount;
		this.result=result;
	}
	public String toString(){
		return "{totalCount:"+totalCount+",result:"+result+"}";
	}
}
