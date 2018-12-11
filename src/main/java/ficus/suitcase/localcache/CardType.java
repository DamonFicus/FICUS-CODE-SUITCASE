
package ficus.suitcase.localcache;

/**
 * 类说明：<br>
 * 卡种类型
 * @author DamonFicus
 */
public enum CardType {
	/**
	 * 借记卡
	 */
	DCARD("借记卡"),
	/**
	 * 贷记卡
	 */
	CCARD("贷记卡"),
	/**
	 * 预付费卡
	 */
	PCARD("预付费卡"),
	/**
	 * 准贷记卡
	 */
	QCCARD("准贷记卡");
	
	 private String text;
	 
	 private CardType(String text) {
		 this.text = text;
	 }
	 
	 public String getText() {
		 return text;
	 }
}
