package lk.solution.health.excellent.processHandling.controller;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.solution.health.excellent.common.service.DateTimeAgeService;
import lk.solution.health.excellent.common.service.FileHandelService;
import lk.solution.health.excellent.general.service.InvoiceHasLabTestService;
import lk.solution.health.excellent.lab.entity.LabTest;
import lk.solution.health.excellent.lab.service.LabTestService;
import lk.solution.health.excellent.resource.entity.MedicalPackage;
import lk.solution.health.excellent.resource.entity.Patient;
import lk.solution.health.excellent.resource.service.*;
import lk.solution.health.excellent.transaction.service.DiscountRatioService;
import lk.solution.health.excellent.transaction.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/invoiceProcess")
public class InvoiceProcessRestController {

    private final InvoiceService invoiceService;
    private final UserService userService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final LabTestService labTestService;
    private final DateTimeAgeService dateTimeAgeService;
    private final CollectingCenterService collectingCenterService;
    private final DiscountRatioService discountRatioService;
    private final MedicalPackageService medicalPackageService;
    private final InvoiceHasLabTestService invoiceHasLabTestService;
    private final FileHandelService fileHandelService;
    private final ServletContext context;

    @Autowired
    public InvoiceProcessRestController(InvoiceService invoiceService, UserService userService, PatientService patientService,
                                        DoctorService doctorService, LabTestService labTestService, DateTimeAgeService dateTimeAgeService,
                                        CollectingCenterService collectingCenterService, DiscountRatioService discountRatioService,
                                        MedicalPackageService medicalPackageService, InvoiceHasLabTestService invoiceHasLabTestService, FileHandelService fileHandelService, ServletContext context) {
        this.invoiceService = invoiceService;
        this.userService = userService;
        this.patientService = patientService;
        this.doctorService = doctorService;
        this.labTestService = labTestService;
        this.dateTimeAgeService = dateTimeAgeService;
        this.collectingCenterService = collectingCenterService;
        this.discountRatioService = discountRatioService;
        this.medicalPackageService = medicalPackageService;
        this.invoiceHasLabTestService = invoiceHasLabTestService;
        this.fileHandelService = fileHandelService;
        this.context = context;
    }

    // using spring boot filtering service most required variable send to the front end
    private void filterParameter(MappingJacksonValue mappingJacksonValue, FilterProvider filters) {
        mappingJacksonValue.setFilters(filters);
    }

