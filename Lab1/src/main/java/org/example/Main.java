package org.example;

import dataBase.Service;

import java.util.Objects;
import java.util.Scanner;
import presentation.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        var run = new Service();
        run.drop();
        run.init();

        var logUser = new LogParse(null);
        var withdrawalAccount = new WithdrawalParse(logUser);
        var viewBalance = new ViewBalanceParse(withdrawalAccount);
        var replenishmentAccount = new ReplenishmentParse(viewBalance);
        var createAccount = new CreateParse(replenishmentAccount);

        while (true) {
            String input = scanner.nextLine();
            if (Objects.equals(input, "q")) return;
            createAccount.parse(input);
        }
    }
}