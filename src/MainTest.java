import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.InputMismatchException;

public class MainTest {

    public static void main (String[] args) {
        ArrayList<Block> blockchain = new ArrayList<Block>();
        int prefix = 4;   //we want our hash to start with four zeroes
        Scanner scnr = new Scanner(System.in);
        ArrayList<Transaction> transactionList = new ArrayList<Transaction>();
        ArrayList<Artefact> artefactList = new ArrayList<Artefact>();
        ArrayList<Stakeholder> stakeholderList = new ArrayList<Stakeholder>();


        //creating stakeholders data file
        FileInputStream stakeholdersFile = null;
        //ensure the file actually exists
        try {
            stakeholdersFile = new FileInputStream("src/stakeholdersFile");
        }
        //if file does not exist, end program
        catch (FileNotFoundException e) {
            System.out.println("Could not find and open file - exiting code; please enter the correct file name");
            System.exit(1);
        }
        //if file does exist, continue program using file
        Scanner fileReader = new Scanner(stakeholdersFile);
        //read in the first line of variables, pass over to get to the actual data
        String header = fileReader.nextLine();


        System.out.println("Initializing stakeholder database from file...");
        //create stakeholder database
        while (fileReader.hasNextLine()){
            //create new stakeholder object
            Stakeholder s = new Stakeholder();
            //read in next line
            String stakeholderText = fileReader.nextLine();
            //parse by ","
            String[] splits = stakeholderText.split(",");
            //set private variables
            s.setId(splits[0]);
            s.setName(splits[1]);
            s.setAddress(splits[2]);
            s.setBalance(Double.parseDouble(splits[3]));
            //add s to the list of stakeholders
            stakeholderList.add(s);
        }
        fileReader.close();
        System.out.print("Stakeholder database complete. Would you like to add another stakeholder? (0 for yes, 1 for no) ");
        int answer = 2;
        //ensure a 0 or 1 answer
        try {
            answer = scnr.nextInt();
            while (answer != 0 && answer != 1){
                System.out.println("Please enter either 0 for yes or 1 for no.");
                answer = scnr.nextInt();
            }
        }
        //if input not a number, continue on
        catch (InputMismatchException e) {
            System.out.println("Invalid character. No new stakeholders added.");
        }

        while(answer == 0){
            //create new stakeholder object
            Stakeholder s = new Stakeholder();
            System.out.println("Enter stakeholder data as: ID,name,address,balance (spaces allowed, commas used only to separate fields)");
            scnr.nextLine();
            //read in next line
            String stakeholderText = scnr.nextLine();
            //parse by ","
            String[] splits = stakeholderText.split(",");
            //set private variables
            s.setId(splits[0]);
            s.setName(splits[1]);
            s.setAddress(splits[2]);
            s.setBalance(Double.parseDouble(splits[3]));
            //add s to the list of stakeholders
            stakeholderList.add(s);
            //check again
            System.out.print("Stakeholder added. Would you like to add another stakeholder? (0 for yes, 1 for no)");

            answer = scnr.nextInt();
        }
        System.out.println("Stakeholder database initialized.");


        //creating artefacts data file
        FileInputStream artefactsFile = null;
        //ensure the file actually exists
        try {
            artefactsFile = new FileInputStream("src/artefactsFile");
        }
        //if file does not exist, end program
        catch (FileNotFoundException e) {
            System.out.println("Could not find and open file - exiting code; please enter the correct file name");
            System.exit(1);
        }
        //if file does exist, continue program using file
        Scanner fileReader2 = new Scanner(artefactsFile);
        //read in the first line of variables, pass over to get to the actual data
        header = fileReader2.nextLine();


        //create artefact database
        while(fileReader2.hasNextLine()){
            //create new artefact object
            Artefact a = new Artefact(); //filereader2
            Stakeholder country = new Stakeholder();
            //read in next line
            String artefactsText = fileReader2.nextLine();
            //parse by ","
            String[] splits = artefactsText.split(",");
            //set private variables
            a.setId(splits[0]);
            a.setName(splits[1]);
            country.setName(splits[2]);
            country.setAddress(splits[2]);
            country.setId(splits[3]);
            country.setBalance(0);
            a.setCountry(country);
            //add a to the list of artefacts
            artefactList.add(a);
        }
        fileReader2.close();
        System.out.print("Artefact database complete. Would you like to add another artefact? (0 for yes, 1 for no) ");
        //ensure a 0 or 1 answer
        try {
            answer = scnr.nextInt();
            while (answer != 0 && answer != 1){
                System.out.println("Please enter either 0 for yes or 1 for no.");
                answer = scnr.nextInt();
            }
        }
        //if input not a number, continue on
        catch (InputMismatchException e) {
            System.out.println("Invalid character. No new artefacts added.");
        }
        while(answer == 0){
            //create new artefact object
            Artefact a = new Artefact(); //filereader2
            Stakeholder country = new Stakeholder();
            System.out.println("Enter artefact data as: ID,name of artefact,country of origin,country ID (spaces allowed, commas used only to separate fields)");
            scnr.nextLine();
            //read in next line
            String artefactsText = scnr.nextLine();
            //parse by ","
            String[] splits = artefactsText.split(",");
            //set private variables
            a.setId(splits[0]);
            a.setName(splits[1]);
            country.setName(splits[2]);
            country.setAddress(splits[2]);
            country.setId(splits[3]);
            country.setBalance(0);
            a.setCountry(country);
            //add a to the list of artefacts
            artefactList.add(a);
            //check again
            System.out.print("Artefact added. Would you like to add another artefact? (0 for yes, 1 for no)");
            answer = scnr.nextInt();
        }
        System.out.println("Artefact database initialized.");

        //creating transactions file
        FileOutputStream transactionsFile = null;
        //ensure the file actually exists
        try {
            transactionsFile = new FileOutputStream("src/transactionsFile");
        }
        //if file does not exist, end program
        catch (FileNotFoundException e) {
            System.out.println("Could not find and open file - exiting code; please enter the correct file name");
            System.exit(1);
        }
        //if file does exist, continue program using file
        //assigning a writer for the file
        PrintWriter fileWriter = new PrintWriter(transactionsFile);


        System.out.print("Would you like to make a transaction? (0 for yes, 1 for no) ");
        //ensure a 0 or 1 answer
        try {
            answer = scnr.nextInt();
            while (answer != 0 && answer != 1){
                System.out.println("Please enter either 0 for yes or 1 for no.");
                answer = scnr.nextInt();
            }
        }
        //if input not a number, assume no transactions wanted
        catch (InputMismatchException e) {
            System.out.println("Invalid character. No new transactions will occur.");
            answer = 1;
        }

        int i = 0; //iterations
        while (answer == 0){
            Transaction dataTemp = new Transaction();

            //set buyer
            System.out.println("Who is the buyer? (enter corresponding number)");
            for (int j = 0; j < stakeholderList.size(); j++){
                System.out.print(j + ") ");
                System.out.println(stakeholderList.get(j).getName());
            }
            int num = scnr.nextInt();
            dataTemp.setBuyer(stakeholderList.get(num));

            //set artefact
            System.out.println("Which artefact are you buying? (enter corresponding number)");
            for (int j = 0; j < artefactList.size(); j++){
                System.out.print(j + ") ");
                System.out.println(artefactList.get(j).getName());
            }
            num = scnr.nextInt();
            dataTemp.setArtefact(artefactList.get(num));

            //set seller/owner
            if(dataTemp.getArtefact().getOwner() == null) {
                System.out.println("Who is the seller? (enter corresponding number)");
                for (int j = 0; j < stakeholderList.size(); j++) {
                    System.out.print(j + ") ");
                    System.out.println(stakeholderList.get(j).getName());
                }
                num = scnr.nextInt();
                dataTemp.getArtefact().setOwner(stakeholderList.get(num));
                dataTemp.setSeller(stakeholderList.get(num));
            }
            else {
                dataTemp.setSeller(dataTemp.getArtefact().getOwner());
            }

            //set auctionhouse
            System.out.println("From which auctionhouse?");
            for (int j = 0; j < stakeholderList.size(); j++){
                System.out.print(j + ") ");
                System.out.println(stakeholderList.get(j).getAddress());
            }
            num = scnr.nextInt();
            dataTemp.setAuctionHouse(stakeholderList.get(num));

            //set price
            System.out.println("Price of artefact?");
            double price = scnr.nextDouble();
            dataTemp.setPrice(price);

            //set timestamp (year)
            dataTemp.setTimestamp(Calendar.getInstance().get(Calendar.YEAR));

            //clarify what transaction is taking place
            System.out.println("Artefact in transaction: " + dataTemp.getArtefact().getName() + " from " + dataTemp.getSeller().getName() + " to " + dataTemp.getBuyer().getName() + " for $" + dataTemp.getPrice());

            Block newBlock = null;
            //first block
            if (blockchain.size() == 0){
                newBlock = new Block( dataTemp, "0", dataTemp.getTimestamp() );
            }
            //following blocks
            else {
                newBlock = new Block( dataTemp, blockchain.get(i-1).getCurrHash(), dataTemp.getTimestamp() );
            }
            //mine the block
            newBlock.mineBlock(prefix, blockchain);

            //check if valid block
            if ( newBlock.verify_Blockchain(blockchain, 0000, i, newBlock) && newBlock.TreatySC(dataTemp)){
                //add to blockchain
                blockchain.add(newBlock);
                //print to transactions file
                fileWriter.println(dataTemp.toString());

                //add transaction to the arrayList
                transactionList.add(dataTemp);

                //update the owner and seller of the artefact
                newBlock.getData().getArtefact().setOwner(newBlock.getData().getBuyer());
                newBlock.getData().setSeller(newBlock.getData().getArtefact().getOwner());

                System.out.println("Transaction completed");
                i++;
            }
            else {
                System.out.println("Malicious block, not added to the chain");
            }
            System.out.print("Would you like to add a transaction? (0 for yes, 1 for no) ");
            answer = scnr.nextInt();
        }
        //close transactions filewriter
        fileWriter.close();

    }

}
