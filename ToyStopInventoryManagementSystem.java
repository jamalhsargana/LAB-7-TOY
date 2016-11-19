package toystopinventorymanagementsystem;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.JOptionPane;
import java.util.Scanner;
import java.io.*;


/**
 *
 * @author Fahad Satti
 */
public class ToyStopInventoryManagementSystem implements java.io.Serializable{
    ToyStopService tsService = new ToyStopService();
    public void init(){
        
        tsService.initEmployees();
        tsService.initStores();
        tsService.initToys();
        System.out.println("Init complete");
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        
        ToyStopInventoryManagementSystem tsims = new ToyStopInventoryManagementSystem();
        tsims.init();
        
        //load previous data
        tsims.loadData();
        tsims.showMenu();
  }

    private void loadData() {
        try {
         FileInputStream fileIn = new FileInputStream("saved.ser");
         ObjectInputStream in = new ObjectInputStream(fileIn);
         this.tsService = (ToyStopService) in.readObject();
         in.close();
         fileIn.close();
      }catch(IOException i) {
          System.out.println("backup class not found");
         //i.printStackTrace();
         return;
      }catch(ClassNotFoundException c) {
        System.out.println("backup class not found");
        // c.printStackTrace();
         return;
      }
        System.out.println("loaded data");
    }

    private void showMenu() {
        int a = -1;
        while (a!=5){
        System.out.println("Welcome to Toy Stop Inventory Management System");
        System.out.println("Enter 1 to show all data");
        System.out.println("Enter 2 to add a new Store");
        System.out.println("Enter 3 to add a new Employee");
        System.out.println("Enter 4 to add a new Toy");
        System.out.println("Enter 0 to save state");
        System.out.println("Enter 5 to exit");
                Scanner in = new Scanner (System.in);
        a = in.nextInt();
        //System.out.println(a);
        if (a==1)
        {
            printAll();
        }
        else if (a==2)
        {
            tsService.addStore();
            System.out.println("store added");
        }
        
        else if (a==3)
        {
            tsService.addEmployee();
            System.out.println("employee added");
        }
        else if (a==4)
        {
            addnewtoy();
            System.out.println("toy added");
        }
        else if (a==0)
        {
            save();
        }
        else {
            break;
        }
        
   }
}

    private void printAll() {
        System.out.println(this.tsService.stores);
    }
    
    private void addnewtoy(){
        Toy newToy = new Toy();
            newToy.setUID(Util.getSaltNum(-1));
            newToy.setMinAge(Util.getSaltNum(1));
            newToy.setMaxAge(Util.getSaltNum(18));
            newToy.setPrice(Util.getSaltNum(1000));
            newToy.setName(Util.getSaltAlphaString());
            newToy.setAddedOn(LocalDateTime.now());
            
            Random randStore = new Random();
            int index = randStore.nextInt(tsService.stores.size());
            Store selectedStore = (Store)tsService.stores.get(index);
            selectedStore.addToy(newToy);
    }
    private void save(){
        try {
         FileOutputStream fileOut =
         new FileOutputStream("saved.ser");
         ObjectOutputStream out = new ObjectOutputStream(fileOut);
         out.writeObject(this.tsService);
         out.close();
         fileOut.close();
         System.out.printf("Serialized data is saved \n");
      }catch(IOException i) {
         i.printStackTrace();
      }  
    }
}
