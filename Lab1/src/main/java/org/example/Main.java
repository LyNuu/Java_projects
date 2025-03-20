package org.example;

import dataBase.service;

import java.util.Objects;
import java.util.Scanner;
import presentation.*;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException, Exception {

        Scanner scanner = new Scanner(System.in);
        var run = new service();
        run.Drop();
        run.Init();

        var logUser = new logParse(null);
        var withdrawalAccount = new withdrawalParse(logUser);
        var viewBalance = new viewBalanceParse(withdrawalAccount);
        var replenishmentAccount = new replenishmentParse(viewBalance);
        var createAccount = new createParse(replenishmentAccount);

        while (true) {
            String input = scanner.nextLine();
            if (Objects.equals(input, "q")) return;

            createAccount.Parse(input);
        }
        }
    }