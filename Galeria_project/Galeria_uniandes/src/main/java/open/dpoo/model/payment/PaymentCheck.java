package main.java.open.dpoo.model.payment;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import main.java.open.dpoo.model.usuarios.BaseUser;
import main.java.open.dpoo.model.usuarios.Cashier;

public class PaymentCheck {

    public String accountNum;

    private String paymentGateways;

    private Cashier cashier;

    private BaseUser user;

    private double amount;

    private String transactionNum;

    private Boolean success;

    public PaymentCheck( Cashier cashier, BaseUser user, String paymentGateways, String acountNum, double amount){

        this.cashier = cashier;
        this.user = user;
        this.paymentGateways = paymentGateways;
        this.accountNum = acountNum;
        this.amount = amount;
        
    }

    public PaymentCheck pay() {
        try {
            Class<?> clase = Class.forName(paymentGateways);
            Constructor<?> constructor = clase.getDeclaredConstructor(BaseUser.class, String.class);

            constructor.setAccessible(true); 

            Gateway gateway = (Gateway) constructor.newInstance(user, accountNum);

            this.transactionNum = cashier.generateTransactionNumber(gateway);

            this.success = gateway.pay(accountNum, amount, user, transactionNum);
            
            return this;
        } catch (ClassNotFoundException e) {
            System.out.println("No existe la clase " + paymentGateways);
        } catch (NoSuchMethodException e) {
            System.out.println("No se encontró el constructor adecuado en " + paymentGateways);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.println("Hubo un error al instanciar " + paymentGateways + ": " + e.getMessage());
            e.printStackTrace();
        }
        return null; // Devolver null en caso de error
    }


    }
