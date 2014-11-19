package com.sand.ibsmis.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.sand.ibsmis.filter.AuthPassort;
import com.sand.ibsmis.util.PermissionUtil;
@Controller
public class RedirectController {
	private static final Log logger = LogFactory.getLog(RedirectController.class);
	@RequestMapping("/index")
	public String index(){
		return "index";
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request){
		HttpSession session=request.getSession();
		String username="";
    	if(session.getAttribute("username")!=null){
    		username=session.getAttribute("username").toString();
    		logger.info("requestUri=:"+request.getRequestURI()+",username=:"+username);
    	}
    	if(!"".equalsIgnoreCase(username)){
    		return "redirect:/index";
    	}else{
    		return "login";
    	}
	}
	@AuthPassort
	@RequestMapping("/userManage")
	public String userManage(){
		boolean isPermitted=PermissionUtil.isPermitted("userManage");
		if(isPermitted){
			return "user";
		}else{
			return "forbidden";
		}
	}
	@RequestMapping("/roleManage")
	public String roleManage(){
		boolean isPermitted=PermissionUtil.isPermitted("roleManage");
		if(isPermitted){
			return "role";
		}else{
			return "forbidden";
		}
	}
	@RequestMapping("/compManage")
	public String compManage(){
		boolean isPermitted=PermissionUtil.isPermitted("compManage");
		if(isPermitted){
			return "company";
		}else{
			return "forbidden";
		}
	}
	@RequestMapping("/menuManage")
	public String menuManage(){
		boolean isPermitted=PermissionUtil.isPermitted("menuManage");
		if(isPermitted){
			return "menu";
		}else{
			return "forbidden";
		}
	}
	@RequestMapping("/buttonManage")
	public String buttonManage(){
		boolean isPermitted=PermissionUtil.isPermitted("buttonManage");
		if(isPermitted){
			return "button";
		}else{
			return "forbidden";
		}
	}
	@RequestMapping("/notFound")
	public String notFound(){
		return "404";
	}
	@RequestMapping("/serverError")
	public String serverError(){
		return "500";
	}
	@RequestMapping("/forbidden")
	public String forbidden(){
		return "forbidden";
	}
}
