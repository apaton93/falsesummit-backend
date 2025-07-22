package com.company.falsesummit.model;

public class TariffRate {
    String tariff_name;
    String element_name;
    double prop1;
    double prop2;
    double prop3;

    public TariffRate(String tariff_name, String element_name, double prop1, double prop2, double prop3) {

        this.tariff_name = tariff_name;
        this.element_name = element_name;
        this.prop1 = prop1;
        this.prop2 = prop2;
        this.prop3 = prop3;
    }

}
