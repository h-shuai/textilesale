package com.rosellete.textilesale.interfaces;

import com.rosellete.textilesale.util.RestResponse;
import com.rosellete.textilesale.vo.DepartmentVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public interface OrganizationApi {

    @GetMapping("/getTreePath")
    RestResponse getTreePath();

    @PostMapping(value = "/updateDepartment",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse updateDepartment(@RequestBody @Valid DepartmentVO departmentVO);

    @PostMapping(value = "/insertDepartment",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse insertDepartment(@RequestBody @Valid DepartmentVO departmentVO);

    @PostMapping(value = "/updateStatus/{id}",consumes = MediaType.APPLICATION_JSON_VALUE)
    RestResponse updateStatus(@PathVariable String id);
}
