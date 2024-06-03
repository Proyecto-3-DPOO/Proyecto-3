package main.java.open.dpoo.model.payment;


import java.util.HashMap;


import main.java.open.dpoo.exception.IncorrectOfferException;
import main.java.open.dpoo.model.usuarios.BaseUser;

public class PayPal extends Gateway {

    private static HashMap<String, HashMap<String, Object>> accounts = new HashMap<>(); //NunCuenta: {nombre: 0, saldo: null, verficida: null}

    public PayPal(){
        super();

    }

    @Override
    public Boolean pay(String accountNum, double amount, BaseUser user, String transactionNum) throws IncorrectOfferException{

        if(accounts.containsKey(accountNum)){
            HashMap<String, Object> account = accounts.get(accountNum);
            if((boolean) account.get("verificada")){
                account.put("saldo", (double) account.get("saldo") - amount);
                return true;
            }else{
                issue = "Cuenta no verificada";
                return false;
            }
        }
        else{
            issue = "Cuenta no encontrada en registro";
            throw new IncorrectOfferException("Cuenta no encontrada");

        }
    }
}
