package com.sand.ibsmis.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

public class PermissionUtil {
	public static boolean isPermitted(String permission){
		boolean r=false;
		Subject currentUser=SecurityUtils.getSubject();
		if(currentUser!=null&&currentUser.isPermitted(permission)){
			r=true;
		}
		return r;
	}
}
