package bitedu.bipa.simplesignbackend.mapper;

import bitedu.bipa.simplesignbackend.model.dto.AuthorityDTO;
import bitedu.bipa.simplesignbackend.model.dto.RoleRequestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthorityMapper {
    int findAuthority(RoleRequestDTO roleRequestDTO);

    String getAuthorityName(int authorityCode);
}
