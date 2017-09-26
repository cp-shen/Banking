package banking.domain;

import banking.domain.Account;

/**
 * @author BJTU16301130
 * @version 201270922
 */
public class CheckingAccount extends Account {
        private double overdraftProtection;
        public CheckingAccount(double balance){
            super(balance);
        }
        public CheckingAccount(double balance, double protect){
            super(balance);
            overdraftProtection = protect;
        }

        public boolean withdraw(double amt){
            if(balance >= amt){
                balance -= amt;
                return true;
            }
            else if(balance + overdraftProtection >= amt){
                overdraftProtection -= amt - balance;
                balance = 0;
                return true;
            }
            else return false;
        }
    }

