package com.briup.crmmarketing.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * <p>
 * 
 * </p>
 *
 * @author briup
 * @since 2022-11-21
 */
@TableName("sal_contract")
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableField("cont_id")
    private Long id;

    @TableField("cont_code")
    private String code;

    @TableField("cont_note")
    private String note;

    @TableField("cont_many")
    private Object many;

    @TableField("cont_start_date")
    private LocalDate startDate;

    @TableField("cont_end_date")
    private LocalDate endDate;

    @TableField("cont_customer_id")
    private Integer customerId;
    
    public Long getId() {
        return id;
    }

      public void setId(Long id) {
          this.id = id;
      }
    
    public String getCode() {
        return code;
    }

      public void setCode(String code) {
          this.code = code;
      }
    
    public String getNote() {
        return note;
    }

      public void setNote(String note) {
          this.note = note;
      }
    
    public Object getMany() {
        return many;
    }

      public void setMany(Object many) {
          this.many = many;
      }
    
    public LocalDate getStartDate() {
        return startDate;
    }

      public void setStartDate(LocalDate startDate) {
          this.startDate = startDate;
      }
    
    public LocalDate getEndDate() {
        return endDate;
    }

      public void setEndDate(LocalDate endDate) {
          this.endDate = endDate;
      }
    
    public Integer getCustomerId() {
        return customerId;
    }

      public void setCustomerId(Integer customerId) {
          this.customerId = customerId;
      }

    @Override
    public String toString() {
        return "Contract{" +
              "id = " + id +
                  ", code = " + code +
                  ", note = " + note +
                  ", many = " + many +
                  ", startDate = " + startDate +
                  ", endDate = " + endDate +
                  ", customerId = " + customerId +
              "}";
    }
}
