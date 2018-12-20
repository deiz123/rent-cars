package ru.soft.rentcars.models.requests;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "New client")
public class NewClientRequest {
    @ApiModelProperty(notes = "Client name")
    @NotNull(message = "Please provide client name")
    private String name;

    @ApiModelProperty(notes = "Client birthday")
    @NotNull(message = "Please provide client birthday in format: 1990-12-31")
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date birthday;

    @ApiModelProperty(notes = "Car model")
    @NotNull(message = "Please provide car model")
    private String model;

    @ApiModelProperty(notes = "Car year")
    @NotNull(message = "Please provide car year")
    @Min(value = 1000, message = "Please provide correct car year")
    @Max(value = 3000, message = "Please provide correct car year")
    private int year;
}
