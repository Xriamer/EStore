package com.qfedu.estore.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class EncodingFilter implements Filter {
	public void init(FilterConfig config) throws ServletException { }
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		// 将请求和响应对象转换成和HTTP协议相关的对象
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		// 对请求和响应对象进行预处理：设置编码，然后放行
		req.setCharacterEncoding("UTF-8"); // 只对post有效
		response.setContentType("text/html;charset=UTF-8");
		// 判断请求方式是否是get
		String method = request.getMethod();
		if (method.equalsIgnoreCase("get")) {
			// 对HttpServletRequest接口的实现类HttpServletRequestWrapper的子类，进行放行
			// 使用装饰者模式对request对象进行包装增强:在包装类的内部对某些方法进行重写
			MyRequest myReq = new MyRequest(request);
			chain.doFilter(myReq, response);
			return ; // 不能放行2次，因此return
		}
		
		chain.doFilter(req, response);
	}
	public void destroy() { }
	private class MyRequest extends HttpServletRequestWrapper {
		public MyRequest(HttpServletRequest request) {
			super(request);
			// 构造函数只执行一次，因此对map的处理放在这里
			Map<String, String[]> map = super.getParameterMap();
			if ( map != null ) {
				// 获取map中所有的key值
				Set<String> keys = map.keySet();
				for (String key : keys) {
					String[] values = map.get(key);
					if ( values != null ) {
						for (int i = 0; i < values.length; i++) {
							try {
								values[i] = new String(values[i].getBytes("ISO-8859-1"), "UTF-8");
							} catch (UnsupportedEncodingException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
		}
		public String getParameter(String name) {
			// 调用复写的getParameterValues方法
			String[] values = getParameterValues(name);
			if ( values != null ) {
				return values[0];
			}
			return null;
		}
		public String[] getParameterValues(String name) {
			// 调用父类中的getParameterMap方法，获取所有数据，该map中的数据已经在构造函数中处理过乱码了
			Map<String, String[]> map = super.getParameterMap();
			// 从处理后的map中获取数据
			return map.get(name);
		}
	}
}