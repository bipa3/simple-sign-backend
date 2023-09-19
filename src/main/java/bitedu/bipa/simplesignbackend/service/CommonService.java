package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.model.dto.CompanyDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CommonService {

    CommonDAO commonDAO;

    public CommonService (CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    public ArrayList<CompanyDTO> selectCompList() {
        return commonDAO.selectCompany();
    }
}
