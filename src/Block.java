import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Block {

    //variable declarations
    private String prevHash; //Hash of the previous block, an important part to build the blockchain
    private Transaction data; //The actual data, the provenance of artefacts
    private int timestamp; //The year of the creation of this block
    private int nonce; //A nonce, which is an arbitrary (i.e. random) number used in cryptography
    private String currHash; //The hash of this block, calculated based on other data

    //default constructor
    public Block (){
        prevHash = "";
        data = null;
        timestamp = 0;
        nonce = 0;
        currHash = "";
    }

    //copy constructor
    public Block (Block b){
        this.prevHash = b.prevHash;
        this.data = b.data;
        this.timestamp = b.timestamp;
        this.nonce = b.nonce;
        this.currHash = b.currHash;
    }

    //parameterized constructor
    public Block (Transaction data, String prevHash, int year){
        this.nonce = (int)Math.random();
        this.prevHash = prevHash;
        this.data = data;
        this.timestamp = year;
        this.currHash = calculateBlockHash();
    }


    //getter methods
    public String getPrevHash() {
        return prevHash;
    }
    public Transaction getData() {
        return data;
    }
    public int getTimestamp() {
        return timestamp;
    }
    public int getNonce() {
        return nonce;
    }
    public String getCurrHash() {
        return currHash;
    }
    //setter methods
    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }
    public void setData(Transaction data) {
        this.data = data;
    }
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
    public void setNonce(int nonce) {
        this.nonce = nonce;
    }
    public void setCurrHash(String currHash) {
        this.currHash = currHash;
    }

    //calculate block hash
    public String calculateBlockHash() {
        String dataToHash = prevHash
                + timestamp
                + Integer.toString(nonce)
                + data.toString();
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            System.out.println("The encoding is not supported");
        }
        StringBuffer buffer = new StringBuffer();
        for (byte b : bytes) {
            buffer.append(String.format("%02x", b));
        }
        return buffer.toString();
    }

    //method to mine the blocks
    public String mineBlock(int prefix, ArrayList<Block> BC){
        //Define the prefix you desire to find
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        //If the transaction meets the stakeholders agreement in TreatySC, mine the block;
        //otherwise abort the transaction and display a proper message.
        if (BC.size() > 2 && !TreatySC(data)){
            System.out.println("This transaction does not meet the stakeholders' agreement -- block not valid.");
        }
        else {
            //Check whether you've found the solution
            //If not, increment the nonce and calculate the hash in a loop
            while (!currHash.substring(0, prefix).equals(prefixString)) {
                nonce++;
                currHash = calculateBlockHash();
            }
            System.out.println("block mined");
            System.out.println(currHash);
        }
        return currHash;
    }

    //implement the agreement between the stakeholders
    public boolean TreatySC (Transaction t){
        if (/*retrieveProvenance(data.getArtefact().getId(), 2001).size() >= 2 &&*/ data.getBuyer().getBalance() >= data.getPrice()){
            //10% of sale given to the auction house
            data.getAuctionHouse().setBalance(data.getAuctionHouse().getBalance() + data.getPrice() * 0.1);
            //20% of sale to country of origin
            data.getArtefact().getCountry().setBalance(data.getArtefact().getCountry().getBalance() + data.getPrice() * 0.2);
            //70% of sale given to the seller
            data.getSeller().setBalance(data.getSeller().getBalance() + data.getPrice() * 0.7);
            return true;
        }
        return false;
    }

    //retrieve all transactions in the block chain for the artefact identified by its id
    public ArrayList<Transaction> retrieveProvenance (String id){
        FileInputStream transactionsFile = null;
        //ensure the file actually exists
        try {
            transactionsFile = new FileInputStream("src/transactionsFile");
        }
        //if file does not exist, end program
        catch (FileNotFoundException e) {
            System.out.println("Could not find and open file - exiting code; please enter the correct file name");
            System.exit(1);
        }
        //if file does exist, continue program using file
        Scanner fileReader = new Scanner(transactionsFile);

        //create array for the transactions
        ArrayList<Transaction> transactions = new ArrayList<>();

        //parse the text file of transactions
        while(fileReader.hasNextLine()){
            //if the id provided matches the artefact's id, add to the array
            if (data.getArtefact().getId() == id){
                transactions.add(data);
            }
        }
        return transactions;
    }

    //retrieve all transactions in the block chain for the artefact identified by id after the date and time given through timestamp
    public ArrayList<Transaction> retrieveProvenance (String id, int timestamp){
        FileInputStream transactionsFile = null;
        //ensure the file actually exists
        try {
            transactionsFile = new FileInputStream("src/transactionsFile");
        }
        //if file does not exist, end program
        catch (FileNotFoundException e) {
            System.out.println("Could not find and open file - exiting code; please enter the correct file name");
            System.exit(1);
        }
        //if file does exist, continue program using file
        Scanner fileReader = new Scanner(transactionsFile);

        //create array for the transactions
        ArrayList<Transaction> transactions = new ArrayList<>();

        //parse the text file of transactions
        while(fileReader.hasNextLine()){
            //if the id provided matches the artefact's id, add to the array
            if (data.getArtefact().getId() == id && data.getTimestamp() > timestamp){
                transactions.add(data);
            }
        }
        return transactions;
    }

    //can be used by any node in the network to validate that a block chain is valid
    public boolean verify_Blockchain (ArrayList<Block> BC, int prefix){

        Block currentBlock;
        Block previousBlock;
        //iterating through all the blocks
        for (int i = 1; i < BC.size(); i++) {
            //storing the current block and the previous block
            currentBlock = BC.get(i);
            previousBlock = BC.get(i - 1);

            //checking if the current hash is equal to the calculated hash or not
            if (!currentBlock.currHash.equals(currentBlock.calculateBlockHash())) {
                //hashes not equal
                System.out.println("first");
                return false;
            }
            //checking if the previous hash is equal to the calculated previous hash or not
            if (!previousBlock.currHash.equals(currentBlock.prevHash)) {
                //prev hashes not equal
                System.out.println(previousBlock.currHash);
                System.out.println(currentBlock.prevHash);
                System.out.println("second");
                return false;
            }
            //checking if the current block has been mined
            if ( currentBlock.getCurrHash().substring(0, prefix).equals(0000) ) {
                //block has not been mined
                System.out.println("third");
                return false;
            }
        }
        // If all the hashes are equal to the calculated hashes, then blockchain is valid
        return true;
    }


}
