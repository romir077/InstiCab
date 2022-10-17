package com.InstiCab.controllers;

import com.InstiCab.models.Driver;
import com.InstiCab.models.RegistrationRequest;
import com.InstiCab.service.DriverService;
import com.InstiCab.service.RegistrationRequestService;
import com.InstiCab.service.UserService;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController extends BaseController{
    @Getter
    @Setter
    static class RequestDetails {
        private Driver driver;
        private RegistrationRequest registrationRequest;
    }
    @Autowired
    public AdminController(UserService userService,
                           DriverService driverService,RegistrationRequestService registrationRequestService) {
        super(userService,driverService,registrationRequestService);
    }

    @GetMapping({"/admin/", "/admin"})
    public String adminHome(Model model) {
        if(!isLoggedIn()) {
            return "redirect:/";
        }
        if(!isAuthorized(model,ROLE_ADMIN))
            return FORBIDDEN_ERROR_PAGE;
        List<RegistrationRequest>requestList = registrationRequestService.getPendingRequest();
        List<Driver>driverList = driverService.getPendingDrivers();
        List<RequestDetails>requestDetailsList = new ArrayList<>();

        for(int i = 0; i < requestList.size(); i++) {
            RequestDetails requestDetails = new RequestDetails();
            requestDetails.setRegistrationRequest(requestList.get(i));
            requestDetails.setDriver(driverList.get(i));
            requestDetailsList.add(requestDetails);
        }
        model.addAttribute("requestDetailsList",requestDetailsList);
//        model.addAttribute("requestList",requestList);
//        model.addAttribute("driverList",driverList);
        model.addAttribute("isAdmin",true);
        return "admin";
    }

    @PostMapping("/admin/accept/{driverId}")
    public String acceptRequest(@PathVariable("driverId") Long driverId, Model model){
        RegistrationRequest req = registrationRequestService.getRequestByDriverId(driverId);
        req.setDateAccepted(Date.valueOf(LocalDate.now()));
        req.setTimeAccepted(Time.valueOf(LocalTime.now()));
        registrationRequestService.acceptRequest(req);
        return "redirect:/admin";
    }

//    @GetMapping("/admin/view/{driverId}")
//    public String viewDriver(@PathVariable("driverId") Long driverId, Model model) {
//
//    }

    @PostMapping("/admin/reject/{driverId}")
    public String rejectRequest(@PathVariable("driverId") Long driverId, Model model){
        registrationRequestService.rejectRequest(driverId);
        return "redirect:/admin";
    }
}
