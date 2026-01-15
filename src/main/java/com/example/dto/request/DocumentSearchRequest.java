package com.example.dto.request;

import com.example.entity.enums.DocumentStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class DocumentSearchRequest {

    @NotBlank(message = "Tên không được để trống !")
    private String name;

    @NotBlank(message = "Người tạo không được để trống !")
    private String createBy;

    @NotNull(message = "Trạng thái không được để trống !")
    private DocumentStatus status;

    private LocalDate fromDate;
    private LocalDate toDate;
}
