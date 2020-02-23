<%@page import="java.io.FileOutputStream"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.Random"%>
<%@ page import="java.io.OutputStream"%>
<%@ page import="java.awt.Color"%>
<%@ page import="java.awt.Font"%>
<%@ page import="java.awt.Graphics"%>
<%@ page import="java.awt.image.BufferedImage"%>
<%@ page import="javax.imageio.ImageIO"%>
<%
	int width = 130, height = 39; // 图片宽度和高度
	// 创建用于保存图片信息的缓冲区对象
	BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
	Graphics g = image.getGraphics(); // 获取画笔
	g.setColor(new Color(0xffffff)); // 设置画笔颜色
	g.fillRect(0, 0, width, height); // 填充背景颜色
	Random r = new Random(); // 创建随机数对象
	// 添加干扰项
	for(int i = 0; i < 15; i++) {
		g.setFont(new Font("Candara", Font.ITALIC, 20)); // 设置字体
		g.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255))); // 重新设置画笔颜色
		g.drawLine(r.nextInt(width), r.nextInt(height), r.nextInt(width), r.nextInt(height));
	}
	String data = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM0123456789";
	int length = data.length();
	String capstr = "";
	g.setFont(new Font("Candara", Font.BOLD, 35)); // 设置字体
	for(int i = 0; i < 4; i++) {
		int index = r.nextInt(length);
		String str = data.charAt(index) + "";
		capstr += str;
		g.setColor(new Color(r.nextInt(150), r.nextInt(150), r.nextInt(150))); // 重新设置画笔颜色
		g.drawString(str, 10+(i*25), 30); // 将字符写入图片
	}
	
	g.dispose(); // 释放资源
	response.setContentType("image/jpeg"); // 设置响应类型
	out.clear(); // 清空缓冲区中的字符流数据(字符流和字节流不能同时使用，否则会抛异常)
	
	OutputStream strm = response.getOutputStream(); // 输出流
	ImageIO.write(image, "jpeg", strm); // 将图片写到输出流
	strm.close(); // 关闭输出流
	
	session.setAttribute("code", capstr); // 保存验证码到session中，用于登录校验
%>