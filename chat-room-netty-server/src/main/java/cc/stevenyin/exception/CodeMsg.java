package cc.stevenyin.exception;

import java.io.Serializable;

public class CodeMsg implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2106734927041134081L;
	private int retCode;
	private String message;
	// 按照模块定义CodeMsg
	// 通用异常
	public static final CodeMsg SUCCESS = new CodeMsg(0,"success");
	public static final CodeMsg SERVER_EXCEPTION = new CodeMsg(500100,"服务端异常");
	public static final CodeMsg USER_NOT_EXIST = new CodeMsg(10001,"用户不存在!");
	
	private CodeMsg(int retCode, String message) {
		this.retCode = retCode;
		this.message = message;
	}
	public int getRetCode() {
		return retCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}