    // medical package lab test send to font end
    @GetMapping("/medicalPackageLabTestGet/{id}")
    public MappingJacksonValue getLabTestByMedicalPackageId(@PathVariable Integer id) {
        List<LabTest> labTests = medicalPackageService.findById(id).getLabTests();
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(labTests);
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("code", "name", "labtestDoneHere");
        FilterProvider filters = new SimpleFilterProvider().addFilter("LabTest", simpleBeanPropertyFilter);
        filterParameter(mappingJacksonValue, filters);
        return mappingJacksonValue;
    }
    // medical package send to font end
    @GetMapping("/medicalPackageGet/{id}")
    public MappingJacksonValue getMedicalPackageById(@PathVariable Integer id) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(medicalPackageService.findById(id));
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "price");
        FilterProvider filters = new SimpleFilterProvider().addFilter("MedicalPackage", simpleBeanPropertyFilter);
        filterParameter(mappingJacksonValue, filters);
        return mappingJacksonValue;
    }
    //send patient details  send to font end
    @GetMapping("/patientFind")
    public MappingJacksonValue getPatient(@PathParam("Patient")Patient patient) {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(patientService.search(patient));
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "number", "title", "name", "nic", "mobile");
        FilterProvider filters = new SimpleFilterProvider().addFilter("Patient", simpleBeanPropertyFilter);
        filterParameter(mappingJacksonValue, filters);
        return mappingJacksonValue;
    }



    //if need to add entity variable to url add @PathParam to class
    @GetMapping(value = "/searchLabTest1")
    public MappingJacksonValue search1(@PathParam("LabTest") LabTest labTest) {
        //LabTest labTest2 = new LabTest();
        //labTest2.setName("lipid");
        System.out.println("im here lab test one");
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(labTestService.search(labTest));
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name");
        FilterProvider filters = new SimpleFilterProvider().addFilter("LabTest", simpleBeanPropertyFilter);
        filterParameter(mappingJacksonValue, filters);
        return mappingJacksonValue;
    }


    //@RequestMapping(value = "/search", method = RequestMethod.GET)
    @GetMapping("/labTest1/{id}")
    public MappingJacksonValue searchLabTest(@PathVariable Integer id) {
        LabTest labTest = new LabTest();
        //labTest.setName("aa");
        labTest.setId(id);
        List<LabTest> labTests = labTestService.search(labTest);
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(labTests);
        for (LabTest lab : labTests
        ) {
            System.out.println(lab.getId());

            SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "labtestDoneHere");
            FilterProvider filters = new SimpleFilterProvider().addFilter("LabTest", simpleBeanPropertyFilter);

            filterParameter(mappingJacksonValue, filters);
        }

        return mappingJacksonValue;
    }

    @GetMapping("/labTest11/{id}")
    public LabTest searchTest(@PathVariable Integer id) {
        return labTestService.findById(id);
    }

    /*
        @ResponseBody
        public MappingJacksonValue getOne(@PathVariable Integer id){


                MappingJacksonValue mappingJacksonValue =  new MappingJacksonValue(labTestService.findById(id));
                SimpleBeanPropertyFilter simpleBeanPropertyFilter =  SimpleBeanPropertyFilter.filterOutAllExcept("id","name","price","labtestDoneHere");
                FilterProvider filters = new SimpleFilterProvider().addFilter("LabTest", simpleBeanPropertyFilter);

                filterParameter(mappingJacksonValue,filters);
            System.out.println(mappingJacksonValue.toString().chars());

            return mappingJacksonValue;


        }
        @GetMapping("/labTest1")*/
    public MappingJacksonValue getAll() {
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(labTestService.findAll());
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter.filterOutAllExcept("id", "name", "description", "labtestDoneHere");
        FilterProvider filters = new SimpleFilterProvider().addFilter("LabTest", simpleBeanPropertyFilter);
        filterParameter(mappingJacksonValue, filters);


        return mappingJacksonValue;
    }


//    @RequestMapping(value="/pat", method = RequestMethod.GET)
//    public List<Patient> findAll(@RequestParam(name = "name",required = false) String name){
//        System.out.println("first-part");
//        if(StringUtils.isEmpty(name)){
//            System.out.println("second - part");
//            return Collections.singletonList(patientService.findByName(name));
//        }
//        System.out.println("all user");
//        return patientService.findAll();
//    }


//        @GetMapping("/labTest1/{id}")
//        public ResponseEntity<Object> findLabTest1(@PathVariable Integer id){
//            System.out.println(id);
//            ServiceResponse<LabTest> response = new ServiceResponse<>("success", labTestService.findById(id));
//            return new ResponseEntity<>(response, HttpStatus.OK);
//    }


/*    @GetMapping("/labTest1/{id}")
//    @ResponseBody --> this will give JSON object representation
    public LabTest findLabTest1(@PathVariable Integer id){
        System.out.println(id);
        return labTestService.findById(id);
    }*/

//    @GetMapping("/lat")
//    public List<LabTest> getLabTest() {
//        return labTestService.findAll();
//    }
    /* @PostMapping("/api/search")
    public ResponseEntity<?> getSearchResultViaAjax(
            @Valid @RequestBody SearchCriteria search, Errors errors) {

        AjaxResponseBody result = new AjaxResponseBody();

        //If error, just return a 400 bad request, along with the error message
        if (errors.hasErrors()) {

            result.setMsg(errors.getAllErrors()
                    .stream().map(x -> x.getDefaultMessage())
                    .collect(Collectors.joining(",")));

            return ResponseEntity.badRequest().body(result);

        }

        List<User> users = userService.findByUserNameOrEmail(search.getUsername());
        if (users.isEmpty()) {
            result.setMsg("no user found!");
        } else {
            result.setMsg("success");
        }
        result.setResult(users);

        return ResponseEntity.ok(result);

    }*/


}
