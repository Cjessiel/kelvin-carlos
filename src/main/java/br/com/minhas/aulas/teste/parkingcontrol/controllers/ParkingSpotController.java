package br.com.minhas.aulas.teste.parkingcontrol.controllers;

import br.com.minhas.aulas.teste.parkingcontrol.models.ParkingSpotModel;
import br.com.minhas.aulas.teste.parkingcontrol.records.ParkingSpotRecord;
import br.com.minhas.aulas.teste.parkingcontrol.services.ParkingSpotService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneId;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600) //determina se determinado recurso pode ser ou não acessado
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot (@RequestBody @Valid ParkingSpotRecord parkingSpotRecord){
        var parkingSpotModel = new ParkingSpotModel(); //cria um novo objeto de ParkingSpotModel
        BeanUtils.copyProperties(parkingSpotRecord, parkingSpotModel); //copia os dados que estao sendo recebidos no record para o model
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC"))); //aqui definimos a data de cadastro
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
        //criamos a respostasta pelo ResponseEntity, ao final no boby criamos o save
    }


}
