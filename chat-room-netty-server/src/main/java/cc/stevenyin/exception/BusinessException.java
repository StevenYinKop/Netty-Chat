package cc.stevenyin.exception;
public class BusinessException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final CodeMsg codeMsg;
	
	public BusinessException(CodeMsg codeMsg) {
		super();
		this.codeMsg = codeMsg;
	}
	public CodeMsg getCodeMsg() {
		return codeMsg;
	}
}
