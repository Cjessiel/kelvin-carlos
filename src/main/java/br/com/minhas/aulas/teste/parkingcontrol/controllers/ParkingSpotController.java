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
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600) //determina se determinado recurso pode ser ou não acessado
@RequestMapping("/parking-spot")
public class ParkingSpotController {

    final ParkingSpotService parkingSpotService;

    public ParkingSpotController(ParkingSpotService parkingSpotService) {
        this.parkingSpotService = parkingSpotService;
    }

    @PostMapping
    public ResponseEntity<Object> saveParkingSpot (@RequestBody @Valid ParkingSpotRecord parkingSpotRecord) {
        if (parkingSpotService.existsByLicensePlateCar(parkingSpotRecord.licensePlateCar())){ //metodo criado para verificar se no banco contem os mesmos dados que estao sendo passados, se tiver, ira infomar a mensagem abaixo
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: License Plate Car is in use!");
        }
        if (parkingSpotService.existsByParkingSpotNumber(parkingSpotRecord.parkingSpotNumber())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot ir already in use!");
        }
        if (parkingSpotService.existsByApartmentAndBlock(parkingSpotRecord.apartment(), parkingSpotRecord.block())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Parking Spot already registered for this apartment/block!");
        }
        var parkingSpotModel = new ParkingSpotModel(); //cria um novo objeto de ParkingSpotModel
        BeanUtils.copyProperties(parkingSpotRecord, parkingSpotModel); //copia os dados que estao sendo recebidos no record para o model
        parkingSpotModel.setRegistrationDate(LocalDateTime.now(ZoneId.of("UTC"))); //aqui definimos a data de cadastro
        return ResponseEntity.status(HttpStatus.CREATED).body(parkingSpotService.save(parkingSpotModel));
        //criamos a respostasta pelo ResponseEntity, ao final no body criamos o save
    }

    @GetMapping
    public ResponseEntity<List<ParkingSpotModel>> getAllParkingSpots(){
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getOneParkingSpots(@PathVariable(value = "id") UUID id) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotModelOptional.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteParkingSpot(@PathVariable(value = "id")UUID id){
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        parkingSpotService.delete(parkingSpotModelOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("Parking Spot deleted successfully.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePrkingSpot(@PathVariable(value = "id")UUID id,
                                                   @RequestBody @Valid ParkingSpotRecord parkingSpotRecord) {
        Optional<ParkingSpotModel> parkingSpotModelOptional = parkingSpotService.findById(id);
        if (!parkingSpotModelOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Parking Spot not found.");
        }
        var parkingSpotModel = parkingSpotModelOptional.get();
        BeanUtils.copyProperties(parkingSpotRecord, parkingSpotModel);
        parkingSpotModel.setId(parkingSpotModelOptional.get().getId());
        parkingSpotModel.setRegistrationDate(parkingSpotModelOptional.get().getRegistrationDate());

                        //dois modos de fazer
//        parkingSpotModel.setParkingSpotNumber(parkingSpotRecord.parkingSpotNumber());
//        parkingSpotModel.setLicensePlateCar(parkingSpotRecord.licensePlateCar());
//        parkingSpotModel.setModelCar(parkingSpotRecord.modelCar());
//        parkingSpotModel.setBrandCar(parkingSpotRecord.brandCar());
//        parkingSpotModel.setColorCar(parkingSpotRecord.colorCar());
//        parkingSpotModel.setResponsibleName(parkingSpotRecord.responsibleName());
//        parkingSpotModel.setApartment(parkingSpotRecord.apartment());
//        parkingSpotModel.setBlock(parkingSpotRecord.block());
        return ResponseEntity.status(HttpStatus.OK).body(parkingSpotService.save(parkingSpotModel));
    }
}
