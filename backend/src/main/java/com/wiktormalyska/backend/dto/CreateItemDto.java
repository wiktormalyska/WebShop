package com.wiktormalyska.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateItemDto {
    private String name;
    private int price;
    private String description;
    private int quantity;
}
