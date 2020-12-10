public class Artefact {

    //variable declarations
    private String id; //Id to identify each artefact
    private String name; //Name of the artefact
    private Stakeholder country; //Country of origin of the artefact
    private Stakeholder owner; //Current owner of type Stakeholder

    //setter methods
    public void setId (String id){
        this.id = id;
    }
    public void setName (String name){
        this.name = name;
    }
    public void setCountry (Stakeholder country){
        this.country = country;
    }
    public void setOwner (Stakeholder owner){
        this.owner = owner;
    }
    //getter methods
    public String getId (){
        return id;
    }
    public String getName (){
        return name;
    }
    public Stakeholder getCountry (){
        return country;
    }
    public Stakeholder getOwner (){
        return owner;
    }

    //to string
    @Override
    public String toString(){
        String string = "Artefact name: " + name + ", ID: " + id + ", Country of origin: " + country.getName() + ", Current owner: " + owner.getName();
        return string;
    }

}
