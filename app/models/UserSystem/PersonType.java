package models.UserSystem;

public enum PersonType {
    PrinicpleInvestigator("PI"),
    PrimaryResponsoblePerson("PRP"),
    Reviewer("Reviewer"),
    Liaison("Liaison"),
    FacultyRTI("RTI"),
    DepartmentHead("HoD"),
    RCD("RCD");

    private String personType;

    PersonType(String personType) {
        this.personType = personType;
    }

    public String getPersonType() {
        return personType;
    }

    public static PersonType parse(String personType){
        switch (personType) {
            case "PI" : return PrinicpleInvestigator;
            case "PRP" : return PrimaryResponsoblePerson;
            case "Reviewer" : return Reviewer;
            case "Liaison" : return Liaison;
            case "RTI" : return FacultyRTI;
            case "HoD" : return DepartmentHead;
            case "RCD" : return RCD;
        }
        return null;
    }
}
