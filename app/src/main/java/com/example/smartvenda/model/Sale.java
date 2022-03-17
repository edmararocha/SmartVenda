package com.example.smartvenda.model;

import java.io.Serializable;

public class Sale implements Serializable {
    private String id;
    private String buyer;
    private String cpf;
    private String description;
    private String value;
    private String valuePaid;
    private String thing;

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) { this.value = value; }

    public String getValuePaid() {
        return valuePaid;
    }

    public void setValuePaid(String valuePaid) {
        this.valuePaid = valuePaid;
    }

    public String getThing() {
        return thing;
    }

    public void setThing(String thing) {
        this.thing = thing;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}