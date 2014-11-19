package com.sand.ibsmis.util;

import java.util.Comparator;

import com.sand.ibsmis.bean.CheckPaper;

public class ComparatorType implements Comparator{

	public int compare(Object o1, Object o2) {
		CheckPaper check1=(CheckPaper)o1;
		CheckPaper check2=(CheckPaper)o2;
		int flag=check1.getTransType().compareTo(check2.getTransType());
		return flag;
	}	
}
