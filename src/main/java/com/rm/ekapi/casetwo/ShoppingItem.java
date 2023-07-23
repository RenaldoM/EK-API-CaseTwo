package com.rm.ekapi.casetwo;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalDateTime;

public class ShoppingItem {

    @NotBlank
    private String objectName;

    @Min(1)
    @Max(100)
    private int quantity;

    @NotBlank
    private String creatorName;

    private LocalDateTime createdDate;

    private String dueDate;

    public ShoppingItem() {
    }

    public ShoppingItem(String name, int quantity, String creatorName, LocalDateTime now, String localDateTime) {
        this.objectName = name;
        this.quantity = quantity;
        this.creatorName = creatorName;
        this.createdDate = now;
        this.dueDate = localDateTime;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
