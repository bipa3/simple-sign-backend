package bitedu.bipa.simplesignbackend.dao;

import bitedu.bipa.simplesignbackend.mapper.ApproveMapper;
import bitedu.bipa.simplesignbackend.mapper.CommonMapper;
import bitedu.bipa.simplesignbackend.mapper.SequenceMapper;
import bitedu.bipa.simplesignbackend.model.dto.*;
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
        int affectedCount = sequenceMapper.insertProductNumber(dto);
        if(affectedCount==0) {
            throw  new RuntimeException();
        }
        return 1;
    }

    public int updateProductNumber(String productFullName, ProductNumberReqDTO dto) {
        int affectedCount = sequenceMapper.updateProductNumber(productFullName);

       return affectedCount;
    }

    public int selectProductNumber(int seqCode, String productFullName) {
        Map<String, Object> map = new HashMap<>();
        map.put("seqCode", seqCode);
        map.put("productFullName", productFullName);
        return sequenceMapper.selectProductNumber(map);
    }
}
