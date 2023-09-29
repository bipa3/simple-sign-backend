package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface SequenceMapper {

    String selectProductForm(int seqCode);

    int updateProductNumber(ProductNumberReqDTO dto);

    int selectLastInsertedId();

    int insertProductNumberLog(ProductNumberReqDTO dto);



}
