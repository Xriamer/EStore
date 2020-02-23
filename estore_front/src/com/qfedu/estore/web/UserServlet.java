package com.qfedu.estore.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.qfedu.estore.constants.Constants;
import com.qfedu.estore.entity.User;
import com.qfedu.estore.service.UserService;
import com.qfedu.estore.utils.BeanFactory;

@SuppressWarnings("all")
@WebServlet("/user")
public class UserServlet extends BaseServlet {
	//UserService uService = new UserServiceImpl();
	UserService service = BeanFactory.getBean(UserService.class);
	
	public void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String telephone = req.getParameter("telephone");
		System.out.println("Telephone  "+telephone);
		String password = req.getParameter("password");
		System.out.println("Password  "+password);
		User user = service.login(telephone, password);
		if ( user == null ) {
			resp.getWriter().write("err");
		} else {
			
			// 将用户对象保存到session中
			req.getSession().setAttribute("loginUser", user);
			// 记住用户名
			String remember = req.getParameter("remember");
			if ( remember != null ) {
				/*
				 * 参数1： cookie的名字
				 * 参数2：值
				 */
				Cookie c = new Cookie("telephone", telephone);
				// 设置cookie的有效期，单位是秒
				c.setMaxAge(9999999); 
				// 将cookie添加到响应对象中！
				resp.addCookie(c);
			}
			// 没有勾选，则对cookie进行删除操作！
			else {
				Cookie c = new Cookie("telephone", null);
				c.setMaxAge(0);
				resp.addCookie(c);
			}
			
			resp.getWriter().write("ok");
		}
	}
	
	public void register(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = new User();
		try {
			BeanUtils.populate(user, req.getParameterMap());
			// 调用业务层，处理注册的业务功能
			service.register(user);
			
			// 重定向到登录页面
			// req.getContextPath() 获取项目的名称  /estore_front
			// 查询之后，使用转发，增删改之后使用重定向 ！
			resp.sendRedirect(req.getContextPath() + "/login.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	// 验证图片验证码是否正确
	public void checkIMGCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String imgCode1 = req.getParameter("imgCode");
		String imgCode2 = (String)req.getSession().getAttribute("code");
		System.out.println("用户输入的图片验证码  "+imgCode1);
		System.out.println("正确的图片验证码  "+imgCode2);
		if ( imgCode1 == null || "".equals(imgCode1) || !imgCode1.equalsIgnoreCase(imgCode2) ) {
			resp.getWriter().write("false");
		} else {
			resp.getWriter().write("true");
		}
	}
	
	// 验证短信验证码是否正确
	public void checkSMSCode(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String smsCode1 = req.getParameter("smsCode");
		String smsCode2 = (String)req.getSession().getAttribute("smsCode");
		if ( smsCode1 == null || "".equals(smsCode1) || !smsCode1.equalsIgnoreCase(smsCode2) ) {
			resp.getWriter().write("false");
		} else {
			resp.getWriter().write("true");
		}
	}
	
	// 验证手机号是否被注册
	public void checkExists(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String tel = req.getParameter("tel");
		boolean exists = service.checkExists(tel);
		resp.getWriter().write(exists + "");
	}
	
	public void sendSMS(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 生成6位验证码
        String code = (Math.random()+"").substring(2, 8);
        // 将验证码保存到session中，验证使用
        req.getSession().setAttribute("smsCode", code);
        System.out.println("短信验证码 =="+code);
		
		/*DefaultProfile profile = DefaultProfile.getProfile("default", "LTAImGY4q153aJTI", "QmdNgPeSLzLGaRrQLmOzFETAbjdSWH");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", req.getParameter("tel"));
        request.putQueryParameter("SignName", "千锋商城");
        request.putQueryParameter("TemplateCode", "SMS_166095879");
        request.putQueryParameter("TemplateParam", "{\"code\": \""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String result = response.getData();
            resp.getWriter().write(result);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }*/
	}
}
