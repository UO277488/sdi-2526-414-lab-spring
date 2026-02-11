package com.uniovi.sdi.grademanager.controllers;

import com.uniovi.sdi.grademanager.entities.Mark;
import org.springframework.web.bind.annotation.*;

@RestController
public class MarksController {

        /**
        * Example of a GET request to retrieve a list of marks.
        * @return A string indicating that the list of marks is being retrieved.
        */
    @RequestMapping("/mark/list")
    public String getList() {
        return "Getting List";
    }

    /**
     * Example of a POST request to add a new mark, using a model attribute to bind the request parameters to a Mark object.
     * @param mark A Mark object that is populated with the request parameters.
     * @return A string indicating that a mark is being added, along with the details of the mark.
     */
    @PostMapping(value = "/mark/add")
    public String setMark(@ModelAttribute Mark mark) {
        return "Adding mark with description: " + mark.getDescription() +
                ", score: " + mark.getScore() + ", id: " + mark.getId();
    }

    /**
     * Example of a GET request to get details of a mark by its id, using a path variable.
     * @param id A Long representing the id of the mark for which details are being retrieved.
     * @return A string indicating that the details of the mark with the specified id are being retrieved.
     */
    @GetMapping("/mark/details/{id}")
    public String getDetail(@PathVariable Long id) {
        return "Getting details of mark with id: " + id;
    }
}
