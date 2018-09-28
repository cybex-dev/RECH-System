package models.UserSystem;

public enum UserType {
    PrimaryInvestigator(1, "PI", "Primary Investigator"),
    PrimaryResponsiblePerson(2, "PRP", "Primary Responsible Person"),
    Liaison(4, "Liaison", "Liaison"),
    Reviewer(3, "Reviewer", "Reviewer"),
    FacultyRTI(5, "RTI", "Faculty RTI"),
    DepartmentHead(6, "HOD", "Head of Department"),
    RCD(10, "RCD", "Research Capacity Development");

    private int privilege = 1;
    private String type = "PI";
    private String description = "Primary Investigator";

    UserType(int privilege, String type, String description) {
        this.privilege = privilege;
        this.type = type;
        this.description = description;
    }

    public static UserType parse(String personType) {
        switch (personType) {
            case "PrimaryInvestigator":
            case "PI": return PrimaryInvestigator;
            case "PrimaryResponsiblePerson":
            case "PRP": return PrimaryResponsiblePerson;
            case "Liaison": return Liaison;
            case "Reviewer": return Reviewer;
            case "FacultyRTI":
            case "RTI": return FacultyRTI;
            case "HOD":
            case "DepartmentHead": return DepartmentHead;
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

    public String getDescription() {
        return description;
    }
}