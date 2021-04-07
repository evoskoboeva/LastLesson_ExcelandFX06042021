package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Controller {
    private ObservableList<Item> pricelist = FXCollections.observableArrayList();

    @FXML
    private   TableView<Item> tableView;

    @FXML
    private TableColumn <Item, Integer> tableId;
    @FXML
    private TableColumn<Item, String> tableDescription;
    @FXML
    private TableColumn<Item,Double> tablePrice;

    @FXML
    private void initialize(ActionEvent actionEvent){
        Load();
        //pricelist.add(new Item(123,"qwerty",150));
        tableId.setCellValueFactory(new PropertyValueFactory<Item,Integer>("id"));
        tableDescription.setCellValueFactory(new PropertyValueFactory<Item,String>("title"));
        tablePrice.setCellValueFactory(new PropertyValueFactory<Item,Double>("retailPrice"));
        tableView.setItems(pricelist);


    }


    public void Load() {
        Workbook wb = null;
        String filename = "price.xls";
        OPCPackage pkg = null;

        if (filename.endsWith("xlsx") || filename.endsWith("xls")) {
            boolean isXLSX = (filename.endsWith("xlsx"));

            try {
                InputStream inp = new FileInputStream(filename);

                if (isXLSX) {

                    pkg = OPCPackage.open(inp);
                    wb = new XSSFWorkbook(pkg);
                } else {
                    wb = new HSSFWorkbook(new POIFSFileSystem(inp));


                }

                Item item = new Item();
                Cell cell;
                int id;
                String title;
                double retailPrice;


                for (Sheet sheet : wb) {
                    for (Row row : sheet) {

                        cell = row.getCell(0);
                        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
                            id = (int) cell.getNumericCellValue();
                            title = row.getCell(1).getStringCellValue();
                            retailPrice = row.getCell(2).getNumericCellValue();

                            item = new Item(id, title, retailPrice);
                            pricelist.add(item);
                            System.out.println(item);
                        }
                    }

                }
                wb.close();



                if (pkg != null) {
                    pkg.close();
                }
            } catch (FileNotFoundException | InvalidFormatException e) {
                System.out.println(e.getLocalizedMessage());
            } catch (IOException e) {
                System.out.println(e.getLocalizedMessage());
            }

            // printList("\n-------------------- All Items --------------------\n", pricelist);


           /*  printList("\n-------------------- Items with keyword: \"Adidas\" --------------------\n", SearchByTitle(pricelist, "Adidas"));

            printList("\n-------------------- Items with price under 1000 --------------------\n", SearchByPriceLower(pricelist, 1000));
*///
        /* ArrayList<Item> Logitech_cheaper_200 = SearchByPriceLower(SearchByTitle(pricelist, "Logitech"), 200);
         printList("\n-------------------- Items with keyword: \"Logitech\" and price under 200 --------------------\n", Logitech_cheaper_200);
*/

        } else {
            System.out.println("Given file is NOT Microsoft Excel file!");
        }



    }
    public static ArrayList<Item> SearchByPriceLower(ArrayList<Item> pricelist, double retailPrice) {
        ArrayList<Item> temp = new ArrayList<>();
        for (Item item : pricelist) {
            if (item.retailPrice < retailPrice) {
                //System.out.println(item);
                temp.add(item);
            }
        }
        return temp;
    }


    public static ArrayList<Item> SearchByTitle(ArrayList<Item> pricelist, String title) {
        ArrayList<Item> temp = new ArrayList<>();
        for (Item item : pricelist) {
            if (item.title.contains(title)) {
                //System.out.println(item);
                temp.add(item);
            }
        }
        return temp;
    }

    public static void printList(String header, ArrayList<Item> pricelist) {
        System.out.println(header);
        for (Item item : pricelist) {
            System.out.println(item);

        }

    }

}
