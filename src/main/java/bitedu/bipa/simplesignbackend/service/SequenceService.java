package bitedu.bipa.simplesignbackend.service;

import bitedu.bipa.simplesignbackend.dao.ApproveDAO;
import bitedu.bipa.simplesignbackend.dao.CommonDAO;
import bitedu.bipa.simplesignbackend.dao.SequenceDAO;
import bitedu.bipa.simplesignbackend.enums.OrganizationStatus;
import bitedu.bipa.simplesignbackend.enums.SequenceItem;
import bitedu.bipa.simplesignbackend.model.dto.BelongOrganizationDTO;
import bitedu.bipa.simplesignbackend.model.dto.ProductNumberReqDTO;
import bitedu.bipa.simplesignbackend.model.dto.ProductNumberResDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class SequenceService {

    private final SequenceDAO sequenceDAO;
    private final CommonDAO commonDAO;

    public SequenceService(SequenceDAO sequenceDAO, CommonDAO commonDAO, ApproveDAO approveDAO) {
        this.sequenceDAO = sequenceDAO;
        this.commonDAO = commonDAO;
    }

    @Transactional
    public String createProductNum(int seqCode, int userId) {
        //1. 채번코드에 맞는 양식문자열 가져오기(ex)'01,02,03')
        String productForm = sequenceDAO.selectProductForm(seqCode);
        List<String> productList = new ArrayList<>(Arrays.asList(productForm.split(",")));

        //userId에 맞는 부서아이디, 회사아이디, 사업장 아이디 가져오기
        BelongOrganizationDTO belongs = commonDAO.getBelongs(userId);

        //품의번호 만들기
        StringBuffer buffer = new StringBuffer();
        for(String product:productList) {
            switch (product) {
                case"01":
                    buffer.append(product);
                    break;
                case"02":
                    buffer.append(SequenceItem.JE.getProductItem());
                    break;
                case"03":
                    buffer.append(SequenceItem.HO.getProductItem());
                    break;
                case"04":
                    buffer.append(SequenceItem.HYPHEN.getProductItem());
                    break;
                case"05":
                    buffer.append(SequenceItem.SLASH.getProductItem());
                    break;
                case"06":
                    buffer.append(SequenceItem.PERIOD.getProductItem());
                    break;
                case"07":
                    buffer.append(SequenceItem.COMMA.getProductItem());
                    break;
                case"08":
                    buffer.append(SequenceItem.TILDE.getProductItem());
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
                    buffer.append(SequenceItem.CODE2);
                    break;
                case "13":
                    buffer.append(SequenceItem.CODE3);
                    break;
                case "14":
                    buffer.append(SequenceItem.CODE4);
                    break;
                case "15":
                    buffer.append(SequenceItem.CODE5);
                    break;
                case "16":
                    buffer.append(belongs.getCompName());
                    break;
                case "17":
                    buffer.append(belongs.getEstName());
                    break;
                case "18":
                    buffer.append(belongs.getDeptName());
                    break;
            }
        }

        //만들어진 품의번호가 품의번호 테이블에 존재하는지 확인
        List<ProductNumberResDTO> productFullNameList = sequenceDAO.selectProductFullNameList(seqCode);
        boolean isStringPresent = productFullNameList.stream()
                .anyMatch(s -> s.getProductFullName().contains(buffer.toString()));
        //없다면 품의번호 테이블에 insert, 로그 insert
        //있다면 품의번호 update, 로그 insert
        ProductNumberReqDTO productNumberReqDTO = new ProductNumberReqDTO();
        productNumberReqDTO.setSeqCode(seqCode);
        productNumberReqDTO.setProductFullName(buffer.toString());
        int productNum = 0; // 최근 삽입된 품의번호
        if(!isStringPresent) {
            productNum = sequenceDAO.insertProductNumber(productNumberReqDTO);
        }else {
            int productId = productFullNameList.stream()
                    .filter(s -> s.getProductFullName().equals(buffer.toString()))
                    .collect(Collectors.toList()).get(0).getProductId();
            productNum = sequenceDAO.updateProductNumber(productId,productNumberReqDTO);
        }

        //완전히 만든 품의번호 return
        String productFullName = buffer.toString();
        if(productFullName.contains("CODE2")) {
            productFullName = productFullName.replace("CODE2", String.format("%02d", productNum));
        } else if(productFullName.contains("CODE3")) {
            productFullName = productFullName.replace("CODE3", String.format("%03d", productNum));
        }else if(productFullName.contains("CODE4")) {
            productFullName = productFullName.replace("CODE4", String.format("%04d", productNum));
        }else if(productFullName.contains("CODE5")) {
            productFullName = productFullName.replace("CODE5", String.format("%05d", productNum));
        }

        //System.out.println("완성 productName:" + productFullName);
        return productFullName;

    }


}
