package com.sand.ibsmis.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;

import sun.util.logging.resources.logging;

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
