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

    private String id;
    private String name;
    private final String surName;
    private final String maidenName;
    private final String birthDate;

    public TableRow(String name, String surName, String maidenName, String birthDate) {
        this.name = name;
        this.surName = surName;
        this.maidenName = maidenName;
        this.birthDate = birthDate;
    }

    public TableRow(String id, String name, String surName, String maidenName, String birthDate) {
        this.id = id;
        this.name = name;
        this.surName = surName;
        this.maidenName = maidenName;
        this.birthDate = birthDate;
    }

    public String getIdentificator() {
        return id;
    }

    public String getFamilyName() {
        return surName;
    }

    public String getFirstName() {
        return name;
    }

    public String getDateOfBirth() {
        return birthDate;
    }

    public String getMaidenName() {
        return maidenName;
    }
}
