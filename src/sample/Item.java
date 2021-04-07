package sample;

import java.util.Currency;
import java.util.Locale;

public class Item {
   int id;
   String title;
   double retailPrice;


   public Item() {
      this.id = -1;
      this.retailPrice = 0;
      this.title = "";

   }

   public Item(int id, String title, double retailPrice ) {
      this.id = id;
      this.retailPrice = retailPrice;
      this.title = title;
   }

   @Override
   public String toString() {
        /*
        for (Locale locale : Locale.getAvailableLocales()) {
            try {
                Currency currency = Currency.getInstance(locale);
                System.out.println(locale + " / " + currency);
            } catch (Exception e) {
            }
        }
*/
      // Locale locale=Locale.getDefault();
      Locale locale = new Locale("uk_ua", "UA"); //("en", "US");

      Currency currency = Currency.getInstance(locale);
      String symbol = currency.getSymbol();

      return String.format("%8d", id) + "\t" +
              String.format("%-70s", this.title) + "\t" +
              String.format("%10.1f", this.retailPrice) + ' ' + symbol;

   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public double getRetailPrice() {
      return retailPrice;
   }

   public void setRetailPrice(double retailPrice) {
      this.retailPrice = retailPrice;
   }
}
