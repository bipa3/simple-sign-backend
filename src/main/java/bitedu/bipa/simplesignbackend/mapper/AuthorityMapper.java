package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.AuthorityDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AuthorityMapper {
    AuthorityDTO findAuthority(int userId);
}
