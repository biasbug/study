package controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@ConfigurationProperties("person")
public class Quick3Controller {
    @Value("${myname}")
    private String myname;
    @Value("${person.name}")
    private String pname;
    @Value("${persons[0].age}")
    private Integer page;

    private Integer age;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @RequestMapping("/quick3")
    public String quick3(){
        System.out.println(myname);
        System.out.println(pname);
        System.out.println(page);
        System.out.println(age);
        return "test";
    }
}
