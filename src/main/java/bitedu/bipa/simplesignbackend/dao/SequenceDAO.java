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

    public String selectProductForm(int seqCode) {
        return sequenceMapper.selectProductForm(seqCode);
    }

    public int updateProductNumber(ProductNumberReqDTO dto){
        sequenceMapper.updateProductNumber(dto);
        return sequenceMapper.selectLastInsertedId();
    }

    public int insertProductNumberLog(ProductNumberReqDTO dto) {
        return sequenceMapper.insertProductNumberLog(dto);
    }

}
