package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.ApproveMapper;
import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.mapper.SequenceMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SequenceDAO {

    SequenceMapper sequenceMapper;

    public SequenceDAO(SequenceMapper sequenceMapper) {
        this.sequenceMapper = sequenceMapper;
    }

    public String selectProductForm(int seqCode) {
        return sequenceMapper.selectProductForm(seqCode);
    }

    public List<ProductNumberResDTO> selectProductFullNameList(int seqCode) {
        return sequenceMapper.selectProductFullNameList(seqCode);
    }

    public int insertProductNumber(ProductNumberReqDTO dto) {
        int affectedCount = 0;
        try{
            affectedCount = sequenceMapper.insertProductNumber(dto);
        }catch (DuplicateKeyException e) {
            System.out.println("duplicatedException");
            return 0;
        }
       return affectedCount;
    }

    public int updateProductNumber(String productFullName, ProductNumberReqDTO dto) {
        Map<String, Object> map = new HashMap();
        map.put("productFullName", productFullName);
        map.put("seqCode", dto.getSeqCode());
        int affectedCount = sequenceMapper.updateProductNumber(map);

       return affectedCount;
    }

    public Integer selectProductNumber(int seqCode, String productFullName) {
        Map<String, Object> map = new HashMap<>();
        map.put("seqCode", seqCode);
        map.put("productFullName", productFullName);
        return sequenceMapper.selectProductNumber(map);
    }

    public boolean selectProductNumberExist(int seqCode, String productFullName) {
        Map<String, Object> map = new HashMap<>();
        map.put("seqCode", seqCode);
        map.put("productFullName", productFullName);
        int count = sequenceMapper.selectProductNumberExist(map);
        if(count ==0) {
            return false;
        }
        return true;
    }
}
