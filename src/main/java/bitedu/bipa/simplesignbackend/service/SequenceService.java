package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.ApproveDAO;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.dao.SequenceDAO;
import bitedu.bipa.simplesignbackend.enums.OrganizationStatus;
import bitedu.bipa.simplesignbackend.enums.SequenceItem;
import bitedu.bipa.simplesignbackend.model.dto.BelongOrganizationDTO;
import bitedu.bipa.simplesignbackend.model.dto.ProductNumberReqDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SequenceService {

    private final SequenceDAO sequenceDAO;
    private final CommonDAO commonDAO;
    private final ApproveDAO approveDAO;

    public SequenceService(SequenceDAO sequenceDAO, CommonDAO commonDAO, ApproveDAO approveDAO) {
        this.sequenceDAO = sequenceDAO;
        this.commonDAO = commonDAO;
        this.approveDAO = approveDAO;
    }

    @Transactional
    public void createProductNum(int seqCode, int userId, int approvalDocId) {
        //1. 채번코드에 맞는 양식문자열 가져오기(ex)'01,02,03')
        String productForm = sequenceDAO.selectProductForm(seqCode);
        List<String> productList = new ArrayList<>(Arrays.asList(productForm.split(",")));

        //userId에 맞는 부서아이디, 회사아이디, 사업장 아이디 가져오기
        BelongOrganizationDTO belongs = commonDAO.getBelongs(userId);
        System.out.println(belongs);
        //만약 양식문자열에 회사명이나 사업장명 부서명이 포함되어 있다면 그에 맞는 채번의 품의번호 업데이트와 로그 인서트후 번호 가져오기
        // 없으면? 업데이트????????품의번호 가져오기
        int productNumber = 0;
        ProductNumberReqDTO productNumberReqDTO = new ProductNumberReqDTO();
        productNumberReqDTO.setSeqCode(seqCode);
        for(String product:productList) {
            if(product.contains(SequenceItem.DEPTNAME.getCode())) {
                System.out.println("부서명");
                productNumberReqDTO.setStatus(OrganizationStatus.DEPARTMENT.getCode());
                productNumberReqDTO.setUseId(belongs.getDeptId());

                productNumber = sequenceDAO.updateProductNumber(productNumberReqDTO);
                sequenceDAO.insertProductNumberLog(productNumberReqDTO);
            } else if (product.contains(SequenceItem.ESTNAME.getCode())) {
                System.out.println("사업장명");
                productNumberReqDTO.setStatus(OrganizationStatus.ESTABLISHMENT.getCode());
                productNumberReqDTO.setUseId(belongs.getEstId());

                productNumber = sequenceDAO.updateProductNumber(productNumberReqDTO);
                sequenceDAO.insertProductNumberLog(productNumberReqDTO);
            } else if (product.contains(SequenceItem.COMPNAME.getCode())) {
                System.out.println("회사명");
                productNumberReqDTO.setStatus(OrganizationStatus.COMPANY.getCode());
                productNumberReqDTO.setUseId(belongs.getCompId());

                productNumber = sequenceDAO.updateProductNumber(productNumberReqDTO);
                sequenceDAO.insertProductNumberLog(productNumberReqDTO);
            } else {
                //?????????????????????????????????????????
            }
        }

        //결재문서에 만들어진 품의번호 넣기
        StringBuffer buffer = new StringBuffer();
        for(String product:productList) {
            switch (product) {
                case"01": case"02": case"03": case"04":
                case"05": case"06": case"07": case"08":
                    buffer.append(product);
                    break;
                case"09":
                    buffer.append(LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY")));
                    break;
                case"10":
                    buffer.append(LocalDate.now().format(DateTimeFormatter.ofPattern("MM")));
                    break;
                case"11":
                    buffer.append(LocalDate.now().format(DateTimeFormatter.ofPattern("DD")));
                    break;
                case "12":
                    buffer.append(String.format("%02d", productNumber));
                    break;
                case "13":
                    buffer.append(String.format("%03d", productNumber));
                    break;
                case "14":
                    buffer.append(String.format("%04d", productNumber));
                    break;
                case "15":
                    buffer.append(String.format("%05d", productNumber));
                    break;
                case "16":
                    buffer.append(belongs.getCompName());
                    break;
                case "17":
                    buffer.append(belongs.getEstName());
                    break;
                case "18":
                    buffer.append(belongs.getDeptId()); //추후 수정필요
                    break;
            }
        }
        int affectedCount = approveDAO.insertProductNumber(buffer.toString(), approvalDocId);
        if(affectedCount ==0) {
            throw  new RuntimeException();
        }

    }


}
