package ficus.suitcase.designPattern.composite;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 01333346 on 2018/10/18.
 * 以员工及下属的树形关系来描述组合模式
 */
public class Employee {

    private String name;
    private String dept;
    private int    salary;
    private List<Employee> subordinates;

    public Employee(String name,String dept,int salary){
        this.name=name;
        this.dept=dept;
        this.salary=salary;
        subordinates=new ArrayList<Employee>();
    }

    public void add(Employee employee){
        subordinates.add(employee);
    }
    public void remove(Employee employee){
        subordinates.remove(employee);
    }
    public List<Employee> getSubordinates(){
        return subordinates;
    }
    @Override
    public String toString(){
        return ("Employee :[ Name : "+ name
                +", dept : "+ dept + ", salary :"
                + salary+" ]");
    }


}
