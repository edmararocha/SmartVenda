package com.example.smartvenda.model;

public class Sale {
    private String buyer;
    private int cpf;
    private String description;
    private float value;
    private int valuePaid;
    private int thing;

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public int getCpf() {
        return cpf;
    }

    public void setCpf(int cpf) {
        this.cpf = cpf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getValuePaid() {
        return valuePaid;
    }

    public void setValuePaid(int valuePaid) {
        this.valuePaid = valuePaid;
    }

    public int getThing() {
        return thing;
    }

    public void setThing(int thing) {
        this.thing = thing;
    }
}