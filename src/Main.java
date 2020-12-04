import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

public class Main {
    public static void main (String[] args) {
        ArrayList<Block> blockchain = new ArrayList<>();
        int prefix = 4;   //we want our hash to start with four zeroes
        Scanner scnr = new Scanner(System.in);
        ArrayList<Transaction> data = new ArrayList<Transaction>();

        //creating stakeholders file
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

        //creating artefacts file
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

        //read in stakeholder data
        while (fileReader.hasNextLine() && fileReader2.hasNextLine()) {

            //creates Transaction
            Transaction dataTemp = new Transaction();

            //creates the Stakeholder objects
            Stakeholder seller = new Stakeholder(); //filereader
            Stakeholder buyer = new Stakeholder(); //filereader
            Stakeholder auctionHouse = new Stakeholder(); //filereader
            Stakeholder country = new Stakeholder(); //filereader2
            //creates the Artefact objects
            Artefact a = new Artefact(); //filereader2

            //read in next line
            String stakeholderText = fileReader.nextLine();
            //parse by ","
            String[] splits = stakeholderText.split(",");
            //set private variables
            seller.setId(splits[0]);
            seller.setName(splits[1]);
            seller.setAddress(splits[2]);
            seller.setBalance(Double.parseDouble(splits[3]));

            //read in next line
            stakeholderText = fileReader.nextLine();
            //parse by ","
            splits = stakeholderText.split(",");
            //set private variables
            buyer.setId(splits[0]);
            buyer.setName(splits[1]);
            buyer.setAddress(splits[2]);
            buyer.setBalance(Double.parseDouble(splits[3]));

            //read in next line
            stakeholderText = fileReader.nextLine();
            //parse by ","
            splits = stakeholderText.split(",");
            //set private variables
            auctionHouse.setId(splits[0]);
            auctionHouse.setName(splits[1]);
            auctionHouse.setAddress(splits[2]);
            auctionHouse.setBalance(Double.parseDouble(splits[3]));

            //read in next line
            String artefactsText = fileReader2.nextLine();
            //parse by ","
            splits = artefactsText.split(",");

            //set private variables
            a.setId(splits[0]);
            a.setName(splits[1]);
            country.setName(splits[2]);
            country.setAddress(splits[2]);
            country.setId(splits[3]);
            country.setBalance(0);
            a.setCountry(country);
            a.setOwner(dataTemp.getSeller());

            dataTemp.setArtefact(a);
            dataTemp.setPrice(0); //initialize
            dataTemp.setTimestamp(0); //initialize
            dataTemp.setSeller(seller);
            dataTemp.setBuyer(buyer);
            dataTemp.setAuctionHouse(auctionHouse);
            data.add(dataTemp);

        }

        fileReader.close();
        fileReader2.close();


        //could add option for user to add a new artefact/stakeholder
        //System.out.println("Would you like to add another Stakeholder to the database?");
        //System.out.println("Would you like to add another artefact to the database?");
        //fill in

////need to figure out how to make this not clear each time the code is run
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


        System.out.println("Would you like to add a transaction? (0 for yes, 1 for no)");
        int answer = scnr.nextInt();
        int i = 0;
        while (answer == 0){

            System.out.println("Artefact in transaction: " + data.get(i).getArtefact().getName() + " from " + data.get(i).getSeller().getName() + " to " + data.get(i).getBuyer().getName());
            System.out.print("Enter price of transaction: $");
            data.get(i).setPrice(scnr.nextDouble());
            data.get(i).setTimestamp(Calendar.getInstance().get(Calendar.YEAR));

            Block newBlock = null;
            if (blockchain.size() == 0){
                newBlock = new Block(data.get(i), "0", data.get(i).getTimestamp());
            }
            else if (blockchain.size() >= 1 && blockchain.size() < data.size() && i >=1 ){
                newBlock = new Block( data.get(i), blockchain.get(i-1).getCurrHash(), data.get(i).getTimestamp() );
            }
////problem with i or the iteration maybe? or prev hash?
            else { //if i is 0 and blockchain already has values
                newBlock = new Block( data.get(i), blockchain.get(i).getPrevHash(), data.get(i).getTimestamp() );
            }

            //Block newBlock = new Block(data.get(i), "0", data.get(i).getTimestamp());
            newBlock.mineBlock(prefix, blockchain);
            //check if valid block
            if (newBlock.verify_Blockchain(blockchain, 0000)){
                blockchain.add(newBlock);
                fileWriter.println(data.get(i).toString());

                System.out.println("Transaction completed");
            }
            else
                System.out.println("Malicious block, not added to the chain");

            //iterate through data, cycles back to start if at end
            if (i < data.size() - 1) {
                i++;
            }
            else {
                i = 0;
            }

            System.out.println("Would you like to add a transaction? (0 for yes, 1 for no)");
            answer = scnr.nextInt();
        }


/*
        //adding first block
        System.out.println("Artefact in transaction: " + dataTemp.getArtefact().getName() + " from " + dataTemp.getSeller().getName() + " to " + dataTemp.getBuyer().getName());
        System.out.print("Enter price of transaction: $");
        dataTemp.setPrice(scnr.nextDouble());
        dataTemp.setTimestamp(Calendar.getInstance().get(Calendar.YEAR));
        Block genesisBlock = new Block(dataTemp, "0", dataTemp.getTimestamp());
        genesisBlock.mineBlock(prefix, blockchain);
        //check if valid block
        if (genesisBlock.verify_Blockchain(blockchain, prefix)){
            blockchain.add(genesisBlock);
            fileWriter.println(dataTemp.toString());
            System.out.println("Transaction completed");
        }
        else
            System.out.println("Malicious block, not added to the chain");

        //adding second block
        System.out.println("Artefact in transaction: " + data2.getArtefact().getName() + " from " + data2.getSeller().getName() + " to " + data2.getBuyer().getName());
        System.out.print("Enter price of transaction: $");
        data2.setPrice(scnr.nextDouble());
        data2.setTimestamp(Calendar.getInstance().get(Calendar.YEAR));
        Block secondBlock = new Block(data2, blockchain.get(blockchain.size() - 1).getCurrHash(), data2.getTimestamp());
        secondBlock.mineBlock(prefix, blockchain);
        //check if valid block
        if (secondBlock.verify_Blockchain(blockchain, prefix)){
            blockchain.add(secondBlock);
            fileWriter.println(data2.toString());
            System.out.println("Transaction completed");
        }
        else
            System.out.println("Malicious block, not added to the chain");

        //adding third block
        System.out.println("Artefact in transaction: " + data3.getArtefact().getName() + " from " + data3.getSeller().getName() + " to " + data3.getBuyer().getName());
        System.out.print("Enter price of transaction: $");
        data3.setPrice(scnr.nextDouble());
        data3.setTimestamp(Calendar.getInstance().get(Calendar.YEAR));
        Block newBlock = new Block(data3, blockchain.get(blockchain.size() - 1).getCurrHash(), data3.getTimestamp());
        newBlock.mineBlock(prefix, blockchain);
        //check if valid block
        if (newBlock.verify_Blockchain(blockchain, prefix)){
            blockchain.add(newBlock);
            fileWriter.println(data3.toString());
            System.out.println("Transaction completed");
        }
        else
            System.out.println("Malicious block, not added to the chain");
*/
        fileWriter.close();
    }

}
