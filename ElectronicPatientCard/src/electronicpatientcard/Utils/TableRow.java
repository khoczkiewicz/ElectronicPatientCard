/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicpatientcard.Utils;

/**
 *
 * @author root
 */
public class TableRow {

    private String firstName;
    private final String familyName;
    private final String maidenName;
    private final String dateOfBirth;

    public TableRow(String firstName, String familyName, String maidenName, String dateOfBirth) {
        this.firstName = firstName;
        this.familyName = familyName;
        this.maidenName = maidenName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getFamilyName() {
        return familyName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public String getMaidenName() {
        return maidenName;
    }
}
