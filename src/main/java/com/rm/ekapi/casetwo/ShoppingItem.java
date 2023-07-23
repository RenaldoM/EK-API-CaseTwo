package com.rm.ekapi.casetwo;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class ShoppingItem {

    @NotBlank
    private String objectName;

    @Min(1)
    @Max(100)
    private int quantity;

    @NotBlank
    private String createdBy;

    private LocalDateTime createdDate;

    @Future
    private LocalDateTime dueDate;

    // Konstruktor, Getter und Setter hier weggelassen für Kürze
}
