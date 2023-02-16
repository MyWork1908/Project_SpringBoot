package com.project.application.controllers;

import com.project.application.exception.NotFoundException;
import com.project.application.exception.NotImplementedException;
import com.project.application.models.Fresher;
import com.project.application.models.Thesis;
import com.project.application.models.dto.ResponseObject;
import com.project.application.models.dto.Transcript;
import com.project.application.models.mapper.TranscriptMapper;
import com.project.application.repositosies.FresherRepository;
import com.project.application.repositosies.ThesisRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/v1/Fresher")
public class FresherController {
    @Autowired
    FresherRepository fresherRepository;
    @Autowired
    ThesisRepository thesisRepository;

    @GetMapping(path = "/all")
    public List<Fresher> getAllFresher() {
        return fresherRepository.findAll();
    }

    @PostMapping(path = "/insert")
    public @ResponseBody ResponseEntity<ResponseObject> insertFresher(@RequestParam String fresherCode,
                                                                      @RequestParam String firstName,
                                                                      @RequestParam String lastName,
                                                                      @RequestParam String gender,
                                                                      @RequestParam String dateOfBirth,
                                                                      @RequestParam String address,
                                                                      @RequestParam String email,
                                                                      @RequestParam String phone) throws ParseException {
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirth);
        Fresher fresher = new Fresher(fresherCode,firstName,lastName,gender,date,address,email,phone);
        List<Fresher> fresherList = fresherRepository.findByFresherCode(fresherCode);
        if(fresherList.size()==0){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Insert product successfully", fresherRepository.save(fresher))
            );
        }
        throw new NotImplementedException("Fresher name already taken");
    }

    @DeleteMapping(path = "/delete/{fresherCode}")
    public @ResponseBody ResponseEntity<ResponseObject> deleteFresher(@PathVariable String fresherCode) {
        boolean exists = fresherRepository.existsById(fresherCode);
        if(exists) {
            fresherRepository.deleteById(fresherCode);
            return  ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok","Delete fresher successfully","")
            );
        }
        throw new NotFoundException("Cannot find fresher to delete");
    }

    @PutMapping(path = "/edit/{fresherCode}")
    public @ResponseBody ResponseEntity<ResponseObject> editFresher(@PathVariable String fresherCode,
                                                                    @RequestParam String firstName,
                                                                    @RequestParam String lastName,
                                                                    @RequestParam String gender,
                                                                    @RequestParam String dateOfBirth,
                                                                    @RequestParam String address,
                                                                    @RequestParam String email,
                                                                    @RequestParam String phone) throws ParseException {
        Date date = new SimpleDateFormat("dd-MM-yyyy").parse(dateOfBirth);
        Fresher newfresher = fresherRepository.findById(fresherCode)
                .map(fresher -> {
                    fresher.setFirstName(firstName);
                    fresher.setLastName(lastName);
                    fresher.setGender(gender);
                    fresher.setDateOfBirth(date);
                    fresher.setAddress(address);
                    fresher.setEmail(email);
                    fresher.setPhone(phone);
                    return fresherRepository.save(fresher);
                }).orElseGet(()->{
                    Fresher fresher = new Fresher(fresherCode,firstName,lastName,gender,date,address,email,phone);
                    return fresherRepository.save(fresher);
                });
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("Ok", "Update fresher successfully", newfresher)
        );
    }

    @GetMapping(path = "/thesis")
    public List<Transcript> getAllThesis() {
        List<Transcript> transcriptList = new ArrayList<Transcript>();
        List<Thesis> thesisList = thesisRepository.findAll();
        for(Thesis thesis: thesisList) {
            Optional<Fresher> fresher = fresherRepository.findById(thesis.getFresherCode());
            transcriptList.add(TranscriptMapper.toTranscript(thesis,fresher));
        }
        return transcriptList;
    }

    @PutMapping(path = "/thesis/upsert")
    public @ResponseBody ResponseEntity<ResponseObject> upsertThesis(@RequestParam String fresherCode,
                                                                     @RequestParam String programmingLanguage,
                                                                     @RequestParam Double thesisScore01,
                                                                     @RequestParam Double thesisScore02,
                                                                     @RequestParam Double thesisScore03) {
        Double average = (double) Math.round(((thesisScore01 + thesisScore02 + thesisScore03)/3)*10)/10;
        try {
            Thesis newthesis = thesisRepository.findById(fresherCode)
                    .map(thesis -> {
                        thesis.setProgrammingLanguage(programmingLanguage);
                        thesis.setThesisScore01(thesisScore01);
                        thesis.setThesisScore02(thesisScore02);
                        thesis.setThesisScore03(thesisScore03);
                        thesis.setThesisScoreAverage(average);
                        return thesisRepository.save(thesis);
                    }).orElseGet(()->{
                        Thesis thesis = new Thesis(fresherCode,programmingLanguage,thesisScore01,thesisScore02,thesisScore03,average);
                        return thesisRepository.save(thesis);
                    });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Update thesis successfully", newthesis)
            );
        }catch (Exception e) {
            throw new NotFoundException(e.getMessage());
        }
    }
    @GetMapping(path = "/search/lastname")
    public List<Fresher> searchLastName(@RequestParam String lastName) {
        List<Fresher> fresherList = fresherRepository.findByLastName(lastName);
        if(fresherList.size()>0) {
            return  fresherList;
        }
        throw new NotFoundException("There are no fresher with the same name as above");
    }
    @GetMapping(path = "/search/email")
    public List<Fresher> searchEmail(@RequestParam String email) {
        List<Fresher> fresherList = fresherRepository.findByEmail(email);
        if(fresherList.size()>0) {
            return  fresherList;
        }
        throw new NotFoundException("There are no fresher with the same name as above");
    }
    @GetMapping(path = "/search/programming")
    public List<Optional<Fresher>> sreachProgrammingLanguage(@RequestParam String programmingLanguage) {
        List<Optional<Fresher>> fresherList = new ArrayList<>();
        List<Thesis> thesisList = thesisRepository.findByProgrammingLanguage(programmingLanguage);
        if(thesisList.size()>0) {
            for (Thesis thesis: thesisList) {
                Optional<Fresher> fresher = fresherRepository.findById(thesis.getFresherCode());
                fresherList.add(fresher);
            }
            return fresherList;
        }
        throw new NotFoundException("There are no fresher with the same name as above");
    }
    @PutMapping(path = "/center/{fresherCode}")
    public @ResponseBody ResponseEntity<ResponseObject> addCenter(@RequestParam String centerCode,
                                                                  @PathVariable String fresherCode) {
        boolean exists = fresherRepository.existsById(fresherCode);
        if(exists) {
            Optional<Object> newfresher = fresherRepository.findById(fresherCode)
                    .map(fresher -> {
                       fresher.setCenterCode(centerCode);
                       return fresher;
                    });
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("Ok", "Add fresher to the center successfully", newfresher)
            );
        }
        throw new NotImplementedException("Center code is not in table center");
    }
}
