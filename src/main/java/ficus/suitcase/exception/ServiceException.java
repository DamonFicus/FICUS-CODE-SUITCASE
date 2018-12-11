package ficus.suitcase.exception;


/**
 *@author DamonFicus
 * 异常封装类，用于返回显示错误码和
 * 错误信息
 */
public class ServiceException extends RuntimeException {

	private static final long serialVersionUID = 6668243232129476823L;

	/**异常错误码*/
	private String code;

	/**异常描述*/
	private String msg;


	public ServiceException(String code) {
		super(code);
		this.code = code; 
	}

	public ServiceException(String code, String msg) {
		super(code+":"+msg);
		this.code = code;
		this.msg = msg;
	}
 

	public ServiceException(String code, Throwable e) {
		super(code, e);
		this.code = code; 
	}


	public ServiceException(String code, String msg, Throwable e) {
		super(code+":"+msg, e);
		this.code = code;
		this.msg = msg; 
	}

	public String getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}
 

}
