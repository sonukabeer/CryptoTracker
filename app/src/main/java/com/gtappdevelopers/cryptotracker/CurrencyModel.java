 package com.gtappdevelopers.cryptotracker;
    public class CurrencyModel {
        // variable for currency name,
        // currency symbol and price.
        private String name;
        private final String symbol;
        private final double price;
        public CurrencyModel(String name, String symbol, double price) {
            this.name = name;
            this.symbol = symbol;
            this.price = price;
        }
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getSymbol() {
            return symbol;
        }

        public double getPrice() {
            return price;
        }
    }
