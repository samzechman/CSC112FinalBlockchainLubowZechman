import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;

public class Main {
    public static void main (String[] args) {
        ArrayList<Block> blockchain = new ArrayList<>();
        int prefix = 4;   //we want our hash to start with four zeroes
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        Scanner scnr = new Scanner(System.in);

        //data1 - data3 created
        Transaction data1 = new Transaction();
        Transaction data2 = new Transaction();
        Transaction data3 = new Transaction();

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

        //read in stakeholder data
        while (fileReader.hasNextLine()) {
            //creates the Stakeholder objects
            Stakeholder s = new Stakeholder();

            //read in next line
            String text = fileReader.nextLine();

            //parse by ","
            String[] splits = text.split(",");

            //set private variables
            s.setId(splits[0]);
            s.setName(splits[1]);
            s.setAddress(splits[2]);
            s.setBalance(Double.parseDouble(splits[3]));

            //fill in data1 - data3
            if (data1.getBuyer() == null) {
                data1.setBuyer(s);
            } else if (data1.getSeller() == null) {
                data1.setSeller(s);
            } else if (data1.getAuctionHouse() == null) {
                data1.setAuctionHouse(s);
            } else if (data2.getBuyer() == null) {
                data2.setBuyer(s);
            } else if (data2.getSeller() == null) {
                data2.setSeller(s);
            } else if (data2.getAuctionHouse() == null) {
                data2.setAuctionHouse(s);
            } else if (data3.getBuyer() == null) {
                data3.setBuyer(s);
            } else if (data3.getSeller() == null) {
                data3.setSeller(s);
            } else if (data3.getAuctionHouse() == null) {
                data3.setAuctionHouse(s);
                break;
            }
        }
        fileReader.close();

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

        fileReader = new Scanner(artefactsFile);

        //read in the first line of variables, pass over to get to the actual data
        header = fileReader.nextLine();

        //read in artefact data
        while (fileReader.hasNextLine()) {
            //creates the Artefact objects
            Artefact a = new Artefact();

            //read in next line
            String text = fileReader.nextLine();

            //parse by ","
            String[] splits = text.split(",");

            //set private variables
            a.setId(splits[0]);
            a.setName(splits[1]);
            a.setCountry(splits[2]);

            //fill in data1 - data3
            if (data1.getArtefact() == null) {
                a.setOwner(data1.getSeller());
                data1.setArtefact(a);
            } else if (data2.getArtefact() == null) {
                a.setOwner(data2.getSeller());
                data2.setArtefact(a);
            } else if (data3.getArtefact() == null) {
                a.setOwner(data3.getSeller());
                data3.setArtefact(a);
                break;
            }
        }
        fileReader.close();

        //data1-data3 filled from text files

        //could add option for user to add a new artefact/stakeholder

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


        //adding first block
        data1.setTimestamp(Calendar.getInstance().get(Calendar.YEAR));
        System.out.print("Enter price of transaction: $");
        data1.setPrice(scnr.nextDouble());
        Block genesisBlock = new Block(data1, "0", data1.getTimestamp());
        genesisBlock.mineBlock(prefix, blockchain);

        blockchain.add(genesisBlock);
        System.out.println(data1.toString());
        fileWriter.println(data1.toString());
        /*
        if (genesisBlock.getCurrHash().substring(0, prefix).equals(prefixString) && genesisBlock.verify_Blockchain(blockchain, prefix)){
            blockchain.add(genesisBlock);
            fileWriter.println(data1.toString());
        }
        else
            System.out.println("Malicious block, not added to the chain");*/

        //adding second block
        data2.setTimestamp(Calendar.getInstance().get(Calendar.YEAR));
        Block secondBlock = new Block(data2, blockchain.get(blockchain.size() - 1).getCurrHash(), data2.getTimestamp());
        secondBlock.mineBlock(prefix, blockchain);
        if (secondBlock.getCurrHash().substring(0, prefix).equals(prefixString) && secondBlock.verify_Blockchain(blockchain, prefix))
            blockchain.add(secondBlock);
        else
            System.out.println("Malicious block, not added to the chain");

        //adding third block
        data3.setTimestamp(Calendar.getInstance().get(Calendar.YEAR));
        Block newBlock = new Block(data3, blockchain.get(blockchain.size() - 1).getCurrHash(), data3.getTimestamp());
        newBlock.mineBlock(prefix, blockchain);
        if (newBlock.getCurrHash().substring(0, prefix).equals(prefixString) && newBlock.verify_Blockchain(blockchain, prefix))
            blockchain.add(newBlock);
        else
            System.out.println("Malicious block, not added to the chain");

    }

}
