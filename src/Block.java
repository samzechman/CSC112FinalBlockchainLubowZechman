import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class Block {

    //variable declarations
    private String prevHash; //Hash of the previous block, an important part to build the blockchain
    private Transaction data; //The actual data, the provenance of artefacts
    private LocalDateTime timestamp; //The timestamp of the creation of this block
    private int nonce; //A nonce, which is an arbitrary (i.e. random) number used in cryptography
    private String currHash; //The hash of this block, calculated based on other data

    //getter methods
    public String getPrevHash() {
        return prevHash;
    }
    public Transaction getData() {
        return data;
    }
    public LocalDateTime getTimestamp() {
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
    public void setTimestamp(LocalDateTime timestamp) {
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
                + timestamp //ask about in class
                + Integer.toString(nonce)
                + data.toString();
        MessageDigest digest = null;
        byte[] bytes = null;
        try {
            digest = MessageDigest.getInstance("SHA-256"); //ask about in class
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
    public String mineBlock(int prefix){
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        if (TreatySC(t)){

        }
        else {
            while (!currHash.substring(0, prefix).equals(prefixString)) {
                nonce++;
                currHash = calculateBlockHash();
            }
            return currHash;
        }
    }

    //implement the agreement between the stakeholders
    public boolean TreatySC (Transaction t){
        return true;
    }

    //retrieve all transactions in the block chain for the artefact identified by its id
    public void retrieveProvenance (String id){

    }

    //retrieve all transactions in the block chain for the artefact identified by id after the date and time given through timestamp
    public void retrieveProvenance (String id, long timestamp){

    }

    //can be used by any node in the network to validate that a block chain is valid
    public boolean verify_Blockchain (ArrayList<Block> BC){

        return true;
    }



}
