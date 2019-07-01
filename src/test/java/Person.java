public class Person {
    private int age;
    private String name;
    private String id;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String test(int swithPara){
        String result="";
        switch (swithPara){
         case  1: result="one";
         break;
         case  2: result="two";
         break;
         case  3: result="three";
         break;
         default: result="Parent";
        }
        return result;
    }

}
