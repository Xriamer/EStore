package com.qfedu.estore.constants;

public interface Constants {
	// Ctrl + Shift + X/Y
	/** 订单状态 */
	interface STATUS_ORDER {
		/** 未付款 */
		public static final int UNPAY = 1;
		/** 已付款 */
		public static final int PAY = 2;
		/** 已过期 */
		public static final int OUTTIME = 3;
		/** 已取消 */
		public static final int CANCEL = 4;
	}
	/** 用户相关的常量 */
	interface USER {
		/** 登陆用户 */
		public static final String LOGINUSER = "loginUser";
	}
}
