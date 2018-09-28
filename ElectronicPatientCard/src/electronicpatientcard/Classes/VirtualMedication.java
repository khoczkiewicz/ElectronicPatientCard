/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package electronicpatientcard.Classes;

/**
 *
 * @author root
 */
public class VirtualMedication {

    private String name;
    private String manufacturer;
    private String form;
    private boolean isOverTheCounter;

    public VirtualMedication(String name, String manufacturer, String form, boolean isOverTheCounter) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.form = form;
        this.isOverTheCounter = isOverTheCounter;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the manufacturer
     */
    public String getManufacturer() {
        return manufacturer;
    }

    /**
     * @param manufacturer the manufacturer to set
     */
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    /**
     * @return the form
     */
    public String getForm() {
        return form;
    }

    /**
     * @param form the form to set
     */
    public void setForm(String form) {
        this.form = form;
    }

    /**
     * @return the isOverTheCounter
     */
    public boolean isIsOverTheCounter() {
        return isOverTheCounter;
    }

    /**
     * @param isOverTheCounter the isOverTheCounter to set
     */
    public void setIsOverTheCounter(boolean isOverTheCounter) {
        this.isOverTheCounter = isOverTheCounter;
    }

}
