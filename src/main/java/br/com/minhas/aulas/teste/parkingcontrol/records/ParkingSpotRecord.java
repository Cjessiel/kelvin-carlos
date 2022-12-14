package br.com.minhas.aulas.teste.parkingcontrol.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ParkingSpotRecord(

        @NotBlank     //anotação que valida se os dados estao vazios
        String parkingSpotNumber,
        @NotBlank
        @Size(max = 7)
        String licensePlateCar,
        @NotBlank
        String brandCar,
        @NotBlank
        String modelCar,
        @NotBlank
        String colorCar,
        @NotBlank
        String responsibleName,
        @NotBlank
        String apartment,
        @NotBlank
        String block

        ) {


}
