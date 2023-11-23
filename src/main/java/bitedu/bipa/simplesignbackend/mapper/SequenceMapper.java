package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;


@Mapper
public interface SequenceMapper {

    String selectProductForm(int seqCode);

    List<ProductNumberResDTO> selectProductFullNameList(int seqCode);

    int insertProductNumber(ProductNumberReqDTO dto);

    int selectLastInsertedId();

    int insertProductLog(Map map);

    int selectProductNumber(Map map);

    int updateProductNumber(Map map);

    int selectProductNumberExist(Map map);
}
