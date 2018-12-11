package ficus.suitcase.enumutil;
/**
 * 通过提供getEnumByValue这样的处理方法，方便根据值找期对应的描述的场景
 * 这种场景的应用，比如在导出excel的时候，需要根据值转换成对应的中文描述
 * 的需求；
 * @author DamonFicus 20181025
 */
public enum AreaEnum {
	/**
	 * 同城
	 */
	AREA_IN_CITY("Y","同城"),
	/**
	 * 异地
	 */
	AREA_OUT_CITY("N","异地"),
	/**
	 * 同城和异地
	 */
	AREA_ALL("A","同城和异地");

	private String value;
	private String name;

	private AreaEnum(String value, String name) {
		this.value = value;
		this.name = name;
	}
	
	public String getValue() {
		return value;
	}
	
	public String getName() {
		return name;
	}
	
	public static AreaEnum getEnumByValue(String value) {
		for (AreaEnum e: values()) {
			if (e.value.equals(value)) {
				return e;
			}
 		}
		return null;
	}
}
