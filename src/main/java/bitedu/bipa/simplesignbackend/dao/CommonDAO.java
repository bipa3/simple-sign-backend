package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.model.dto.CompanyDTO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class CommonDAO {

    CommonMapper commonMapper;

    public CommonDAO(CommonMapper commonMapper) {
        this.commonMapper = commonMapper;
    }
    public ArrayList<CompanyDTO> selectCompany() {
        ArrayList<CompanyDTO> companyList = (ArrayList) commonMapper.getCompanyList();
        return companyList;
    }
}
