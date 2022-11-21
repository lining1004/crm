package com.briup.crmcustomer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author briup
 * @since 2022-11-15
 */
@TableName("cst_customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "cust_id", type = IdType.AUTO)
    private Long id;

    @TableField("cust_name")
    private String name;

    @TableField("cust_region")
    private String region;

    @TableField("cust_manager_id")
    private Long managerId;

    @TableField("cust_manager_name")
    private String managerName;

    @TableField("cust_level_label")
    private String levelLabel;

    @TableField("cust_satisfy")
    private Integer satisfy;

    @TableField("cust_credit")
    private Integer credit;

    @TableField("cust_addr")
    private String addr;

    @TableField("cust_zip")
    private String zip;

    @TableField("cust_tel")
    private String tel;

    @TableField("cust_fax")
    private String fax;

    @TableField("cust_website")
    private String website;

    @TableField("cust_licence_no")
    private String licenceNo;

    @TableField("cust_chieftain")
    private String chieftain;

    @TableField("cust_bankroll")
    private Double bankroll;

    @TableField("cust_turnover")
    private Double turnover;

    @TableField("cust_bank")
    private String bank;

    @TableField("cust_bank_account")
    private String bankAccount;

    @TableField("cust_local_tax_no")
    private String localTaxNo;

    @TableField("cust_national_tax_no")
    private String nationalTaxNo;

    @TableField("cust_status")
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public Long getManagerId() {
        return managerId;
    }

    public void setManagerId(Long managerId) {
        this.managerId = managerId;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getLevelLabel() {
        return levelLabel;
    }

    public void setLevelLabel(String levelLabel) {
        this.levelLabel = levelLabel;
    }

    public Integer getSatisfy() {
        return satisfy;
    }

    public void setSatisfy(Integer satisfy) {
        this.satisfy = satisfy;
    }

    public Integer getCredit() {
        return credit;
    }

    public void setCredit(Integer credit) {
        this.credit = credit;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getLicenceNo() {
        return licenceNo;
    }

    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }

    public String getChieftain() {
        return chieftain;
    }

    public void setChieftain(String chieftain) {
        this.chieftain = chieftain;
    }

    public Double getBankroll() {
        return bankroll;
    }

    public void setBankroll(Double bankroll) {
        this.bankroll = bankroll;
    }

    public Double getTurnover() {
        return turnover;
    }

    public void setTurnover(Double turnover) {
        this.turnover = turnover;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getLocalTaxNo() {
        return localTaxNo;
    }

    public void setLocalTaxNo(String localTaxNo) {
        this.localTaxNo = localTaxNo;
    }

    public String getNationalTaxNo() {
        return nationalTaxNo;
    }

    public void setNationalTaxNo(String nationalTaxNo) {
        this.nationalTaxNo = nationalTaxNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id = " + id +
                ", name = " + name +
                ", region = " + region +
                ", managerId = " + managerId +
                ", managerName = " + managerName +
                ", levelLabel = " + levelLabel +
                ", satisfy = " + satisfy +
                ", credit = " + credit +
                ", addr = " + addr +
                ", zip = " + zip +
                ", tel = " + tel +
                ", fax = " + fax +
                ", website = " + website +
                ", licenceNo = " + licenceNo +
                ", chieftain = " + chieftain +
                ", bankroll = " + bankroll +
                ", turnover = " + turnover +
                ", bank = " + bank +
                ", bankAccount = " + bankAccount +
                ", localTaxNo = " + localTaxNo +
                ", nationalTaxNo = " + nationalTaxNo +
                ", status = " + status +
                "}";
    }
}
