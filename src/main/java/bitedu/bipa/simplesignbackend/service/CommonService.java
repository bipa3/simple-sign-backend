package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.model.dto.CompanyDTO;
import bitedu.bipa.simplesignbackend.model.dto.SeqItemListDTO;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommonService {

    CommonDAO commonDAO;

    public CommonService (CommonDAO commonDAO) {
        this.commonDAO = commonDAO;
    }

    public ArrayList<CompanyDTO> selectCompList() {
        return commonDAO.selectCompany();
    }

    public List<SeqItemListDTO> selectSeqItemList() {
        return commonDAO.selectSeqItemList();
    }
}
