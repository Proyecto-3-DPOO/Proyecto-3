package main.java.open.dpoo.model.payment;

import java.util.HashMap;

import main.java.open.dpoo.model.usuarios.BaseUser;

public abstract class Gateway {

    protected  HashMap<String, HashMap<String, Object>> accounts;

    protected  String issue;

    public abstract Boolean pay(String accountNum, double amount, BaseUser user, String transactionNum);

    public  HashMap<String, HashMap<String, Object>> getAccounts(){
        return accounts;
    }
}
