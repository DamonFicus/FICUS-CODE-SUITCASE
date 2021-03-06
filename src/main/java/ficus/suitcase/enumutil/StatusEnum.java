package ficus.suitcase.enumutil;

/**
 * Created by DamonFicus on 2018/10/9.
 * @author DamonFicus
 */
public enum StatusEnum implements CommonEnum {
    /**等待*/
    WAITTING(0,"WAITTING","等待"),
    /**开始*/
    STARTED(1,"STARTED","开始"),
    /**结束*/
    END(2,"END","结束");

    private int code;
    private String name;
    private String desc;

    StatusEnum(int code, String name, String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    @Override
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
