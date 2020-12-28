package org.cyg.thinking.in.spring.ioc.overview.domain;

import org.cyg.thinking.in.spring.ioc.overview.anntotation.Super;
import org.springframework.beans.factory.BeanNameAware;

@Super
public class SuperUser extends User implements BeanNameAware {


    private String address;

    private String beanName;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = name;
        System.out.println(this.beanName + " Aware exec!");
    }
}
