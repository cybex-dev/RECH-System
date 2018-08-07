package models.UserSystem;

public enum UserType {
    PrimaryInvestigator(1, "PI"),
    PrimaryResponsiblePerson(2, "PRP"),
    Liaison(4, "Liaison"),
    Reviewer(3, "Reviewer"),
    FacultyRTI(5, "RTI"),
    DepartmentHead(6, "HOD"),
    RCD(10, "RCD");

    private int privilege = 1;
    private String type = "PI";

    UserType(int privilege, String type) {
        this.privilege = privilege;
        this.type = type;
    }

    public static UserType parse(String personType) {
        switch (personType) {
            case "PI": return PrimaryInvestigator;
            case "PRP": return PrimaryResponsiblePerson;
            case "Liaison": return Liaison;
            case "Reviewer": return Reviewer;
            case "RTI": return FacultyRTI;
            case "HOD": return DepartmentHead;
            case "RCD": return RCD;
        }
        return null;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrivilege() {
        return privilege;
    }
}