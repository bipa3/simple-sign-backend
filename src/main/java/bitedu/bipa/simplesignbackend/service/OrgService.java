package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.OrgDAO;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrgService {

    @Autowired
    private OrgDAO orgDAO;


    // TreeView
    public List<OrgCompanyDTO> orgTreeView() {
        List<OrgCompanyDTO> rawDataList = orgDAO.getOrgTreeView();

        Map<Integer, OrgCompanyDTO> companyMap = new HashMap<>();
        Map<Integer, OrgEstablishmentDTO> establishmentMap = new HashMap<>();
        Map<Integer, OrgDepartmentDTO> departmentMap = new HashMap<>();

        for (OrgCompanyDTO rawData : rawDataList) {
            OrgCompanyDTO companyDTO = companyMap.computeIfAbsent(rawData.getCompId(), id -> {
                OrgCompanyDTO newCompany = new OrgCompanyDTO();
                newCompany.setCompId(rawData.getCompId());
                newCompany.setCompName(rawData.getCompName());
                newCompany.setEsts(new ArrayList<>());
                return newCompany;
            });

            if (rawData.getEstId() != 0) {
                OrgEstablishmentDTO estDTO = establishmentMap.computeIfAbsent(rawData.getEstId(), id -> {
                    OrgEstablishmentDTO newEstablishment = new OrgEstablishmentDTO();
                    newEstablishment.setEstId(rawData.getEstId());
                    newEstablishment.setEstName(rawData.getEstName());
                    newEstablishment.setDepts(new ArrayList<>());
                    companyDTO.getEsts().add(newEstablishment);
                    return newEstablishment;
                });

                if (rawData.getDeptId() != 0) {
                    OrgDepartmentDTO deptDTO = departmentMap.computeIfAbsent(rawData.getDeptId(), id -> {
                        OrgDepartmentDTO newDepartment = new OrgDepartmentDTO();
                        newDepartment.setDeptId(rawData.getDeptId());
                        newDepartment.setDeptName(rawData.getDeptName());
                        newDepartment.setUpperDeptId(rawData.getUpperDeptId());
                        newDepartment.setSubDepts(new ArrayList<>());

                        if (newDepartment.getUpperDeptId() == 0) {
                            estDTO.getDepts().add(newDepartment);
                        }
                        return newDepartment;
                    });

                    if (deptDTO.getUpperDeptId() != 0) {
                        OrgDepartmentDTO upperDept = departmentMap.get(deptDTO.getUpperDeptId());
                        if (upperDept != null) {
                            upperDept.getSubDepts().add(deptDTO);
                        } else {
                            List<OrgDepartmentDTO> subDeptsList = new ArrayList<>();
                            subDeptsList.add(deptDTO);
                            deptDTO.setSubDepts(subDeptsList);
                        }
                    }
                }
            }
        }

        return new ArrayList<>(companyMap.values());
    }

}
