package ru.soft.rentcars.models.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "Remove client")
public class RemoveClientRequest {
    @ApiModelProperty(notes = "Client name")
    @NotNull(message = "Please provide client name")
    private String name;

    @ApiModelProperty(notes = "Car model")
    @NotNull(message = "Please provide car model")
    private String model;
}
