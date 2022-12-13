package br.com.minhas.aulas.teste.parkingcontrol.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity //significa que uma classe é uma entidade (ou seja, ela é uma tabela no banco de dados)
@Table(name = "TB_PARKING_SPOT") //define o nome da tabela no banco de dados
@Getter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParkingSpotModel implements Serializable {
    private static final long serialVersionUID = 1L; //indentificador de versão de serialização de uma classe

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) //gera automaticamente o Id
    private UUID id;
    @Column(nullable = false, unique = true, length = 10) //indicamos que nao pode ser nulo, é um valor unico e com tamanho de ate 10 caracteres
    private String parkingSpotNumber;
    @Column(nullable = false, unique = true, length = 7)
    private String licensePlateCar;
    @Column(nullable = false, length = 70)
    private String brandCar;
    @Column(nullable = false, length = 70)
    private String modelCar;
    @Column(nullable = false, length = 70)
    private String colorCar;
    @Column(nullable = false)
    private LocalDateTime registrationDate;
    @Column(nullable = false, length = 130)
    private String responsableName;
    @Column(nullable = false, length = 30)
    private String apartment;
    @Column(nullable = false, length = 30)
    private String block;





}
