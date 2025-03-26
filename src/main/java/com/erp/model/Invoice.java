package com.erp.model;

import com.erp.model.enums.InvoiceStatus;
import com.erp.model.enums.InvoiceType;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Invoice")
@Data
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private InvoiceType invoiceType;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = true)
    private Client client;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = true)
    private Supplier supplier;

    @Enumerated(EnumType.STRING)
    private InvoiceStatus status;

    private BigDecimal totalAmount;

    private LocalDateTime createdAt;

    private LocalDate dueDate;


}
