package com.project.application.controllers;

import com.project.application.exception.NotFoundException;
import com.project.application.exception.NotImplementedException;
import com.project.application.models.Center;
import com.project.application.models.dto.ResponseObject;
import com.project.application.repositosies.CenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/Center")
public class CenterController {
    @Autowired
    CenterRepository centerRepository;
    @GetMapping(path = "/all")
    public List<Center> getAllCenter() {
        return centerRepository.findAll();
    }
    @PostMapping(path = "/insert")
    public @ResponseBody ResponseEntity<ResponseObject> insertCenter(@RequestParam String centerCode,
                                                                     @RequestParam String centerName,
                                                                     @RequestParam String centerAddress,
                                                                     @RequestParam String centerPhone) {
        Center center = new Center(centerCode,centerName,centerAddress,centerPhone);
        List<Center> centerList = centerRepository.findByCenterCode(centerCode);
        if(centerList.size()==0) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Insert center successfully", centerRepository.save(center))
            );
        }
        throw new NotImplementedException("Center code already taken");
    }
    @DeleteMapping(path = "/delete/{centerCode}")
    public @ResponseBody ResponseEntity<ResponseObject> deleteCenter(@PathVariable String centerCode) {
        boolean exists = centerRepository.existsById(centerCode);
        if(exists) {
            centerRepository.deleteById(centerCode);
            return  ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok","Delete center successfully","")
            );
        }
        throw new NotFoundException("Cannot find center to delete");
    }
}
