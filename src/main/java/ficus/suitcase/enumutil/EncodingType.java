package ficus.suitcase.enumutil;

/**
 * 类说明：编码枚举类
 * @author DamonFicus
 */
public enum EncodingType {
	
	/**
     * 编码UTF_8
     */
    UTF_8("UTF-8"),
    
    /**
     * 编码GBK
     */
    GBK("GBK"),
    
    /**
     * 编码GB2312
     */
    GB2312("GB2312"),
    
    /**
     * 编码GB18030
     */
    GB18030("GB18030"),
    
    /**
     * 编码ISO_8859_1
     */
    ISO_8859_1("ISO-8859-1");
    
    private String text;

	public String getText() {
		return text;
	}

	/**
	 * @param text
	 */
	private EncodingType(String text) {
		this.text = text;
	}

}
