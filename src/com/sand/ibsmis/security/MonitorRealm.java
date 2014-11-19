package com.sand.ibsmis.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import sun.security.krb5.internal.crypto.Des3;

import com.sand.ibsmis.bean.Role;
import com.sand.ibsmis.bean.User;
import com.sand.ibsmis.service.inf.IBSBaseDataService;
import com.sand.ibsmis.util.Des3Util;
import com.sand.ibsmis.util.IBSMisConf;
import com.sand.ibsmis.util.MD5Util;

public class MonitorRealm extends AuthorizingRealm{
	
	private IBSBaseDataService service;
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//通过用户名获得用户的所有资源，并把资源存入info中
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录的用户名
        String account = (String) super.getAvailablePrincipal(principals);
        
        List<String> roles = new ArrayList<String>();  
        List<String> permissions = new ArrayList<String>();
        List<String> urlList = new ArrayList<String>();
        User user = service.getUser(account);
        if(user != null){
            if (user.getRole() != null) {
            	String roleId=user.getRole().getRole_id();
            	roles.add(user.getRole().getRole_name());
            	permissions=service.findPermissions(roleId);
            	urlList=service.findUrlList(roleId);
            }
        }else{
            throw new AuthorizationException();
        }
        //给当前用户设置角色
        info.addRoles(roles);
        //给当前用户设置权限
        info.addStringPermissions(permissions); 
        info.addStringPermissions(urlList);
        return info;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		//token中储存着输入的用户名和密码
		UsernamePasswordToken upToken=(UsernamePasswordToken)token;
		//获得用户名与密码
		String username=upToken.getUsername();
		String password=String.valueOf(upToken.getPassword());
		//与数据库中用户名和密码进行比对。比对成功则返回info，比对失败则抛出对应信息的异常AuthenticationException
		User user=service.getUser(username);
		//用户不存在
		if(user==null)
		{
			throw new UnknownAccountException();
		}
		Des3Util des3Util=new Des3Util(IBSMisConf.SICKEY);
		//用户名、密码正确
		if(user.getPassword().equals(des3Util.getEncString(password))){
			if(!"0".equalsIgnoreCase(user.getState())){
				throw new LockedAccountException();
			}else{
				SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password .toCharArray(),getName());
				return info;
			}
		}
		//密码错误
		else if(!user.getPassword().equals(des3Util.getEncString(password)))
		{
			throw new IncorrectCredentialsException();
		}
		return null;
	}
	@Autowired
	public void setService(IBSBaseDataService service) {
		this.service = service;
	}
}	
