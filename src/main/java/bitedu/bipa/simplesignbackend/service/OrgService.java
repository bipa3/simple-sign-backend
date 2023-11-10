package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.OrgDAO;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrgService {

    @Autowired
    private OrgDAO orgDAO;

    // TreeView
    @Cacheable(value="orgTreeView")
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

    //회사별 트리뷰
    @Cacheable(value="orgTreeViewComp", key="#compId")
    public List<OrgCompanyDTO> orgTreeViewComp(int compId) {
        List<OrgCompanyDTO> rawDataList = orgDAO.getOrgTreeViewComp(compId);

        if (rawDataList == null || rawDataList.isEmpty()) {
            return Collections.emptyList();
        }

        OrgCompanyDTO companyDTO = new OrgCompanyDTO();
        companyDTO.setCompId(rawDataList.get(0).getCompId());
        companyDTO.setCompName(rawDataList.get(0).getCompName());
        companyDTO.setEsts(new ArrayList<>());

        Map<Integer, OrgEstablishmentDTO> establishmentMap = new HashMap<>();
        Map<Integer, OrgDepartmentDTO> departmentMap = new HashMap<>();

        for (OrgCompanyDTO rawData : rawDataList) {
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

        return Collections.singletonList(companyDTO);
    }


    // TopGridView
    @Cacheable(value="getGrid", key="#nodeId + '_' + #type + '_' + #isChecked")
    public List<OrgRespDTO> getGrid(String nodeId, String type, boolean isChecked){
        String[] ids = nodeId.split("-");


        if(isChecked){
            switch (ids.length){
                case 1:
                    if("dept".equals(type)){
                        return orgDAO.getBottomDeptComp(Integer.parseInt(ids[0]));
                    } else if ("user".equals(type) || "approvalUser".equals(type)) {
                        return orgDAO.getBottomUserComp(Integer.parseInt(ids[0]));
                    }
                case 2:
                    if("dept".equals(type)){
                        return orgDAO.getBottomEst(Integer.parseInt(ids[1]));
                    }else if("user".equals(type) || "approvalUser".equals(type)){
                        return orgDAO.getBottomUserEst(Integer.parseInt(ids[1]));
                    }
                case 3:
                    if("dept".equals(type)){
                        return orgDAO.getBottonDept(Integer.parseInt(ids[2]));
                    } else if ("user".equals(type) || "approvalUser".equals(type)) {
                        return orgDAO.getBottonUser(Integer.parseInt(ids[2]));
                    }
                    break;
                default:
                    return new ArrayList<>();
            }
        }else{
            switch (ids.length){
                case 1:
                    return orgDAO.getComp(Integer.parseInt(ids[0]));
                case 2:
                    if("dept".equals(type)){
                        return orgDAO.getDeptEst(Integer.parseInt(ids[1]));
                    }else if("user".equals(type) || "approvalUser".equals(type)){
                        return orgDAO.getEst(Integer.parseInt(ids[1]));
                    }
                case 3:
                    if("dept".equals(type)){
                        return orgDAO.getDept(Integer.parseInt(ids[2]));
                    } else if ("user".equals(type) || "approvalUser".equals(type)) {
                        return orgDAO.getUser(Integer.parseInt(ids[2]));
                    }
                    break;
                default:
                    return new ArrayList<>();
            }
        }
        return new ArrayList<>();
    }

    // Search
    public List<OrgRespDTO> searchOrg(String category, String search) {
        if (category.equals("회사")) {
            return orgDAO.searchComp(search);
        } else if (category.equals("사업장")) {
            return orgDAO.searchEst(search);
        }else if(category.equals("부서")){
            return orgDAO.searchDept(search);
        } else if (category.equals("사용자")) {
            return orgDAO.searchUser(search);
        }
        return new ArrayList<>();
    }
}