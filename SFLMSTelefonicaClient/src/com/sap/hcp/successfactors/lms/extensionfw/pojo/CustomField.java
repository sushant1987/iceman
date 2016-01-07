package com.sap.hcp.successfactors.lms.extensionfw.pojo;

public class CustomField implements Comparable<CustomField> {

    private Long id;

    private String dataType;

    private String name;

    private int length;

    private boolean hasValuelist;

    private String shortDesc;

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType
     *            the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the length
     */
    public int getLength() {
        return length;
    }

    /**
     * @param length
     *            the length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

    /**
     * @return the hasValuelist
     */
    public boolean isHasValuelist() {
        return hasValuelist;
    }

    /**
     * @param hasValuelist
     *            the hasValuelist to set
     */
    public void setHasValuelist(boolean hasValuelist) {
        this.hasValuelist = hasValuelist;
    }

    /**
     * @return the shortDesc
     */
    public String getShortDesc() {
        return shortDesc;
    }

    /**
     * @param shortDesc
     *            the shortDesc to set
     */
    public void setShortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    @Override
    public int compareTo(CustomField o) {
        return this.getId().compareTo(o.getId());
    }

}
