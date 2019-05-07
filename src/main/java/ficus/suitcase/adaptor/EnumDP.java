package ficus.suitcase.adaptor;

public enum EnumDP  {

    A("a","001"),
    B("b","002"),
    C("c","003"),
    D("d","004");

    private String value;
    private String name;

    private EnumDP(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static EnumDP getEnumByValue(String value) {
        for (EnumDP e: values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }

}
