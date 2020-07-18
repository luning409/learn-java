package org.cyg.thinking.in.spring.ioc.overview.domain;

import org.cyg.thinking.in.spring.ioc.overview.anntotation.Super;

@Super
public class SuperUser extends User {


    private String address;

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
}
